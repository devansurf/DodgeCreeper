package me.devsdevelop.powerup.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devsdevelop.game.GamePlayer;
import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public abstract class PowerUpItem extends ItemStack{
	
	private PowerUp powerUp;
	private String displayName;
	private boolean isActive = false;
	
	public PowerUpItem(PowerUp powerUp, Material material, int amount, String displayName) {
		super(material, amount);
		this.powerUp = powerUp;
		this.displayName = displayName;
		setCustomModelId(powerUp.getCustomModelId());
		setAsEnchanted();
		setNameData();
	
	}
	
	abstract public void activatePowerUp(GamePlayer gamePlayer);
	abstract public void removePowerUp(GamePlayer gamePlayer);
	
	public PowerUp getPowerUp() {
		return powerUp;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean value) {
		isActive = value;
	}
	
	private void setCustomModelId(int customModelData) {
		ItemMeta meta  = getItemMeta();
		meta.setCustomModelData(customModelData);
		setItemMeta(meta);
	}
	
	private void setAsEnchanted(){
		ItemMeta meta  = getItemMeta();
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
		setItemMeta(meta);
	}
	private void setNameData() {
		List<String> lore = new ArrayList<String>();
		ItemMeta meta  = getItemMeta();
		lore.add(Utils.chat("&dRight click to use Power Up!"));
		meta.setLore(lore);
		meta.setDisplayName(displayName);
		setItemMeta(meta);
	}
}
