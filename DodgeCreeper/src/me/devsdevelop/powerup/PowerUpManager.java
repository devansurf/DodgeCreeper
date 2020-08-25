package me.devsdevelop.powerup;

import java.util.ArrayList;
import java.util.Random;

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
import me.devsdevelop.utils.Utils;
import me.devsdevelop.utils.sound.SoundManager;
import me.devsdevelop.utils.sound.SoundType;



public class PowerUpManager {
	
	private DodgeCreeper plugin;
	
	private ArrayList<PowerUpBlockGroup> powerUpBlocks = new ArrayList<PowerUpBlockGroup>();
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
			PowerUpBlockGroup powerUpBlockGroup = new PowerUpBlockGroup(generateRandomArenaSpawn(0), getRandomPowerUp());
			if (powerUpBlockGroup != null) {
				powerUpBlocks.add(powerUpBlockGroup);
				Bukkit.broadcastMessage(Utils.chat("&dA &bPower Up &dhas spawned&c!"));
			}
			retrys = 0;
		}
		else {
			retrys++;
		}
		
	}
	
	public void checkCollectedPowerUps(double ticks) {

		 ArrayList<GamePlayer> gamePlayers = plugin.getGameManager().getGamePlayers();
		 if (powerUpBlocks.isEmpty()) {
			 return;
		 }
		 
		 for (GamePlayer gamePlayer : gamePlayers) {
			 
			 gamePlayer.subtractCooldowns(ticks); //any existing cooldowns are subtracted over time, and also removed if needed.
			 
			 for (int i = 0; i < powerUpBlocks.size(); i++) {
				 
				 Player player = gamePlayer.getPlayer();
				 Location playerLoc = gamePlayer.getPlayer().getLocation();
				 Location blockLoc = powerUpBlocks.get(i).getLocation();
				 
				 if (playerLoc.getBlockX() == blockLoc.getBlockX()
						 && playerLoc.getBlockZ() == blockLoc.getBlockZ()) {
					 
					 player.getInventory().addItem(getPowerUpItemStack(powerUpBlocks.get(i).getPowerUp())); // add the powerUp to the player's inventory
					 player.updateInventory();
					 SoundManager.sendSound(player, player.getLocation(), SoundType.POP);
					 
					 powerUpBlocks.get(i).revertBlockGroup(); // revert the block changes on the world.
					 powerUpBlocks.remove(i);  // remove blockGroup from list.
					 break;
				 } 
			 }
		 }	 
	}
	
	public void removeAllPowerUps() {
		for (PowerUpBlockGroup powerUpBlockGroup : powerUpBlocks) {
			powerUpBlockGroup.revertBlockGroup();
		}
	}
	
	private Location generateRandomArenaSpawn(int retrys) {
		Location arenaLoc = plugin.getDataManager().getArenaLocation();
		
		int cornerX = arenaLoc.getBlockX() + plugin.getConfigClass().getRedCornerX();
		int cornerZ = arenaLoc.getBlockZ() + plugin.getConfigClass().getRedCornerZ();
		
		int x = getRandomArenaCoord(cornerX, plugin.getConfigClass().getArenaWidth()); // choose coordinates within the arena parameters
		int y = arenaLoc.getBlockY() + plugin.getConfigClass().getSpawnHeight();
		int z = getRandomArenaCoord(cornerZ, plugin.getConfigClass().getArenaHeight());
		Location generatedLoc = new Location(plugin.getDataManager().getWorld(),x,y,z);
		for (PowerUpBlockGroup powerUpBlockGroup : powerUpBlocks) {
			if (powerUpBlockGroup.getLocation().getBlockX() == generatedLoc.getBlockX() 
					&& powerUpBlockGroup.getLocation().getBlockZ() == generatedLoc.getBlockZ()) { // if location was repeated, ignore this generation.
				if (retrys > 10) {
					return null;
				}
				else {
					return generateRandomArenaSpawn(retrys); // call function again to get a new Location.
				}
			}
		}
		return generatedLoc;
	}
	
	private PowerUp getRandomPowerUp() {
		return PowerUp.values()[new Random().nextInt(PowerUp.values().length)];
	}
	
	private int getRandomArenaCoord(int corner, int maxOffset) { 
		int randInt = rand.nextInt(Math.abs(maxOffset)); // get a random number within the parameters of the arena.
		int randOffset;
		if (maxOffset > 0) {
			randOffset = maxOffset - randInt; // returns a positive number smaller or equal to maxOffset
		}
		else { // if negative
			randOffset = maxOffset + randInt; // returns a negative number larger or equal to maxOffset
		}
		return corner + randOffset;
	}
	
	private ItemStack getPowerUpItemStack(PowerUp powerUp) {	// reflection proved to be too complicated, for now we will resort to this.	
		PowerUpItem item;
			switch(powerUp) {		
//				case CANNON:
//					item = new CannonPU(powerUp,1);
//					break;
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
