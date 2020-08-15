package me.devsdevelop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.CustomCreeper;

public class CreeperTargetListener implements Listener{

	private DodgeCreeper plugin;
	
	public CreeperTargetListener(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent event) {  // NOT VERY HAPPY WITH THIS BUT IT WORKS, creates a behavior for the creeper to attack the enemy team and not its own.
		if (!(event.getEntity().getCustomName().contains("Creeper")) || !(event.getTarget() instanceof Player)){ // if its not a creeper or the attacker isn't a player, then ignore.
			return;
		}	
		
		for (CustomCreeper creeper : plugin.getGameManager().getCreepers()) { // for each existing creeper in game
			if (creeper.getUniqueID().equals(event.getEntity().getUniqueId())) {
					
				if (creeper.getGamePlayer().getPlayerTeam().containsMember((Player)event.getTarget())) { //check if this entity is this Object, and if so check if it's attacking its own team.
					event.setCancelled(true); // if the creeper is attacking its team then cancel the event.
					return;
				}
				else if (creeper.getGamePlayer().getPlayerTeam().containsMember((Player)event.getTarget())){ // if enemy team then ignite
					creeper.ignite();
					return;
				}
			}
		}
	}
}
