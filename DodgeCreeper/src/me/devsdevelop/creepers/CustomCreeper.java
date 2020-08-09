package me.devsdevelop.creepers;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

import me.devsdevelop.gameplayer.GamePlayer;
import me.devsdevelop.gameplayer.TeamColor;
import net.minecraft.server.v1_15_R1.EntityCreeper;
import net.minecraft.server.v1_15_R1.EntityTypes;

public class CustomCreeper extends EntityCreeper{
	private GamePlayer gamePlayer;
	private TeamColor teamColor;
	private String type;
	
	public CustomCreeper(GamePlayer gamePlayer, Location loc, int ticks, String type) {
		
		super(EntityTypes.CREEPER,((CraftWorld)loc.getWorld()).getHandle());
		
		this.type = type;
		this.gamePlayer = gamePlayer;
		this.maxFuseTicks = ticks;
		teamColor = gamePlayer.getPlayerTeam().getTeamColor();
		this.setPosition(loc.getX()+0.5D, loc.getY()+1, loc.getZ()+0.5D);	// place in the middle of the block
		this.setHealth(500f);
	}
	
	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}
	public TeamColor getTeamColor() {
		return teamColor;
	}
	public String getType() {
		return type;
	}
	
}
