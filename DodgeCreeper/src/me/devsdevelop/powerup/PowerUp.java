package me.devsdevelop.powerup;

import java.util.Random;

import org.bukkit.Material;

public enum PowerUp {
	CANNON(Material.GREEN_STAINED_GLASS),
	HEALTH (Material.PINK_STAINED_GLASS),
	INVISIBLE(Material.LIGHT_BLUE_STAINED_GLASS),
	INVULNERABLE(Material.WHITE_STAINED_GLASS),
	SPEED(Material.YELLOW_STAINED_GLASS);
	// more creepers powerup?
	
	private final Material glass;
	
	PowerUp(Material glass) {
		this.glass = glass;
		
	}
	
	public Material getGlass() {
		return glass;
	}
	
	public PowerUp getRandomPowerUp() {
		return values()[new Random().nextInt(values().length)];
	}
	

}
