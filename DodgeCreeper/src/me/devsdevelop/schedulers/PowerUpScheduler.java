package me.devsdevelop.schedulers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.powerup.PowerUpManager;

public class PowerUpScheduler {
	private DodgeCreeper plugin;
	private PowerUpManager powerUpManager;
	private List<Integer> scheduleIds = new ArrayList<Integer>();
	private boolean isRunning = false;
	
	public PowerUpScheduler(DodgeCreeper plugin) {
		this.plugin = plugin;
		this.powerUpManager = plugin.getPowerUpManager();
	}
	public void initializeSchedulers() {
	
		if (!isRunning) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();	
			long powerUpSpawnTimer = (long)plugin.getConfigClass().getPowerUpSpawnTimer();
			long powerUpCheckTimer = (long)plugin.getConfigClass().getPowerUpCheckTimer();
			powerUpSpawnScheduler(scheduler, powerUpSpawnTimer);
			powerUpCheckScheduler(scheduler, powerUpSpawnTimer, powerUpCheckTimer);
			isRunning = true; // important that it's located after running scheduler.
		}
		
		
	}
	public void stopSchedulers() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		for (Integer taskid : scheduleIds) {
			scheduler.cancelTask(taskid);
		}
		isRunning = false;
		
	}
	public void powerUpSpawnScheduler(BukkitScheduler scheduler, long timer) {
		
		int taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable(){
	        @Override
	        public void run(){ 
	        	powerUpManager.spawnPowerUp();
	        }
	     }, timer, timer); // ticks ->// 20 ticks = 1 second// 
	      //warmup,interval
	     scheduleIds.add(taskId);
	}
	public void powerUpCheckScheduler(BukkitScheduler scheduler, long initTimer, final long timer) {
		int taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable(){
	        @Override
	        public void run(){ 
	        	powerUpManager.checkCollectedPowerUps((double)timer);
	        }
	     }, initTimer, timer); 
	    
	     scheduleIds.add(taskId);
	}
}
