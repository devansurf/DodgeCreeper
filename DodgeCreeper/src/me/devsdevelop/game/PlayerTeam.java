package me.devsdevelop.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devsdevelop.utils.Utils;



public class PlayerTeam {
	
	private TeamColor teamColor;
	private List<Player> teamMembers = new ArrayList<Player>();
	
	public PlayerTeam(TeamColor teamColor) {	// team of type color
		this.teamColor = teamColor;
	}
	
	public TeamColor getTeamColor() {
		return teamColor;
	}
	
	public void addTeamMember(Player player) {
		teamMembers.add(player);
	}
	
	public List<Player> getTeamMembers(){
		return teamMembers;
	}
	
	public int getTotalMembers() {
		return teamMembers.size();
	}
	public boolean containsMember(Player player) {
		if (teamMembers.contains(player)) {
			return true;
		}
		return false;
	}
	
	public void clearMembers() {
		teamMembers.clear();
	}
	
	public ItemStack[] getTeamArmor(int enchantLevel) {
		return new ItemStack[] {Utils.createCustomArmor(Material.LEATHER_BOOTS, teamColor.toString(), enchantLevel), 
								Utils.createCustomArmor(Material.LEATHER_LEGGINGS, teamColor.toString(), enchantLevel),
								Utils.createCustomArmor(Material.LEATHER_CHESTPLATE, teamColor.toString(), enchantLevel),
								Utils.createCustomArmor(Material.LEATHER_HELMET, teamColor.toString(), enchantLevel)};
		
	}

	
}
