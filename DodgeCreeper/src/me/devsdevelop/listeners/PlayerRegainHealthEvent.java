package me.devsdevelop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerRegainHealthEvent implements Listener{
	@EventHandler
	public void onPlayerRegenerate(EntityRegainHealthEvent event) {
		//event.setCancelled(true);
	}
}
