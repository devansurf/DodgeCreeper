package me.devsdevelop.gameplayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.creepers.CustomCreeper;
import me.devsdevelop.utils.Utils;
import net.minecraft.server.v1_15_R1.EntityCreeper;

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
//		if (gamePlayers.size() < 2) {
//			player.sendMessage(Utils.chat("&cThere are an insufficient amount of players to start the game, use /dc add <player> to add players"));
//			return;
//		}
		
		if (!arenaBuilt) {
			createArena(player);
		}
		gameStarted = true;
		teleportGamePlayers();
		giveGamePlayerItems();
		
		// give player armor, items, spawn points, team, and teleport them.
	}
	public void clearGame() {
		gameStarted = false;
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
    	for (GamePlayer gamePlayer : gamePlayers) {
    		Player player = gamePlayer.getPlayer();
    		player.getInventory().addItem(Utils.createKnockbackStick(plugin.getConfigClass())); // knockback Sticks
    		player.updateInventory();
    	}
    }
	
	
}
