package me.devsdevelop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.devsdevelop.DodgeCreeper;

public class BlockBreakListener implements Listener{
	
	private DodgeCreeper plugin;
	
	public BlockBreakListener(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak (BlockBreakEvent event) {
		if (plugin.getGameManager().gameStarted) {
			event.setCancelled(true);
		}
	}

}
