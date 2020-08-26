package me.devsdevelop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.DodgeCreeper;

public class RightClickListener implements Listener {
	
	private DodgeCreeper plugin;
	
	public RightClickListener(DodgeCreeper plugin) { 
		this.plugin = plugin;
	}

	@EventHandler
    public void onRightClick(PlayerInteractEvent event){
		
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null) {
			return;
		}
        if (plugin.getGameManager().gameStarted) {
            if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) 
            		&& player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){ // make sure player is holding something
            	
            	ItemStack item = player.getInventory().getItemInMainHand();
            	item.setAmount(1);
                plugin.getPowerUpItemManager().activateItemPowerUp(item, player);
                player.getInventory().removeItem(item);
                event.setCancelled(true); 	
            }
        }        
    }
}
