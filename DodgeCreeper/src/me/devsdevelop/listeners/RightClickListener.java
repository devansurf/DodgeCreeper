package me.devsdevelop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.powerup.items.PowerUpItem;

public class RightClickListener implements Listener {
	
	private DodgeCreeper plugin;
	
	public RightClickListener(DodgeCreeper plugin) { 
		this.plugin = plugin;
	}

	@EventHandler
    public void onRightClick(PlayerInteractEvent event){
		
        Player player = event.getPlayer();
        if (plugin.getGameManager().gameStarted) {
            if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) 
            		&& player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){
                plugin.getPowerUpItemManager().activateItemPowerUp(player.getInventory().getItemInMainHand(), player);   
               // calling PowerUpItemManager to sort what item ability to activate, using rigorous if statements
            }
        }
        else {
        	Bukkit.broadcastMessage("Game has not started");
        }
        
    }
}
