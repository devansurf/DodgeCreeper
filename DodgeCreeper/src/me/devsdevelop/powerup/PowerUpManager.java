package me.devsdevelop.powerup;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.reflect.ConstructorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.items.CannonPU;
import me.devsdevelop.powerup.items.HealthPU;
import me.devsdevelop.powerup.items.InvisiblePU;
import me.devsdevelop.powerup.items.InvulnerablePU;
import me.devsdevelop.powerup.items.PowerUpItem;
import me.devsdevelop.powerup.items.SpeedPU;



public class PowerUpManager {
	
	private DodgeCreeper plugin;
	
	private ArrayList<PowerUpBlockGroup> powerUps = new ArrayList<PowerUpBlockGroup>();
	private Random rand = new Random();
	private double probability;  // should hold a value <= 1
	private double growth;
	private int retrys = 0;
	
	public PowerUpManager(DodgeCreeper plugin) {
		this.plugin = plugin;
		probability = plugin.getConfigClass().getPowerUpProbability();
		growth = plugin.getConfigClass().getPowerUpGrowth();
	}
	
	public void spawnPowerUp() {
		
		double roll = rand.nextDouble();
		if (roll <= probability + (growth * retrys)) { // probability increases for every retry
			powerUps.add(new PowerUpBlockGroup(generateRandomArenaSpawn(), getRandomPowerUp()));
			retrys = 0;
		}
		else {
			retrys++;
		}
		
	}
	
	public void checkCollectedPowerUps() {

		 ArrayList<GamePlayer> gamePlayers = plugin.getGameManager().getGamePlayers();
		 if (powerUps.isEmpty()) {
			 return;
		 }
		 
		 for (GamePlayer gamePlayer : gamePlayers) {
			 int i = 0;
			 for (PowerUpBlockGroup powerUpBlockGroup : powerUps) {
				 
				 Player player = gamePlayer.getPlayer();
				 Location playerLoc = gamePlayer.getPlayer().getLocation();
				 Location blockLoc = powerUpBlockGroup.getLocation();
				 
				 if (playerLoc.getBlockX() == blockLoc.getBlockX()
						 && playerLoc.getBlockZ() == blockLoc.getBlockZ()) {
					 player.getInventory().addItem(getPowerUpItemStack(powerUpBlockGroup.getPowerUp()));
					 player.updateInventory();
					 powerUpBlockGroup.revertBlockGroup();
					 powerUps.remove(i);  // remove blockGroup from list.
					 break;
				 }
				 i++;	 
			 }
		 }	 
	}
	
	public void removeAllPowerUps() {
		for (PowerUpBlockGroup powerUpBlockGroup : powerUps) {
			powerUpBlockGroup.revertBlockGroup();
		}
	}
	
	private Location generateRandomArenaSpawn() {
		Location arenaLoc = plugin.getDataManager().getArenaLocation();
		
		int cornerX = arenaLoc.getBlockX() + plugin.getConfigClass().getRedCornerX();
		int cornerZ = arenaLoc.getBlockZ() + plugin.getConfigClass().getRedCornerZ();
		
		int x = getNumberInRange(cornerX, cornerX +  plugin.getConfigClass().getArenaWidth()); // choose coordinates within the arena parameters
		int y = arenaLoc.getBlockY() + plugin.getConfigClass().getSpawnHeight();
		int z = getNumberInRange(cornerZ, cornerZ +  plugin.getConfigClass().getArenaHeight());
		Bukkit.broadcastMessage("x: " + x + " y: " + y + " z: " + z);
		return new Location(plugin.getDataManager().getWorld(),x,y,z);
	}
	
	private PowerUp getRandomPowerUp() {
		return PowerUp.values()[new Random().nextInt(PowerUp.values().length)];
	}
	
	private int getNumberInRange(int min, int max) { 
		int sign = 1;
		
		if ((max - min) + min < 0 ) { // allows for random to work with negative numbers.
			sign = -1;
		}
	    return (rand.nextInt(Math.abs(Math.abs(max) - Math.abs(min))) + Math.abs(min)) * sign;
	}
	private ItemStack getPowerUpItemStack(PowerUp powerUp) {	// reflection proved to be too complicated, for now we will resort to this.	
		PowerUpItem item;
			switch(powerUp) {		
				case CANNON:
					item = new CannonPU(powerUp,1);
					break;
				case HEALTH:
					item = new HealthPU(powerUp,1);	
					break;
				case INVISIBLE:
					item = new InvisiblePU(powerUp,1);
					break;
				case INVULNERABLE:
					item = new InvulnerablePU(powerUp,1);
					break;
				case SPEED:
					item = new SpeedPU(powerUp,1);
					break;
				default:
					item = new HealthPU(powerUp,99);
			}
			return item;
	}
}
