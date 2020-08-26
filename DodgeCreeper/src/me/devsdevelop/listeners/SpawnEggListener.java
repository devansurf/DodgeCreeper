package me.devsdevelop.listeners;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.BasicCreeper;
import me.devsdevelop.creepers.ChargedCreeper;
import me.devsdevelop.creepers.CustomCreeper;
import me.devsdevelop.game.GameManager;
import me.devsdevelop.utils.Utils;
import net.minecraft.server.v1_15_R1.WorldServer;


public class SpawnEggListener implements Listener{
	
	private DodgeCreeper plugin;
	
	private ItemStack removeBasic;
	private ItemStack removeCharged;
	public SpawnEggListener(DodgeCreeper plugin) {
		this.plugin = plugin;
		removeBasic = Utils.getEgg(1, "basic");
		removeCharged = Utils.getEgg(1, "charged");
	}
	
	@EventHandler
	public void onPlaceSpawnEgg(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		GameManager gameManager = plugin.getGameManager();
		if (player.getInventory().getItemInMainHand() == null) {
			return;
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK  // if right clicked block
		&& player.getInventory().getItemInMainHand().getType().equals(Material.CREEPER_SPAWN_EGG)) { // while holding creeper spawn egg
			if (!gameManager.gameStarted) { // Cancel any creeper spawns if game hasn't started
				event.setCancelled(true);
				return;
			}
			if (event.getClickedBlock().getType().equals(Material.getMaterial(plugin.getConfigClass().getCreeperSpawnBlock()))) { // and player is looking at the block specified in config 
				
				if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Charged")) {
					
					CustomCreeper creeper = new ChargedCreeper(gameManager.getGamePlayerFromPlayer(player),
							event.getClickedBlock().getLocation(), plugin.getConfigClass().getChargedCreeperTicks(), "charged");
					
					WorldServer world =  ((CraftWorld) event.getPlayer().getWorld()).getHandle();
					world.addEntity(creeper);	
					gameManager.addCreepers(creeper);
					player.getInventory().removeItem(removeCharged);
				}
				else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Basic")) {
					
					CustomCreeper creeper = new BasicCreeper(gameManager.getGamePlayerFromPlayer(player), 			
							event.getClickedBlock().getLocation(), plugin.getConfigClass().getBasicCreeperTicks(), "basic");
					
					WorldServer world =  ((CraftWorld) event.getPlayer().getWorld()).getHandle();
					world.addEntity(creeper);
					gameManager.addCreepers(creeper);
					player.getInventory().removeItem(removeBasic);
				}		
	        }
			event.setCancelled(true);
		}
		
	}
	
}
