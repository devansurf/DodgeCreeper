package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class InvisiblePU extends PowerUpItem{

	public InvisiblePU(PowerUp powerUp, int amount) {
		super(powerUp, Material.GOLDEN_CARROT, amount,Utils.chat("&bInvisibility"));
	
	}

	@Override
	public void activatePowerUp(Player player) {
	
		
	}

}
