package me.devsdevelop.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.devsdevelop.config.Config;
import net.md_5.bungee.api.ChatColor;

public class Utils {

	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static ItemStack createKnockbackStick(Config config) {
		
		List<String> lore = new ArrayList<String>();
		
		ItemStack stick = new ItemStack(Material.STICK);
		ItemMeta stickMeta = stick.getItemMeta();
		stickMeta.addEnchant(Enchantment.KNOCKBACK, config.getKnockbackStickValue(), true);
		stickMeta.setDisplayName(config.getKnockbackStickName());
		lore.add(config.getKnockbackStickDescription());
		stickMeta.setLore(lore);
		stick.setItemMeta(stickMeta);
		return stick;
	}
	public static ItemStack createKnockbackStickTwo(Config config) { // TO BE CHANGED LATER
		
		List<String> lore = new ArrayList<String>();
		
		ItemStack stick = new ItemStack(Material.STICK);
		ItemMeta stickMeta = stick.getItemMeta();
		stickMeta.addEnchant(Enchantment.KNOCKBACK, config.getKnockbackStickValue()/2, true);
		stickMeta.setDisplayName(config.getKnockbackStickName());
		lore.add(config.getKnockbackStickDescription());
		stickMeta.setLore(lore);
		stick.setItemMeta(stickMeta);
		return stick;
	}
	public static ItemStack createCustomArmor(Material leatherPiece, String colorName, int enchantLevel) {
		ItemStack item = new ItemStack(leatherPiece);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, enchantLevel, true);
		if (colorName.equalsIgnoreCase("blue"))
			meta.setColor(Color.BLUE);
		else if (colorName.equalsIgnoreCase("red"))
			meta.setColor(Color.RED);
		item.setItemMeta(meta);
		return item;
		
	}
	public static ItemStack getEgg(int amount, String type) {
		
		if (type.equalsIgnoreCase("basic")) {
			ItemStack egg = new ItemStack(Material.CREEPER_SPAWN_EGG, amount);
			ItemMeta eggMeta  = egg.getItemMeta();
			eggMeta.setDisplayName(Utils.chat("&eBasic Creeper"));
			egg.setItemMeta(eggMeta);
			return egg;
		}
		else if (type.equalsIgnoreCase("charged")) {
			ItemStack egg = new ItemStack(Material.CREEPER_SPAWN_EGG, amount);
			ItemMeta eggMeta  = egg.getItemMeta();
			eggMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			eggMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
			eggMeta.setDisplayName(Utils.chat("&eCharged Creeper"));
			egg.setItemMeta(eggMeta);
			return egg;
		}
		return null;
		
		
	}

	public static String getTeamColorCode(String teamColor) {
		String cc = "";
		if (teamColor.equalsIgnoreCase("blue")) {
			cc = "&b";
		}
		else if (teamColor.equalsIgnoreCase("red")) {
			cc = "&c";
		}
		else {
			cc = "&e";
		}
		return cc;
	}

}
