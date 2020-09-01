package me.devsdevelop.powerup;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.items.CannonPU;
import me.devsdevelop.powerup.items.HealthPU;
import me.devsdevelop.powerup.items.InvisiblePU;
import me.devsdevelop.powerup.items.InvulnerablePU;
import me.devsdevelop.powerup.items.PowerUpItem;
import me.devsdevelop.powerup.items.SpeedPU;

public class PowerUpItemManager {
	
	private DodgeCreeper plugin;
	private ArrayList<PowerUpItem> powerUpList = new ArrayList<PowerUpItem>();
	
	public PowerUpItemManager(DodgeCreeper plugin) {	
		this.plugin = plugin;
		powerUpList.add (new CannonPU(PowerUp.CANNON,1));
		powerUpList.add (new HealthPU(PowerUp.HEALTH,1));
		powerUpList.add (new InvisiblePU(PowerUp.INVISIBLE,1));
		powerUpList.add (new InvulnerablePU(PowerUp.INVULNERABLE,1));
		powerUpList.add (new SpeedPU(PowerUp.SPEED,1));
	}
	
	public void activateItemPowerUp(ItemStack item, GamePlayer gamePlayer) { // called by PlayerInteractEvent
		
		ItemMeta itemMeta = item.getItemMeta();
		for (PowerUpItem powerUpItem : powerUpList) { // search through existing powerUps
			if (itemMeta.getCustomModelData() == powerUpItem.getPowerUp().getCustomModelId()) { // verify item in hand
				powerUpItem.activatePowerUp(gamePlayer);	// calls the function specific to the powerUp
				powerUpItem.setActive(true); // set the powerUpItems state to active
				gamePlayer.addGamePlayerCooldown(powerUpItem, (long)plugin.getConfigClass().getPowerUpCooldown()); // add the powerUp Cooldown. calls GamePlayer
				return;
			}
		}
	}
}
