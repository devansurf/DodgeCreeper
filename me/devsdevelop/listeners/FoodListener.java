package me.devsdevelop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListener implements Listener{

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) { // cancel food depletion
		event.setCancelled(true);
	}
}
