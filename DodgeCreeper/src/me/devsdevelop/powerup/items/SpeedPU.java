package me.devsdevelop.powerup.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class SpeedPU extends PowerUpItem{

	public SpeedPU(PowerUp powerUp, int amount) {	
		super(powerUp, Material.SUGAR, amount, Utils.chat("&eSpeed Boost"));

	}

	@Override
	public void activatePowerUp(Player player) {
		Bukkit.broadcastMessage("Activated speed");
		
	}

}
