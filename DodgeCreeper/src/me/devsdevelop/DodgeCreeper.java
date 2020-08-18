package me.devsdevelop;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.devsdevelop.commands.GameCommand;
import me.devsdevelop.config.Config;
import me.devsdevelop.data.DataManager;
import me.devsdevelop.game.GameManager;
import me.devsdevelop.listeners.BlockBreakListener;
import me.devsdevelop.listeners.CreeperTargetListener;
import me.devsdevelop.listeners.FoodListener;
import me.devsdevelop.listeners.OnHitEntityListener;
import me.devsdevelop.listeners.OnPlayerDamageListener;
import me.devsdevelop.listeners.PlayerMoveListener;
import me.devsdevelop.listeners.PlayerRegainHealthEvent;
import me.devsdevelop.listeners.PressurePlateListener;
import me.devsdevelop.listeners.SpawnEggListener;
import me.devsdevelop.powerup.PowerUpBlockManager;
import me.devsdevelop.schedulers.EggsScheduler;
import me.devsdevelop.schematic.SchematicManager;

public class DodgeCreeper extends JavaPlugin{
	
	private GameManager gameManager;
	private SchematicManager schematicManager;
	private PowerUpBlockManager powerUpBlockManager;
	private EggsScheduler eggsScheduler;
	private Config config;
	private DataManager data;
	
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
		
	}
	
	private void LoadConfigs() {
		saveDefaultConfig();
		data = new DataManager(this);
		data.saveDefaultConfig();
		
	}
	
	private void RegisterEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PressurePlateListener(this), this);
		pm.registerEvents(new SpawnEggListener(this), this);
		pm.registerEvents(new CreeperTargetListener(this), this);
		pm.registerEvents(new OnHitEntityListener(this), this);
		pm.registerEvents(new PlayerMoveListener(this), this);
		pm.registerEvents(new FoodListener(), this);
		pm.registerEvents(new OnPlayerDamageListener(), this);
		pm.registerEvents(new PlayerRegainHealthEvent(), this);
		pm.registerEvents(new BlockBreakListener(this), this);
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
		powerUpBlockManager = new PowerUpBlockManager(this);
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
	public DataManager getDataManager() {
		return data;
	}
	public PowerUpBlockManager getPowerUpManager() {
		return powerUpBlockManager;
	}
}
