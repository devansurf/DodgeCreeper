package me.devsdevelop.config;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;


import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.game.TeamColor;

public class Config {
	
	private DodgeCreeper plugin;
	
	public Config (DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	

	
	public Location getArenaPlayerLocation(GamePlayer gamePlayer) {
		Random rand = new Random();
		
		Location arenaLocation = plugin.getDataManager().getArenaLocation();
		Location spawnPoint;
		int xLeft, xRight;
		int spawnOffset;
		int z; // z does not change for team for the same team
		int y = getSpawnHeight();
		float yaw = 0;
		
		
		if (gamePlayer.getPlayerTeam().getTeamColor().equals(TeamColor.BLUE)) {
			xLeft = plugin.getConfig().getInt("BArena.bluecorner-L.getX");
			xRight= plugin.getConfig().getInt("BArena.bluecorner-R.getX");
			z = plugin.getConfig().getInt("BArena.bluecorner-L.getZ");	
			spawnOffset = plugin.getConfig().getInt("BArena.bluespawnoffset");
			yaw = plugin.getConfig().getInt("BArena.blueyaw");
			
			int difference = Math.abs(xRight - xLeft);
			int rand_x = rand.nextInt(difference); // generates a number up to the size of the spawn area in the x coordinate (width)
			
			spawnPoint = new Location(arenaLocation.getWorld(),
					arenaLocation.getBlockX() + xLeft - rand_x,   // X offset and X random
					arenaLocation.getBlockY() + y,   // Y
					arenaLocation.getBlockZ() + z + spawnOffset);  // Z offset and Z constant (to spawn in center of spawn area)	
		}
		else if (gamePlayer.getPlayerTeam().getTeamColor().equals(TeamColor.RED)){ // copied code expect random needs to be positive so a minus sign was added (SLOPPY)
			xLeft = plugin.getConfig().getInt("BArena.redcorner-L.getX");
			xRight= plugin.getConfig().getInt("BArena.redcorner-R.getX");
			z = plugin.getConfig().getInt("BArena.redcorner-L.getZ");
			spawnOffset = plugin.getConfig().getInt("BArena.redspawnoffset");
			yaw = plugin.getConfig().getInt("BArena.redyaw");
			
			int difference = xRight - xLeft;
			int rand_x = rand.nextInt(difference); // generates a number up to the size of the spawn area in the x coordinate (width)
			
			spawnPoint = new Location(arenaLocation.getWorld(),
					arenaLocation.getBlockX() + xLeft + rand_x,   // X offset and X random
					arenaLocation.getBlockY() + y,   // Y
					arenaLocation.getBlockZ() + z + spawnOffset);  // Z offset and Z constant (to spawn in center of spawn area)	
			
		}
		else {
			Bukkit.broadcastMessage("Error assigning GamePlayer to the proper team, SpawnPoint not assigned.");
			return null;
		}
		
		spawnPoint.setYaw(yaw);
		return spawnPoint;
	}
	
	public int getChargedCreeperTicks() {return plugin.getConfig().getInt("creeper.chargedcreeper.tick");}
	public int getBasicCreeperTicks() {return plugin.getConfig().getInt("creeper.basiccreeper.tick");}
	
	public int getKnockbackStickValue() {return plugin.getConfig().getInt("item.stick.knockback");}
	
	
	public int getRedCornerZ() {return plugin.getConfig().getInt("BArena.redcorner-L.getZ");}
	public int getRedCornerX() {return plugin.getConfig().getInt("BArena.redcorner-L.getX");}
	public int getBlueCornerZ() {return plugin.getConfig().getInt("BArena.bluecorner-L.getZ");}
	public int getBlueCornerX() {return plugin.getConfig().getInt("BArena.bluecorner-L.getX");}
	
	public int getSpawnHeight() {return plugin.getConfig().getInt("BArena.spawnheight");}
	
	public int getArenaWidth() {
		return getBlueCornerX() - getRedCornerX();
	}
	public int getArenaHeight() {
		return getBlueCornerZ() - getRedCornerZ();
	}
	public int getMiddleLine() {return ((getBlueCornerZ() - getRedCornerZ())/2)-2 ;}
	
	public int getChargedCreeperTimer() {return plugin.getConfig().getInt("creeper.chargedcreeper.eggtimer");}
	public int getBasicCreeperTimer() {return plugin.getConfig().getInt("creeper.basiccreeper.eggtimer");}
	public int getChargedCreeperAmount() {return plugin.getConfig().getInt("creeper.chargedcreeper.amount");}
	public int getBasicCreeperAmount() {return plugin.getConfig().getInt("creeper.basiccreeper.amount");}
	
	public int getArmorLevel() {return plugin.getConfig().getInt("item.armor.enchant");}
	
	public int getMaxEggs() {return plugin.getConfig().getInt("item.egg.amount");}
	
	public int getPowerUpSpawnTimer() {return plugin.getConfig().getInt("item.powerup.timer");}
	public int getPowerUpCheckTimer() {return plugin.getConfig().getInt("item.powerup.check");}
	public int getPowerUpStayTimer() {return plugin.getConfig().getInt("item.powerup.stay");}
	public double getPowerUpProbability() {return plugin.getConfig().getDouble("item.powerup.probability", 0.1D);}
	public double getPowerUpGrowth() {return plugin.getConfig().getDouble("item.powerup.growth", 0.1D);}
	
	public String getKnockbackStickName() {return plugin.getConfig().getString("item.stick.name");}
	public String getKnockbackStickDescription() {return plugin.getConfig().getString("item.stick.description");}
	public String getCreeperSpawnBlock() {return plugin.getConfig().getString("block.creeperspawn");}
	public String getVerticalAxis() {return plugin.getConfig().getString("BArena.verticalaxis");}
	public String getMiddleLineBlock() {return plugin.getConfig().getString("BArena.middlelineblock");}
	
	
}
