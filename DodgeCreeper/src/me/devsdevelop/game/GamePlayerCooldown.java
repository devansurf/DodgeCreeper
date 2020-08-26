package me.devsdevelop.game;

import me.devsdevelop.powerup.items.PowerUpItem;

public class GamePlayerCooldown {

	private PowerUpItem powerUpItem;
	private long cooldownInTicks;
	
	public GamePlayerCooldown(PowerUpItem powerUpItem, long ticks) {
		this.powerUpItem = powerUpItem;
		cooldownInTicks = ticks;
	}
	
	public long getCooldown() {
		return cooldownInTicks;
	}
	
	public void setCooldown(long ticks) {
		cooldownInTicks = ticks;
	}
	
	public boolean hasCooldown() {
		if (cooldownInTicks <= 0) {
			return false;
		}
		return true;
	}
	
	public void reduceCooldownByAmount(long ticks) {
		cooldownInTicks -= ticks;
	}
	
	public PowerUpItem getPowerUpItem() {
		return powerUpItem;
	}
	
}
