package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class InvisiblePU extends PowerUpItem{

	
	public InvisiblePU(PowerUp powerUp, int amount) {
		super(powerUp, Material.GOLDEN_CARROT, amount,Utils.chat("&bInvisibility"));
	
	}

	@Override
	public void activatePowerUp(GamePlayer gamePlayer) {
		if (!super.isActive()) {
			Player player = gamePlayer.getPlayer();
			player.sendMessage(Utils.chat("&7=== &aActivated &bInvisibility &7==="));
			player.getInventory().setArmorContents(new ItemStack[] {null,null,null,null}); // removes all armor of the player
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0)); // time in ticks, amplifier (starting at 0)
		}
	}

	@Override
	public void removePowerUp(GamePlayer gamePlayer) {
		Player player = gamePlayer.getPlayer();
		player.sendMessage(Utils.chat("&7=== &4You no longer reflect light, you are now visible. &7==="));
		player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
	}
}
