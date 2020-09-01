package me.devsdevelop.listeners;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCreeper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.BasicCreeper;
import me.devsdevelop.creepers.ChargedCreeper;
import me.devsdevelop.creepers.CustomCreeper;
import me.devsdevelop.game.GameManager;
import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.utils.Utils;
import net.minecraft.server.v1_15_R1.EntityCreeper;
import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.WorldServer;

public class PlayerInteractListener implements Listener {
	
	private DodgeCreeper plugin;
	
	public PlayerInteractListener(DodgeCreeper plugin) { 
		this.plugin = plugin;
	}

	@EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
   
        if (plugin.getGameManager().gameStarted) {
        	creeperEggUseEvent(event);
        	powerUpUseEvent(event);   	
        }        
    }
	
	private void creeperEggUseEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		GameManager gameManager = plugin.getGameManager();
		
		if (player.getInventory().getItemInMainHand() == null) {
			return;
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK  // if right clicked block
		&& player.getInventory().getItemInMainHand().getType().equals(Material.CREEPER_SPAWN_EGG)) { // while holding creeper spawn egg

			if (event.getClickedBlock().getType().equals(Material.getMaterial(plugin.getConfigClass().getCreeperSpawnBlock()))) { // and player is looking at the block specified in config 
				
				CustomCreeper creeper = determineCreeperType(player.getInventory().getItemInMainHand(),
															 gameManager.getGamePlayerFromPlayer(player), 
															 event.getClickedBlock().getLocation());
					
				WorldServer world =  ((CraftWorld) event.getPlayer().getWorld()).getHandle();
				world.addEntity(creeper);	
				gameManager.addCreepers(creeper);
				player.getInventory().removeItem(Utils.getEgg(1, creeper.getType()));						
	        }
			event.setCancelled(true);
		}
	}
	
	private void powerUpUseEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand() == null) {
			return;
		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
			GamePlayer gamePlayer = plugin.getGameManager().getGamePlayerFromPlayer(player);
            if (itemMeta.hasCustomModelData()){ // make sure player is holding something
            	 ItemStack item = player.getInventory().getItemInMainHand();
                 item.setAmount(1);
                 plugin.getPowerUpItemManager().activateItemPowerUp(item, gamePlayer);
                 player.getInventory().removeItem(item);
                 event.setCancelled(true);
                 return;
            }
            if (itemMeta.hasEnchant(Enchantment.KNOCKBACK) && gamePlayer.getCreeperCannon()) { // if it's a KnockBack stick and creeper cannon is enabled.
            	int eggSlot = player.getInventory().first(Material.CREEPER_SPAWN_EGG);
                if (eggSlot > 0) { // if there's an egg in player's inventory.
                	WorldServer world =  ((CraftWorld) event.getPlayer().getWorld()).getHandle();
                	Location adjustedLoc = new Location(player.getLocation().getWorld(),player.getLocation().getX() - 0.5D, player.getLocation().getY(),player.getLocation().getZ()-0.5D);
                	
                	CustomCreeper customCreeper = determineCreeperType(player.getInventory().getItem(eggSlot), gamePlayer, adjustedLoc);
                	CraftCreeper creeper = customCreeper.getCraftCreeper(); // accesses other properties of the creeper.
                	// create custom creeper equivalent to the first creeper egg found.
                	player.getInventory().removeItem(Utils.getEgg(1, customCreeper.getType()));
    				world.addEntity(customCreeper);	
    				
                	Vector knockback = player.getLocation().getDirection();
                	knockback.multiply(itemMeta.getEnchantLevel(Enchantment.KNOCKBACK));
                	creeper.setVelocity(knockback);
                	creeper.ignite();
                }
            }
        }  
	}
	
	private CustomCreeper determineCreeperType(ItemStack egg, GamePlayer gamePlayer, Location loc) {
		if (egg.getItemMeta().getDisplayName().contains("Charged")) {
			int ticks = plugin.getConfigClass().getChargedCreeperTicks();
			return new ChargedCreeper(gamePlayer, loc, ticks, "charged");
		}
		else if (egg.getItemMeta().getDisplayName().contains("Basic")) {
			int ticks = plugin.getConfigClass().getBasicCreeperTicks();
			return new BasicCreeper(gamePlayer, loc, ticks, "basic");
		}
		else {
			return new CustomCreeper(gamePlayer,loc,20,"normalCreeper");
		}
	}
}
