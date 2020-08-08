package me.devsdevelop.gameplayer;

import org.bukkit.Color;

public enum TeamColor {
	RED(Color.RED),BLUE(Color.BLUE);
	
	private final Color color;
	
    TeamColor(Color color) {
		this.color = color;
	}
    
    public Color getColor() {
    	return color;
    }
}
