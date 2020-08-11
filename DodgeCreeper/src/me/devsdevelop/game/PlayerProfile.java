package me.devsdevelop.game;

import java.util.UUID;

public class PlayerProfile {  // class that will store player stats in the future.
	
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
