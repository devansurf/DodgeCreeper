package me.devsdevelop.powerup;


import java.util.ArrayList;

import me.devsdevelop.DodgeCreeper;


public class PowerUpBlockManager {
	
	private DodgeCreeper plugin;
	private ArrayList<PowerUpBlockGroup> powerUps = new ArrayList<PowerUpBlockGroup>();
	
	public PowerUpBlockManager(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	public void spawnPowerUp() {
		//TODO random coordinate generator for beacon spawn
	}
	
}
