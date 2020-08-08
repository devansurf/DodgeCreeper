package me.devsdevelop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.utils.Utils;

public class GameCommand implements CommandExecutor{
	
	private DodgeCreeper plugin;
	public GameCommand(DodgeCreeper plugin) {
		this.plugin = plugin;
		plugin.getCommand("dc").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender,  Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;// if not a player
		}
		Player player = (Player) sender;
		
		switch (args.length) {
			case 0:
				player.sendMessage(Utils.chat("&o&c--------&eList of commands&c--------\n&c/dc add <player> <team>\n/dc remove <player> <team>\n"
						+ "/dc create\n/dc start\n/dc stop"));
				return true;
			case 1:
				if (args[0].equalsIgnoreCase("start")) {
					player.sendMessage(Utils.chat("&eStarting the game..."));
					plugin.getGameManager().StartGame(player);
				}
				else if (args[0].equalsIgnoreCase("create")) {
					player.sendMessage(Utils.chat("&eCreating the arena..."));
					plugin.getGameManager().createArena(player);;
				}
				else if (args[0].equalsIgnoreCase("stop")) {
					player.sendMessage(Utils.chat("&eStoping the game..."));
					plugin.getGameManager().clearGame();
				}
				return true;
			case 2:
				if (args[0].equalsIgnoreCase("add")) {
					player.sendMessage(Utils.chat("&cPlease specify the team color."));
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					player.sendMessage(Utils.chat("&eRemoving the player " + args[1] + " &efrom the game."));
					if (!plugin.getGameManager().removePlayer(args[1])) 
						player.sendMessage(Utils.chat("&cThe specified player was not in the game to begin with."));
					else {
						player.sendMessage(Utils.chat("&eThe player " + args[1] + " &ewas successfully removed from the game!"));
					}			
				}
				return true;
			case 3:
				if (args[0].equalsIgnoreCase("add")) {
					String s = plugin.getGameManager().addPlayer(args[1], args[2]);
					player.sendMessage(Utils.chat(s));	
				}
				return true;
				
			default:
				return false;
		}
		
			
	}
}
