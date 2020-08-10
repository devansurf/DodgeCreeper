package me.devsdevelop.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.config.Config;
import me.devsdevelop.utils.Utils;

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
		if (!plugin.getGameManager().gameStarted || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		if (verticalLineAxis.equalsIgnoreCase("z")) {
			double zLoc = player.getLocation().getZ();
			if ((int)zLoc == (middleLineCoord + configClass.getRedCornerZ() 
				+ (int)plugin.getSchematicManager().getLocation().getZ())) {
				
				Location teleportLocation = 
						new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY(), 
								zLoc + playerOffset(zLoc), player.getLocation().getYaw(), player.getLocation().getPitch());
				player.teleport(teleportLocation);
				player.sendMessage(Utils.chat("&dYou are not allowed to cross to the other side."));
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
				player.sendMessage(Utils.chat("&dYou are not allowed to cross to the other side."));
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
