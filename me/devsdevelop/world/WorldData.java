package me.devsdevelop.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;

public class WorldData {

	private DodgeCreeper plugin;
	
	public WorldData(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	public void setWorldData(Player player) { // save arena location, and unique world id.
		String worldId = player.getWorld().getUID().toString();
		Location arenaLoc = plugin.getSchematicManager().getLocation();
		plugin.getDataManager().setWorldUID(worldId); // set world id
		plugin.getDataManager().setArena(true); // set arena to true
		plugin.getDataManager().setArenaLocation(arenaLoc.getBlockX(), arenaLoc.getBlockY(),(arenaLoc.getBlockZ()));
		plugin.getDataManager().setArenaRectangle(arenaLoc.getBlockX() + plugin.getConfigClass().getRedCornerX(), 
												  arenaLoc.getBlockY() + plugin.getConfigClass().getRedCornerZ(), 
												  plugin.getConfigClass().getArenaWidth(),
												  plugin.getConfigClass().getArenaHeight());
	
	}
	public boolean worldChanged(Player player) {
		String worldId = player.getWorld().getUID().toString();
		if (plugin.getDataManager().getConfig().contains("world.uuid")) { // if the path exists for worldId
			if (plugin.getDataManager().getWorldUID().equals(worldId)) { //check if this world is the same as the saved one. 
				return false;
			}
			plugin.getDataManager().setArena(false);
		}
		return true;
	}
}
