package me.devsdevelop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.CustomCreeper;

public class OnHitCreeperListener implements Listener{

private DodgeCreeper plugin;
	
	public OnHitCreeperListener(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {

		if (!(event.getDamager() instanceof Player) || !(event.getEntity().getCustomName().contains("Creeper"))) { //if damager is not a player or if the damaged is not a charged creeper then ignore.
			return;
		}
		Player player = (Player) event.getDamager();
		for (CustomCreeper creeper : plugin.getGameManager().getCreepers()) { // for each existing creeper in game
			if (creeper.getUniqueID().equals(event.getEntity().getUniqueId())) {
				if (player.getInventory().getItemInMainHand().getType() == Material.STICK){ // if creeper got hit by a stick
					creeper.ignite();	 // doesn't matter if it gets re-ignited	
					plugin.getGameManager().getCreepers().remove(creeper);
				}		
			}
			
		}	
	}
}
