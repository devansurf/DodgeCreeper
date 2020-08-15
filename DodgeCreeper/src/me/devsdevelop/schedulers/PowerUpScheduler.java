package me.devsdevelop.schedulers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import me.devsdevelop.DodgeCreeper;

public class PowerUpScheduler {
	private DodgeCreeper plugin;
	private List<Integer> scheduleIds = new ArrayList<Integer>();
	private boolean isRunning = false;
	
	public PowerUpScheduler(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	public void initializeSchedulers() {
	
		if (!isRunning) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();	
			long powerUpSpawnTimer = (long)plugin.getConfigClass().getPowerUpSpawnTimer();
			powerUpSpawnScheduler(scheduler, powerUpSpawnTimer);
			isRunning = true; // important that it's located after everything
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
	        	
	        }
	     }, timer, timer); // ticks ->// 20 ticks = 1 second// 
	      //warmup,interval
	     scheduleIds.add(taskId);
	}
}
