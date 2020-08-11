package me.devsdevelop.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;



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
	

	
}
