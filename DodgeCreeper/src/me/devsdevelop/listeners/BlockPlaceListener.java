package me.devsdevelop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.devsdevelop.DodgeCreeper;

public class BlockPlaceListener implements Listener{

	private DodgeCreeper plugin;
	public BlockPlaceListener(DodgeCreeper plugin) { 
		this.plugin = plugin;
	}

	@EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
		if (plugin.getGameManager().gameStarted) {
			event.setCancelled(true);
		}    
    }
}
