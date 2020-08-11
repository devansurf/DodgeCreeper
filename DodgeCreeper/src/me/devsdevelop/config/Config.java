package me.devsdevelop.config;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;


import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.gameplayer.GamePlayer;
import me.devsdevelop.gameplayer.TeamColor;

public class Config {
	
	private DodgeCreeper plugin;
	
	public Config (DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	

	
	public Location getArenaPlayerLocation(GamePlayer gamePlayer) {
		Random rand = new Random();
		
		Location arenaLocation = plugin.getDataManager().getArenaLocation(gamePlayer.getPlayer());
		Location spawnPoint;
		int xLeft, xRight;
		int spawnOffset;
		int z; // z does not change for team for the same team
		int y = plugin.getConfig().getInt("BArena.spawnheight");
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
					arenaLocation.getX() + xLeft - rand_x,   // X offset and X random
					arenaLocation.getY() + y,   // Y
					arenaLocation.getZ() + z + spawnOffset);  // Z offset and Z constant (to spawn in center of spawn area)	
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
					arenaLocation.getX() + xLeft + rand_x,   // X offset and X random
					arenaLocation.getY() + y,   // Y
					arenaLocation.getZ() + z + spawnOffset);  // Z offset and Z constant (to spawn in center of spawn area)	
			
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
	public int getMiddleLine() {return plugin.getConfig().getInt("BArena.middleline");}
	public int getRedCornerZ() {return plugin.getConfig().getInt("BArena.redcorner-L.getZ");}
	public int getRedCornerX() {return plugin.getConfig().getInt("BArena.redcorner-L.getX");}
	public int getBlueCornerZ() {return plugin.getConfig().getInt("BArena.bluecorner-L.getZ");}
	public int getBlueCornerX() {return plugin.getConfig().getInt("BArena.bluecorner-L.getX");}
	public int getChargedCreeperTimer() {return plugin.getConfig().getInt("creeper.chargedcreeper.eggtimer");}
	public int getBasicCreeperTimer() {return plugin.getConfig().getInt("creeper.basiccreeper.eggtimer");}
	public int getChargedCreeperAmount() {return plugin.getConfig().getInt("creeper.chargedcreeper.amount");}
	public int getBasicCreeperAmount() {return plugin.getConfig().getInt("creeper.basiccreeper.amount");}
	public int getArmorLevel() {return plugin.getConfig().getInt("item.armor.enchant");}
	public int getMaxEggs() {return plugin.getConfig().getInt("item.egg.amount");}
	public String getKnockbackStickName() {return plugin.getConfig().getString("item.stick.name");}
	public String getKnockbackStickDescription() {return plugin.getConfig().getString("item.stick.description");}
	public String getCreeperSpawnBlock() {return plugin.getConfig().getString("block.creeperspawn");}
	public String getVerticalAxis() {return plugin.getConfig().getString("BArena.verticalaxis");}
	public String getMiddleLineBlock() {return plugin.getConfig().getString("BArena.middlelineblock");}
	
	
}
