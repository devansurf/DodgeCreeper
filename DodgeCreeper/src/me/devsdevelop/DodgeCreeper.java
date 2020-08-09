package me.devsdevelop;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.devsdevelop.commands.GameCommand;
import me.devsdevelop.config.Config;
import me.devsdevelop.gameplayer.GameManager;
import me.devsdevelop.listeners.CreeperTargetListener;
import me.devsdevelop.listeners.FoodListener;
import me.devsdevelop.listeners.OnHitCreeperListener;
import me.devsdevelop.listeners.OnPlayerDamageListener;
import me.devsdevelop.listeners.PlayerMoveListener;
import me.devsdevelop.listeners.PressurePlateListener;
import me.devsdevelop.listeners.SpawnEggListener;
import me.devsdevelop.schedulers.EggsScheduler;
import me.devsdevelop.schematic.SchematicManager;

public class DodgeCreeper extends JavaPlugin{
	
	private GameManager gameManager;
	private SchematicManager schematicManager;
	private EggsScheduler eggsScheduler;
	private Config config;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		// ORDER MATTERS
		InstantiateClasses();
		RegisterEvents();	
		EnableCommands();
		EnableSchedulers();
		
	}

	private void RegisterEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PressurePlateListener(this), this);
		pm.registerEvents(new SpawnEggListener(this), this);
		pm.registerEvents(new CreeperTargetListener(this), this);
		pm.registerEvents(new OnHitCreeperListener(this), this);
		pm.registerEvents(new PlayerMoveListener(this), this);
		pm.registerEvents(new FoodListener(), this);
		pm.registerEvents(new OnPlayerDamageListener(), this);
	}
	
	private void EnableCommands() {
		new GameCommand(this);
	}
	private void EnableSchedulers() {
		eggsScheduler = new EggsScheduler(this);
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
	public EggsScheduler getEggsScheduler() {
		return eggsScheduler;
	}
}
