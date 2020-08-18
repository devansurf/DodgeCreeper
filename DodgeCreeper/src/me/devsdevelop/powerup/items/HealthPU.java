package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class HealthPU extends PowerUpItem{

	public HealthPU(PowerUp powerUp, int amount) {
		super(powerUp, Material.APPLE, amount, Utils.chat("&cInstant Health"));
	}

	@Override
	public void activatePowerUp(Player player) {
	
		
	}

}
