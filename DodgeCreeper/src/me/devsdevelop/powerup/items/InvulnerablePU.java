package me.devsdevelop.powerup.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class InvulnerablePU extends PowerUpItem{

	public InvulnerablePU(PowerUp powerUp, int amount) {
		super(powerUp, Material.BEDROCK, amount, Utils.chat("&fInvulnerability"));

	}

	@Override
	public void activatePowerUp(Player player) {
		Bukkit.broadcastMessage("Activated Invulnerable");
		
	}

}
