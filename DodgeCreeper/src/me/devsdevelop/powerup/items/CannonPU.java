package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class CannonPU extends PowerUpItem{

	public CannonPU(PowerUp powerUp, int amount) {
		super(powerUp, Material.EMERALD, amount, Utils.chat("&2Creeper Cannon"));
	}

	@Override
	public void activatePowerUp(Player player) {
		player.sendMessage(Utils.chat("&7=== &aActivated &2Creeper Cannon&7 ==="));
		
	}

	@Override
	public void removePowerUp(Player player) {
		// TODO Auto-generated method stub
		
	}
}
