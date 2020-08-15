package me.devsdevelop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.devsdevelop.utils.Utils;
import me.devsdevelop.utils.sound.SoundManager;
import me.devsdevelop.utils.sound.SoundType;

public class OnPlayerDamageListener implements Listener{

	@EventHandler
	public void onPlayerDamage(PlayerDeathEvent event) {
		Player player = (Player)event.getEntity();
		player.setHealth(20f);
		player.setGameMode(GameMode.SPECTATOR);
		SoundManager.sendSound(player, player.getLocation(), SoundType.BLEED);	
		Bukkit.broadcastMessage(Utils.chat("&aThe player &6" + player.getName() + " &ahas been eliminated"));
//		if (event.getDamage() >= player.getHealth()) {
//			player.setGameMode(GameMode.SPECTATOR);
//			Bukkit.broadcastMessage(Utils.chat("&aThe player &6" + player.getName() + " &ahas been eliminated"));
//			SoundManager.sendSound(player, player.getLocation(), SoundType.BLEED);
//			event.setCancelled(true);
//		}
		
		

	}
}
