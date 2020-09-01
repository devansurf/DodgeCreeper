package me.devsdevelop.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.devsdevelop.DodgeCreeper;

public class GameTab implements TabCompleter{

	private DodgeCreeper plugin;
	List<String> arguments1 = new ArrayList<String>();
	List<String> arguments2 = new ArrayList<String>();
	List<String> playerNames = new ArrayList<String>();
	
	public GameTab(DodgeCreeper plugin) {
		this.plugin = plugin;
		plugin.getCommand("dc").setTabCompleter(this);
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (arguments1.isEmpty()) {
			arguments1.add("start"); arguments1.add("create");
			arguments1.add("stop"); arguments1.add("addall");
			arguments1.add("add"); arguments1.add("remove");
			
			arguments2.add("blue"); arguments2.add("red");
		}
		
		List<String> result = new ArrayList<String>();
		
		if (args.length == 1) {
			for (String s : arguments1) {  // tab completer for default starting game commands.
				if (s.toLowerCase().startsWith(args[0].toLowerCase())) { // if the parameters given are similar to an argument, add it to result.
					result.add(s);
				}		
			}
		}
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) { 
				if (playerNames.isEmpty()) {
					for (Player player : Bukkit.getServer().getOnlinePlayers()) { // returns all online players.
						playerNames.add(player.getName());
					}
				}
				for (String s : playerNames) {  // tab completer for players online
					if (s.toLowerCase().startsWith(args[1].toLowerCase())) { // if the parameters given are similar to an argument, recommend a result.
						result.add(s);
					}		
				}		
			}
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("add")) {
				for (String s : arguments2) { // tab completer for player teams.
					if (s.toLowerCase().startsWith(args[2].toLowerCase())) { // if the parameters given are similar to an argument, recommend a result.
						result.add(s);
					}	
				}
			}
		}
		return result;
	}

}
