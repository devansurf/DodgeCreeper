package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class HealthPU extends PowerUpItem{

	public HealthPU(PowerUp powerUp, int amount) {
		super(powerUp, Material.APPLE, amount, Utils.chat("&cHealth Regeneration"));
	}

	@Override
	public void activatePowerUp(Player player) {
		if (!super.isActive()) {
			player.sendMessage(Utils.chat("&7=== &aActivated &cHealth Regeneration &7==="));
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1)); // time in ticks, amplifier (starting at 0)
		}
	
	}

	@Override
	public void removePowerUp(Player player) {
		player.sendMessage(Utils.chat("&7=== &4Your &cHealth Regeneration &4fades away... &7==="));
		player.removePotionEffect(PotionEffectType.REGENERATION);
	}
}
