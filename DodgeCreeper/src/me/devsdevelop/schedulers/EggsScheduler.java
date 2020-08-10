package me.devsdevelop.schedulers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.CustomCreeper;


public class EggsScheduler {

	private DodgeCreeper plugin;
	private List<Integer> scheduleIds = new ArrayList<Integer>();
	private boolean isRunning = false;
	
	public EggsScheduler(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	public void initializeSchedulers() {
	
		if (!isRunning) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();	
			long basicCreeperTimer = (long)plugin.getConfigClass().getBasicCreeperTimer();
			long chargedCreeperTimer = (long)plugin.getConfigClass().getChargedCreeperTimer();
			int chargedCreeperAmount = plugin.getConfigClass().getChargedCreeperAmount();
			int basicCreeperAmount = plugin.getConfigClass().getBasicCreeperAmount();
			BasicCreeperScheduler(scheduler, basicCreeperTimer, basicCreeperAmount);
			ChargedCreeperScheduler(scheduler, chargedCreeperTimer, chargedCreeperAmount);
			RemoveCreeperScheduler(scheduler);
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
	private void RemoveCreeperScheduler(BukkitScheduler scheduler) {
		int taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable(){
	        @Override
	        public void run(){ 
	        	for (CustomCreeper creeper :  plugin.getGameManager().getCreepers()) {
	        		if (creeper.dead) {
	        			plugin.getGameManager().getCreepers().remove(creeper);
	        		}
	        	}
              
	        }
	     }, 0, 200); // ticks ->// 20 ticks = 1 second// every 10 seconds remove dead creepers.
	      //warmup,interval
	     scheduleIds.add(taskId);
	}
	private void BasicCreeperScheduler(BukkitScheduler scheduler, long timer, final int amount) {
		  
	     int taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable(){
	        @Override
	        public void run(){ 
                plugin.getGameManager().givePlayerEggs(amount, "basic");
	        }
	     }, timer, timer); // ticks ->// 20 ticks = 1 second// 
	      //warmup,interval
	     scheduleIds.add(taskId);
	}
	private void ChargedCreeperScheduler(BukkitScheduler scheduler, long timer, final int amount) {
		
		   int taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable(){
		        @Override
		        public void run(){ 
		        	plugin.getGameManager().givePlayerEggs(amount, "charged");
		        }
		     }, timer, timer); // ticks ->// 20 ticks = 1 second// 
		     
		     scheduleIds.add(taskId);
	}
}
