package me.devsdevelop.game;

import me.devsdevelop.powerup.items.PowerUpItem;

public class GamePlayerCooldown {

	private PowerUpItem powerUpItem;
	private double cooldownInTicks;
	
	public GamePlayerCooldown(PowerUpItem powerUpItem, double ticks) {
		this.powerUpItem = powerUpItem;
		cooldownInTicks = ticks;
	}
	
	public double getCooldown() {
		return cooldownInTicks;
	}
	
	public void setCooldown(double ticks) {
		cooldownInTicks = ticks;
	}
	
	public boolean hasCooldown() {
		if (cooldownInTicks <= 0) {
			return false;
		}
		return true;
	}
	
	public void reduceCooldownByAmount(double ticks) {
		cooldownInTicks -= ticks;
	}
	
	public PowerUpItem getPowerUpItem() {
		return powerUpItem;
	}
	
}
