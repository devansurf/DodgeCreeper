package me.devsdevelop.creepers;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.devsdevelop.gameplayer.GamePlayer;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.PathfinderGoalRandomStroll;


public class BasicCreeper extends CustomCreeper{	
	
	public BasicCreeper(GamePlayer gamePlayer, Location loc, int ticks, String type) {	
		super(gamePlayer, loc, ticks, type);
		
		this.setCustomName(new ChatComponentText(ChatColor.GREEN + "Basic Creeper"));
		this.setCustomNameVisible(true);
		
		this.goalSelector.a(0, new PathfinderGoalRandomStroll(this, 0D));
																			
	}
}
