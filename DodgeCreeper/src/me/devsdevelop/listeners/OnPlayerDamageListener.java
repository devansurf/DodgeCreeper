package me.devsdevelop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.devsdevelop.utils.Utils;

public class OnPlayerDamageListener implements Listener{

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		if (event.getDamage() >= player.getHealth()) {
			player.setGameMode(GameMode.SPECTATOR);
			Bukkit.broadcastMessage(Utils.chat("&aThe player &6" + player.getName() + " has been eliminated"));
		}
		
		

	}
}
