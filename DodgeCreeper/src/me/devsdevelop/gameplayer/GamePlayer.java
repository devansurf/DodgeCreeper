package me.devsdevelop.gameplayer;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;


public class GamePlayer{

	private DodgeCreeper plugin;
	private Player player;
	private PlayerProfile profile;
	private PlayerTeam playerTeam;
	private Location spawnPoint;
	private boolean inArena;
	
	 public GamePlayer(Player player, PlayerProfile profile, PlayerTeam playerTeam, DodgeCreeper plugin) {
		this.plugin = plugin;
	    UUID uuid = player.getUniqueId();
	    
	    this.player = player;
	    this.profile = profile;
	    this.profile.setUniqueId(uuid);
	    this.playerTeam = playerTeam;
	    this.inArena = false;
	    this.spawnPoint = generateSpawnPoint();
	 }
	public Player getPlayer() {
		return player;
	}
	public PlayerTeam getPlayerTeam() {
		return playerTeam;
	}
	public void setArenaStatus(boolean value) {
		inArena = value;
	}
	public boolean getArenaStatus() {
		return inArena;
	}
	public Location getSpawnPoint() { 
		return spawnPoint;
	}
	public void teleportGamePlayer() {
		player.teleport(spawnPoint);
	}
	private Location generateSpawnPoint() {
		return plugin.getConfigClass().getArenaPlayerLocation(this);
	}
	
	
	
}
