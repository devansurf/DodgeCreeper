package me.devsdevelop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.config.Config;

public class PlayerMoveListener implements Listener{
	private DodgeCreeper plugin;
	private Config configClass;
	private String verticalLineAxis;
	private int middleLineCoord;
	
	public PlayerMoveListener(DodgeCreeper plugin) {
		this.plugin = plugin;
		configClass = plugin.getConfigClass();
		verticalLineAxis = configClass.getVerticalAxis();
		middleLineCoord = configClass.getMiddleLine();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (verticalLineAxis.equalsIgnoreCase("z")) {
			Bukkit.broadcastMessage("THE LOCATION OF THE BARRIER IS: " + (middleLineCoord + (int)plugin.getSchematicManager().getLocation().getZ() + configClass.getRedCornerZ()));
			double zLoc = player.getLocation().getZ();
			if ((int)zLoc == (middleLineCoord + configClass.getRedCornerZ() 
				+ (int)plugin.getSchematicManager().getLocation().getZ())) {
				
				Location teleportLocation = 
						new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY(), 
								zLoc + playerOffset(zLoc), player.getLocation().getYaw(), player.getLocation().getPitch());
				
				player.teleport(teleportLocation);
			}
		}
		else {
			double xLoc = player.getLocation().getX();
			if ((int)player.getLocation().getX() == (middleLineCoord + configClass.getRedCornerX() 
				+ (int)plugin.getSchematicManager().getLocation().getX())) {	
				Location teleportLocation = 
						new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY(), 
								xLoc + playerOffset(xLoc), player.getLocation().getYaw(), player.getLocation().getPitch());
				
				player.teleport(teleportLocation);
				event.setCancelled(true);
			}
		}
		
	}
	
	private int playerOffset(double coordinate) {
		int offset = 0;
		//check whether the nearest block is in the negative coordinate or positive coordinate.
		if (Math.ceil(Math.abs(coordinate)) - Math.abs(coordinate) >  Math.abs(coordinate) - Math.floor(Math.abs(coordinate))) { 
			//If true, it means that it's closer to the positive coordinate.
			offset = 1;		
		}
		else {
			offset = -1;
		}
		return offset;
	}
}
