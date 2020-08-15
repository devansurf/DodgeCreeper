package me.devsdevelop.creepers;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.devsdevelop.game.GamePlayer;
import net.minecraft.server.v1_15_R1.ChatComponentText;



public class ChargedCreeper extends CustomCreeper{
	
	public ChargedCreeper(GamePlayer gamePlayer, Location loc, int ticks, String type) {
		super(gamePlayer, loc, ticks, type);

		this.setCustomName(new ChatComponentText(ChatColor.DARK_BLUE + "Charged Creeper"));
		this.setCustomNameVisible(true);	
		this.setPowered(true);		
	}
	
}
