package me.devsdevelop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.utils.Utils;


public class PressurePlateListener implements Listener{
	
	private DodgeCreeper plugin;

	public PressurePlateListener(DodgeCreeper plugin) {
		this.plugin = plugin;

	}
	@EventHandler
	public void onPressurePlate (PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.PHYSICAL)){
			if(event.getClickedBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
				player.getInventory().addItem(Utils.getEgg(5,"basic"));
				player.updateInventory();
				return;
			}
			if (event.getClickedBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
				player.getInventory().addItem(Utils.getEgg(1,"charged"));
				player.updateInventory();
				return;
			}
			
		}
	}

}
