package me.devsdevelop.powerup.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devsdevelop.powerup.PowerUp;
import me.devsdevelop.utils.Utils;

public abstract class PowerUpItem extends ItemStack{
	
	private PowerUp powerUp;
	private String displayName;
	
	public PowerUpItem(PowerUp powerUp, Material material, int amount, String displayName) {
		super(material, amount);
		this.powerUp = powerUp;
		this.displayName = displayName;
		setAsEnchanted();
		setNameData();
	}
	
	abstract public void activatePowerUp(Player player);
	
	public PowerUp getPowerUp() {
		return powerUp;
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
	}
}
