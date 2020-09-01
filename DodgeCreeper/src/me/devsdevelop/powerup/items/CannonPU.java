package me.devsdevelop.powerup.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public class CannonPU extends PowerUpItem{

	public CannonPU(PowerUp powerUp, int amount) {
		super(powerUp, Material.EMERALD, amount, Utils.chat("&2Creeper Cannon"));
	}

	@Override
	public void activatePowerUp(GamePlayer gamePlayer) {
		Player player = gamePlayer.getPlayer();
		gamePlayer.setCreeperCannon(true); // this variable allows the PlayerInteractListener to pass a call where rightClicking the stick spawns creepers.
		player.getInventory().addItem(Utils.getEgg(5, "basic")); // give the player 5 basic creeper eggs.
		player.sendMessage(Utils.chat("&7=== &aActivated &2Creeper Cannon&7 ==="));
		
	}

	@Override
	public void removePowerUp(GamePlayer gamePlayer) {
		Player player = gamePlayer.getPlayer();
		gamePlayer.setCreeperCannon(false);
		player.sendMessage(Utils.chat("&7=== &4Your &2Creeper Cannon &4is no longer functional.&7 ==="));
	}
}
