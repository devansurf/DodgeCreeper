package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class SpeedPU extends PowerUpItem{

	public SpeedPU(PowerUp powerUp, int amount) {	
		super(powerUp, Material.SUGAR, amount, Utils.chat("&eSpeed Boost"));

	}

	@Override
	public void activatePowerUp(Player player) {
		if (!super.isActive()) {
			player.sendMessage(Utils.chat("&7=== &aActivated &eSpeed Boost&7 ==="));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 9));
		}
	}

	@Override
	public void removePowerUp(Player player) {
		player.sendMessage(Utils.chat("&7=== &4Your legs start to feel heavy... &7==="));
		player.removePotionEffect(PotionEffectType.SPEED);
		
	}

}
