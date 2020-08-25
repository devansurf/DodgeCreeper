package me.devsdevelop.powerup;
import org.bukkit.Material;

import me.devsdevelop.powerup.items.CannonPU;
import me.devsdevelop.powerup.items.HealthPU;
import me.devsdevelop.powerup.items.InvisiblePU;
import me.devsdevelop.powerup.items.InvulnerablePU;
import me.devsdevelop.powerup.items.PowerUpItem;
import me.devsdevelop.powerup.items.SpeedPU;


public enum PowerUp {
	//CANNON(Material.GREEN_STAINED_GLASS, 1),
	HEALTH (Material.PINK_STAINED_GLASS, 2),
	INVISIBLE(Material.LIGHT_BLUE_STAINED_GLASS, 3),
	INVULNERABLE(Material.WHITE_STAINED_GLASS, 4),
	SPEED(Material.YELLOW_STAINED_GLASS, 5);
	//TODO more creepers powerUp?
	
	private final Material glass;
	private final int customModelId;
	
	PowerUp(Material glass, int customModelId) {
		this.customModelId = customModelId;
		this.glass = glass;
	}
	
	public Material getGlass() {
		return glass;
	}
	
	public int getCustomModelId() {
		return customModelId;
	}
	

}
