package me.devsdevelop.powerup;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import me.devsdevelop.DodgeCreeper;

public class PowerUpManager {
	
	private DodgeCreeper plugin;
	
	public PowerUpManager(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	public void createBeacon(Location location) {
	    int x = location.getBlockX();
	    int y = location.getBlockY() - 30;
	    int z = location.getBlockZ();
	 
	    World world = location.getWorld();
	 
	    world.getBlockAt(x, y, z).setType(Material.BEACON);
	    for (int i = 0; i <= 29; ++i) world.getBlockAt(x, (y + 1) + i, z).setType(Material.ORANGE_STAINED_GLASS);
	    for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) { 
	        for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {            
	            world.getBlockAt(xPoint, y-1, zPoint).setType(Material.IRON_BLOCK);
	            // bruh
	        }
	    } 
	}
}
