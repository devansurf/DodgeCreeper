package me.devsdevelop.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
		

		gameStarted = true;
		creepers.clear();
		checkBuildArena(player);
		setGameRules(player);
		removeAllEntitiesFromWorld(player); // dangerous if the world is used for other things
		setupGamePlayers();
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
		Player player = Bukkit.getPlayerExact(playerName);  // player		
		if (player == null) 
			return  "&cThe specified player does not exist";
		
		checkBuildArena(player);
		PlayerProfile profile = new PlayerProfile(playerName, player.getUniqueId()); //profile, not used yet.
		PlayerTeam team = assignPlayerTeam(player, teamColor);
		if (team == null) {
			return "&cThe specified color does not exist in this context";
		}
		
		gamePlayers.add(new GamePlayer(player,profile,team,plugin));   // gamePlayer
		Bukkit.broadcastMessage(Utils.chat("&6" + playerName + " &ahas joined the " + Utils.getTeamColorCode(teamColor) + teamColor + "&a team !"));
		return "&eSuccessfully added the player " + playerName + " to team " + teamColor;
	}
	
	public String addAllPlayers() { // adds all remaining online players to the game in a balanced fashion.

		int playersAdded = 0;
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			if (!gamePlayersContainsPlayer(player)) { // if a player is not added to the game
				PlayerProfile profile = new PlayerProfile(player.getName(), player.getUniqueId()); //profile, not used yet.
				PlayerTeam team = assignPlayerTeam(player, getSmallerTeamColor()); // adds player to the team with less members.
				gamePlayers.add(new GamePlayer(player,profile,team,plugin)); 
				playersAdded++;
				Bukkit.broadcastMessage(Utils.chat("&6" + player.getName() + " &ahas joined the " 
						+ Utils.getTeamColorCode(team.getTeamColor().toString()) + team.getTeamColor().toString() + "&a team !"));
			}
				
		}
		return Utils.chat("&aAdded a total of &6" + playersAdded + " &aplayer(&es&a) to the game.");
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
		if (plugin.getDataManager().getArena() && !worldChanged(player)) { //if attempting to create arena while an arena already exists
			player.sendMessage(Utils.chat("&cArena has already been built in this world"));
			return;
		}	
		
		if(plugin.getSchematicManager().loadSchematic(player)) { // succesfully loaded the arena
			setWorldData(player);
			player.sendMessage(Utils.chat("&eArena has been successfully created"));
		}
		else {
			player.sendMessage(Utils.chat("&cThe schematic was loaded unsuccesfully."));
		}
		
	}
	public void givePlayerEggs(int amount, String type) {
		ItemStack egg = Utils.getEgg(amount, type);
		for (GamePlayer gp : gamePlayers) {
			if (gp.getPlayer().getGameMode().equals(GameMode.SURVIVAL)  // if in survival and max eggs not reached
					&& !gp.getPlayer().getInventory().containsAtLeast(egg, plugin.getConfigClass().getMaxEggs())) {
				gp.getPlayer().getInventory().addItem(egg);
				gp.getPlayer().updateInventory();
				SoundManager.sendSound(gp.getPlayer(), gp.getPlayer().getLocation(), SoundType.POP);
			}
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
	private boolean gamePlayersContainsPlayer(Player player) {
		for (GamePlayer gamePlayer : gamePlayers) {
			if (gamePlayer.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}
	private String getSmallerTeamColor() { // returns the teamColor with less members.
		String smallerTeam = playerTeams.get(0).getTeamColor().toString();
		int prevTeamNum = playerTeams.get(0).getTotalMembers();
		
		for (PlayerTeam playerTeam : playerTeams) {
			if (playerTeam.getTotalMembers() < prevTeamNum) { //if a team has less members than another team
				prevTeamNum = playerTeam.getTotalMembers();
				smallerTeam = playerTeam.getTeamColor().toString();
			}
		}
		return smallerTeam;
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
	
    private void setupGamePlayers() {
    	for (GamePlayer gp : gamePlayers) {
    		Player player = gp.getPlayer();
    		player.setHealth(20d);
    		player.setFoodLevel(20);
    		player.setSaturation(0f);
    		player.setGameMode(GameMode.SURVIVAL);
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
	private void removeAllEntitiesFromWorld(Player player) {

        for(Entity en : player.getWorld().getEntities()){
            if(!(en instanceof Player)) {
            	en.remove();
            }
        }
	}
	private void setGameRules(Player player) {
		player.getWorld().setGameRule(GameRule.MOB_GRIEFING, false);
	}
	
	private void setWorldData(Player player) { // save arena location, and unique world id.
		String worldId = player.getWorld().getUID().toString();
		Location arenaLoc = plugin.getSchematicManager().getLocation();
		plugin.getDataManager().setWorldUID(worldId); // set world id
		plugin.getDataManager().setArena(true); // set arena to true
		plugin.getDataManager().setArenaLocation(arenaLoc.getBlockX(), arenaLoc.getBlockY(),(arenaLoc.getBlockZ()));

		
	}
	private boolean worldChanged(Player player) {
		String worldId = player.getWorld().getUID().toString();
		if (plugin.getDataManager().getConfig().contains("world.uuid")) { // if the path exists for worldId
			if (plugin.getDataManager().getWorldUID().equals(worldId)) { //check if this world is the same as the saved one. 
				return false;
			}
			plugin.getDataManager().setArena(false);
		}
		return true;
	}
	private boolean checkBuildArena(Player player) {
		if (!plugin.getDataManager().getArena() || worldChanged(player)) { // if an arena does not exist or if the worlds changed, then create a new arena
			Bukkit.broadcastMessage("getArena is: " + plugin.getDataManager().getArena());
			Bukkit.broadcastMessage("worldChanged is: " + worldChanged(player));
			createArena(player);
			return true;
		}
		return false;
	}
}
