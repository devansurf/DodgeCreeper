package me.devsdevelop.gameplayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.CustomCreeper;
import me.devsdevelop.utils.Utils;
import me.devsdevelop.utils.sound.SoundManager;
import me.devsdevelop.utils.sound.SoundType;

public class GameManager {

	private DodgeCreeper plugin;
	private List<PlayerTeam> playerTeams = new ArrayList<PlayerTeam>();
	private List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();
	private List<CustomCreeper> creepers = new ArrayList<CustomCreeper>();
	private boolean arenaBuilt = false;
	public boolean gameStarted = false;
	
	public GameManager(DodgeCreeper plugin) {
		this.plugin = plugin;
		createTeams();
	}
	public void StartGame(Player player) { // the parameter player serves more for providing messages than anything else.
		if (gamePlayers.size() < 1) {
			player.sendMessage(Utils.chat("&cThere are an insufficient amount of players to start the game, use /dc add <player> to add players"));
			return;
		}
		
		if (!arenaBuilt) {
			createArena(player);
		}
		gameStarted = true;
		teleportGamePlayers();
		giveGamePlayerItems();
		plugin.getEggsScheduler().initializeSchedulers();
		// give player armor, items, spawn points, team, and teleport them.
	}
	public void clearGame() {
	
		gameStarted = false;
		plugin.getEggsScheduler().stopSchedulers();
		gamePlayers.clear();
		creepers.clear();
		for (PlayerTeam playerTeam : playerTeams) {
			playerTeam.clearMembers();
		}
	}
	
	public String addPlayer(String playerName, String teamColor) {

		if (PlayerExists(playerName)) 
			return "&eThe specified player already exists";	
		
		Player player = Bukkit.getPlayerExact(playerName);
		if (!arenaBuilt) {
			createArena(player);
		}
		if (player == null) 
			return  "&cThe specified player does not exist";
		
		PlayerProfile profile = new PlayerProfile(playerName, player.getUniqueId());
		PlayerTeam team = assignPlayerTeam(player, teamColor);
		if (team == null) {
			return "&cThe specified color does not exist in this context";
		}
		player.setHealth(20d);
		player.setSaturation(0f);
		gamePlayers.add(new GamePlayer(player,profile,team,plugin));
		Bukkit.broadcastMessage(Utils.chat("&6" + playerName + " &ahas joined the " + Utils.getTeamColorCode(teamColor) + teamColor + "&a team !"));
		return "&eSuccessfully added the player " + playerName + " to team " + teamColor;
	}
	
	public boolean removePlayer(String playerName) {
		for (GamePlayer gp : gamePlayers) {
			if (gp.getPlayer().getName().equalsIgnoreCase(playerName)) {
				gamePlayers.remove(gamePlayers.indexOf(gp)); // removes player
				return true;
			}
		}
		return false;
	}
	
	public boolean PlayerExists(String playerName) {
		for (GamePlayer gp : gamePlayers) {
			if (gp.getPlayer().getName().equalsIgnoreCase(playerName)) {
				return true;
			}
		}
		return false;
	}
	
	public void createArena(Player player) {
		if (arenaBuilt) {
			player.sendMessage(Utils.chat("&cArena has already been built in this world"));
			return;
		}	
		arenaBuilt = plugin.getSchematicManager().loadSchematic(player);
		if (arenaBuilt) {
			player.sendMessage(Utils.chat("&eArena has been successfully created"));
		}else {
			player.sendMessage(Utils.chat("&cThe schematic was loaded unsuccesfully."));
		}
		// store coordinates of arena in config
	}
	public void givePlayerEggs(int amount, String type) {
		ItemStack egg = Utils.getEgg(amount, type);
		for (GamePlayer gp : gamePlayers) {
			gp.getPlayer().getInventory().addItem(egg);
			gp.getPlayer().updateInventory();
			SoundManager.sendSound(gp.getPlayer(), gp.getPlayer().getLocation(), SoundType.POP);
		}
	}
	public GamePlayer getGamePlayerFromPlayer(Player player) {
		for (GamePlayer gp : gamePlayers) {
			if (gp.getPlayer().equals(player)) {
				return gp;
			}
		}
		return null;
	}
	public ArrayList<PlayerTeam> getPlayerTeams() {
		return (ArrayList<PlayerTeam>) playerTeams;
	}
	
	public int getTotalPlayers() {
		return gamePlayers.size();
	}
	
	public void addCreepers(CustomCreeper creeper) {
		creepers.add(creeper);
	}
	public ArrayList<CustomCreeper> getCreepers(){
		return (ArrayList<CustomCreeper>) creepers;
	}
	
	private void createTeams() {
		playerTeams.add(new PlayerTeam(TeamColor.BLUE));
		playerTeams.add(new PlayerTeam(TeamColor.RED));		
	}
	private PlayerTeam assignPlayerTeam(Player player, String teamColor) { // assigns the player to the existing teams
		
		for (PlayerTeam pt : playerTeams) {
			if (teamColor.equalsIgnoreCase("blue") && pt.getTeamColor().equals(TeamColor.BLUE)) { // when adding member to blue team
				pt.addTeamMember(player);
				return pt;
			}
			else if (teamColor.equalsIgnoreCase("red") && pt.getTeamColor().equals(TeamColor.RED)) { // when adding member to red team
				pt.addTeamMember(player); 
				return pt;
			}
		}
		return null;
	}
	
    private void teleportGamePlayers() {
    	for (GamePlayer gp : gamePlayers) {
    		gp.teleportGamePlayer();
    	}
    }
    
    private void giveGamePlayerItems() {
    	for (PlayerTeam playerTeam : playerTeams) {   	
    		ItemStack[] armor = {Utils.createCustomArmor(Material.LEATHER_BOOTS, playerTeam.getTeamColor().toString(), plugin.getConfigClass().getArmorLevel()), 
        					 	 Utils.createCustomArmor(Material.LEATHER_LEGGINGS, playerTeam.getTeamColor().toString(), plugin.getConfigClass().getArmorLevel()),
      /*Armor for each team*/	 Utils.createCustomArmor(Material.LEATHER_CHESTPLATE, playerTeam.getTeamColor().toString(), plugin.getConfigClass().getArmorLevel()), 
        					 	 Utils.createCustomArmor(Material.LEATHER_HELMET, playerTeam.getTeamColor().toString(), plugin.getConfigClass().getArmorLevel())};
  
    		for (Player player : playerTeam.getTeamMembers()) {
    			player.getInventory().clear(); // clear the inventory before adding items.
    			player.getInventory().addItem(Utils.createKnockbackStick(plugin.getConfigClass())); // knockback Sticks
    			player.getInventory().setArmorContents(armor); // equips armor to the player
    			player.updateInventory();  //update inventory for items to take effect
    		}
    	}
    

    }
	
	
}
