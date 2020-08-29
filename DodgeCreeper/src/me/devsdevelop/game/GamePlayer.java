package me.devsdevelop.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.powerup.items.PowerUpItem;


public class GamePlayer{

	private DodgeCreeper plugin;
	private Player player;
	private PlayerProfile profile;
	private PlayerTeam playerTeam;
	private Location spawnPoint;
	private List<GamePlayerCooldown> cooldowns = new ArrayList<GamePlayerCooldown>();
	private boolean creeperCannonStatus = false;
	private boolean inArena = false;
	
	 public GamePlayer(Player player, PlayerProfile profile, PlayerTeam playerTeam, DodgeCreeper plugin) {
		this.plugin = plugin;
	    UUID uuid = player.getUniqueId();
	    
	    this.player = player;
	    this.profile = profile;
	    this.profile.setUniqueId(uuid);
	    this.playerTeam = playerTeam;
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
	public void setCreeperCannon(boolean value) {
		creeperCannonStatus = value;
	}
	public boolean getCreeperCannon() {
		return creeperCannonStatus;
	}
	
	public void addGamePlayerCooldown(PowerUpItem powerUpItem, long time) { // called by PowerUpItemManager
		GamePlayerCooldown cooldown = hasPowerUp(powerUpItem.getPowerUp());
		
		if (cooldown == null) { // if a new powerUp is obtained, add it to the list of the players powerUps
			cooldowns.add(new GamePlayerCooldown(powerUpItem, time));
			return;
		}
		cooldown.setCooldown(cooldown.getCooldown() + time); // if the same powerUp is obtained, extend the timer.
	}
	
	public void subtractCooldowns(long ticks) { //called by PowerUpManager, repeats every so often through the PowerUpScheduler.
		if (cooldowns.isEmpty()) {
			return;
		}		
		for (GamePlayerCooldown gamePlayerCooldown : cooldowns) {
			gamePlayerCooldown.reduceCooldownByAmount(ticks);
		}
	}
	
	public void removeInactiveCooldowns() { 	// called right after substractCooldowns is called
		if (cooldowns.isEmpty()) {
			return;
		}
		for (GamePlayerCooldown gamePlayerCooldown : cooldowns) {
			if (!gamePlayerCooldown.hasCooldown()) { 	// if there is no cooldown left, remove it.
				PowerUpItem powerUpItem = gamePlayerCooldown.getPowerUpItem();
				powerUpItem.setActive(false);
				powerUpItem.removePowerUp(this); 
				cooldowns.remove(gamePlayerCooldown);
				getPlayer().getInventory().setArmorContents(playerTeam.getTeamArmor(plugin.getConfigClass().getArmorLevel())); // when a powerUp is removed, make sure armor is set back.
				break; 	/* break to prevent looping between null values. 2 powerUps can't be collected at same time, 
						  therefore to continue looping would be unnecessary. */
			}
		}
	}
	public void removeCooldowns() {
		if (cooldowns.isEmpty()) {
			return;
		}
		for (GamePlayerCooldown gamePlayerCooldown : cooldowns) {
			PowerUpItem powerUpItem = gamePlayerCooldown.getPowerUpItem();
			powerUpItem.setActive(false);
			powerUpItem.removePowerUp(this); 	
		}
		cooldowns.clear();
	}
	
	private GamePlayerCooldown hasPowerUp(PowerUp powerUp) {
		for (GamePlayerCooldown gamePlayerCooldown : cooldowns) {
			if (gamePlayerCooldown.getPowerUpItem().getPowerUp().equals(powerUp)) 
				return gamePlayerCooldown;		
		}
		return null;
	}
	

	
	private Location generateSpawnPoint() {
		return plugin.getConfigClass().getArenaPlayerLocation(this);
	}
	
	
	
}
