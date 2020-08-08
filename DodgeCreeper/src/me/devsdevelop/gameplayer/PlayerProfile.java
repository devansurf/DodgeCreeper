package me.devsdevelop.gameplayer;

import java.util.UUID;

public class PlayerProfile {
	
	 private final String playerName;
	 private UUID uuid;
	 private boolean loaded;
	 
	 public PlayerProfile(String playerName, UUID uuid) {
		 this.uuid = uuid;
		 this.playerName = playerName;
	 }
	 
	 public UUID getUniqueId() {
		 return uuid;
	 }
	 public void setUniqueId(UUID uuid) {
		 this.uuid = uuid;
	 }
	 
	 
	
}
