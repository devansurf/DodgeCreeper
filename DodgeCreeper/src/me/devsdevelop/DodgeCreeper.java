package me.devsdevelop;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.devsdevelop.commands.GameCommand;
import me.devsdevelop.config.Config;
import me.devsdevelop.gameplayer.GameManager;
import me.devsdevelop.listeners.CreeperTargetListener;
import me.devsdevelop.listeners.OnHitCreeperListener;
import me.devsdevelop.listeners.PressurePlateListener;
import me.devsdevelop.listeners.SpawnEggListener;
import me.devsdevelop.schematic.SchematicManager;

public class DodgeCreeper extends JavaPlugin{
	
	private GameManager gameManager;
	private SchematicManager schematicManager;
	private Config config;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		// ORDER MATTERS
		InstantiateClasses();
		RegisterEvents();	
		EnableCommands();
		
	}
	private void RegisterEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PressurePlateListener(this), this);
		pm.registerEvents(new SpawnEggListener(this), this);
        pm.registerEvents(new CreeperTargetListener(this), this);
        pm.registerEvents(new OnHitCreeperListener(this), this);
	}
	
	private void EnableCommands() {
		new GameCommand(this);
	}
	private void InstantiateClasses() {
		
		gameManager = new GameManager(this);
		schematicManager = new SchematicManager(this);
		config = new Config(this);
		
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
	public SchematicManager getSchematicManager() {
		return schematicManager;
	}
	public Config getConfigClass() {
		return config;
	}
}
