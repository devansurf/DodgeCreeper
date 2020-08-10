package me.devsdevelop.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;

public class DataManager {
	
	private DodgeCreeper plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	
	public DataManager(DodgeCreeper plugin) {
		this.plugin = plugin;
	}
	
	public void reloadConfig() {
		if (this.configFile == null) 
			this.configFile = new File(this.plugin.getDataFolder(),"data.yml");
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		
		InputStream defaultStream = this.plugin.getResource("data.yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	public FileConfiguration getConfig() {
		if (this.dataConfig == null) {
			reloadConfig();
		}
		return this.dataConfig;
	}
	
	public void saveConfig() {
		if (this.dataConfig == null || this.configFile == null) {
			return;
		}
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
		}
	}
	
	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(),"data.yml");
		}
		if (!this.configFile.exists()) {
			this.plugin.saveResource("data.yml", false);
		}
	}
	
	
	public void setArenaLocation(int x, int y, int z) {
		getConfig().set("world.arena.x", x); // set arena coords
		getConfig().set("world.arena.y", y);
		getConfig().set("world.arena.z", z);
		saveConfig();
	}
	public Location getArenaLocation(Player player) {
		Location loc = new Location(player.getWorld(), 
				getConfig().getInt("world.arena.x"),
				getConfig().getInt("world.arena.y"),
				getConfig().getInt("world.arena.z"));
		return loc;
	}
	public void setWorldUID(String UID) {getConfig().set("world.uuid", UID);saveConfig();}
	public String getWorldUID() {return getConfig().getString("world.uuid");}
	
	public void setArena(Boolean value) {getConfig().set("world.arena.value", value);saveConfig();}
	public boolean getArena() {return getConfig().getBoolean("world.arena.value");}
	
	
		
	
}

