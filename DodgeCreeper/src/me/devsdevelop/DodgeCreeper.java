package me.devsdevelop;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.devsdevelop.commands.GameCommand;
import me.devsdevelop.config.Config;
import me.devsdevelop.data.DataManager;
import me.devsdevelop.game.GameManager;
import me.devsdevelop.listeners.BlockBreakListener;
import me.devsdevelop.listeners.BlockPlaceListener;
import me.devsdevelop.listeners.CreeperTargetListener;
import me.devsdevelop.listeners.FoodListener;
import me.devsdevelop.listeners.OnHitEntityListener;
import me.devsdevelop.listeners.OnPlayerDamageListener;
import me.devsdevelop.listeners.PlayerMoveListener;
import me.devsdevelop.listeners.PlayerRegainHealthEvent;
import me.devsdevelop.listeners.PressurePlateListener;
import me.devsdevelop.listeners.PlayerInteractListener;
import me.devsdevelop.powerup.PowerUpItemManager;
import me.devsdevelop.powerup.PowerUpManager;
import me.devsdevelop.schedulers.EggsScheduler;
import me.devsdevelop.schedulers.PowerUpScheduler;
import me.devsdevelop.schematic.SchematicManager;

public class DodgeCreeper extends JavaPlugin{
	
	private GameManager gameManager;
	private SchematicManager schematicManager;
	private PowerUpManager powerUpManager;
	private PowerUpItemManager powerUpItemManager;
	private EggsScheduler eggsScheduler;
	private PowerUpScheduler powerUpScheduler;
	private Config config;
	private DataManager dataManager;
	
	@Override
	public void onEnable() {		
		// ORDER MATTERS
		LoadConfigs();
		InstantiateClasses();
		RegisterEvents();	
		EnableCommands();
		EnableSchedulers();		
	}
	
	@Override
	public void onDisable() {
		gameManager.clearGame();
	}
	
	private void LoadConfigs() {
		saveDefaultConfig();
		dataManager = new DataManager(this); // MUST GO BEFORE CONFIG!!
		dataManager.saveDefaultConfig();
		config = new Config(this);		
	}
	
	private void RegisterEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PressurePlateListener(this), this);
		pm.registerEvents(new CreeperTargetListener(this), this);
		pm.registerEvents(new OnHitEntityListener(this), this);
		pm.registerEvents(new PlayerMoveListener(this), this);
		pm.registerEvents(new FoodListener(), this);
		pm.registerEvents(new OnPlayerDamageListener(), this);
		pm.registerEvents(new PlayerRegainHealthEvent(), this);
		pm.registerEvents(new BlockBreakListener(this), this);
		pm.registerEvents(new BlockPlaceListener(this), this);
		pm.registerEvents(new PlayerInteractListener(this), this);
	}
	
	private void EnableCommands() {
		new GameCommand(this);
	}
	private void EnableSchedulers() {
		eggsScheduler = new EggsScheduler(this);
		powerUpScheduler = new PowerUpScheduler(this);
	}
	private void InstantiateClasses() {
		
		gameManager = new GameManager(this);
		schematicManager = new SchematicManager(this);
		powerUpManager = new PowerUpManager(this);
		powerUpItemManager = new PowerUpItemManager(this);
		
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
	public SchematicManager getSchematicManager() {
		return schematicManager;
	}
	public PowerUpManager getPowerUpManager() {
		return powerUpManager;
	}
	public PowerUpItemManager getPowerUpItemManager() {
		return powerUpItemManager;
	}
	public Config getConfigClass() {
		return config;
	}
	public EggsScheduler getEggsScheduler() {
		return eggsScheduler;
	}
	public PowerUpScheduler getPowerUpScheduler() {
		return powerUpScheduler;
	}
	public DataManager getDataManager() {
		return dataManager;
	}
	
}
