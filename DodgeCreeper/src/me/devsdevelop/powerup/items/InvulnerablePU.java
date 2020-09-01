package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class InvulnerablePU extends PowerUpItem{
	
	public InvulnerablePU(PowerUp powerUp, int amount) {
		super(powerUp, Material.BEDROCK, amount, Utils.chat("&fInvulnerability"));

	}

	@Override
	public void activatePowerUp(GamePlayer gamePlayer) {
		if (!super.isActive()) {
			Player player = gamePlayer.getPlayer();
			player.sendMessage(Utils.chat("&7=== &aActivated &fInvulnerability &7==="));
			player.getInventory().setArmorContents(new ItemStack[] {Utils.createCustomArmor(Material.LEATHER_BOOTS, "yellow", 0), 
				 	 Utils.createCustomArmor(Material.LEATHER_LEGGINGS, "yellow", 0),
				 	 Utils.createCustomArmor(Material.LEATHER_CHESTPLATE, "yellow", 0), 
				 	 Utils.createCustomArmor(Material.LEATHER_HELMET, "yellow", 0)});
			player.setInvulnerable(true);
		}
	}

	@Override
	public void removePowerUp(GamePlayer gamePlayer) {
		Player player = gamePlayer.getPlayer();
		player.sendMessage(Utils.chat("&7=== &4Your skin turns soft, you no longer possess the power of bedrock. &7==="));
		player.setInvulnerable(false);
	}

}
