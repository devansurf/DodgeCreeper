package me.devsdevelop.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperSpawnListener implements Listener{
	
	@EventHandler
	public void onCreeperSpawn(CreatureSpawnEvent event){
		if (event.getEntity().getCustomName().contains("Creeper")) {
			LivingEntity creeperPotion = (LivingEntity)event.getEntity();
			creeperPotion.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,10000,5)); // slowness 6 for 10000 ticks
		}
	}
}
