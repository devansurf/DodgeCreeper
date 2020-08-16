package me.devsdevelop.powerup;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class PowerUpBlockGroup {

	private Location location;
	private Material glass;
	private boolean generated;
	private Map<String, Material> blockMap = new HashMap<String,Material>();
	
	public PowerUpBlockGroup(Location location, Material glass) {
		
		this.location = location;
		this.glass = glass;
		generated = true;
		generateBeacon();
	}
	
	public Location getLocation() {
		return this.location;
	}
	public Material getGlass() {
		return this.glass;
	}
	public boolean isGenerated() {
		return generated;
	}
	public void revertBlockGroup() {	
		int x = location.getBlockX();
		int y = location.getBlockY() - 1;
		int z = location.getBlockZ();
		 
		World world = location.getWorld();
		Block topBlock = world.getBlockAt(x,y, z);
		Block middleBlock = world.getBlockAt(x, y-1, z);
		
		topBlock.setType(blockMap.get("top"));
		middleBlock.setType(blockMap.get("middle"));
		
		for (int yPoint = y+1; yPoint <= y+64; yPoint++) {
			Block aboveBlock = world.getBlockAt(x, yPoint, z);
			if (!aboveBlock.getType().equals(Material.AIR)) { // if its not air
				aboveBlock.setType(blockMap.get("top"+yPoint));
			}
		}
		
		for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) {  // creates 3x3 grid of iron blocks centering on the location
		    for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {  
		    	Block bottomBlock = world.getBlockAt(xPoint, y-2, zPoint);
		    	bottomBlock.setType(blockMap.get("bottom"+xPoint+zPoint));	       	      
		    }
		} 
		blockMap.clear();
		generated = false;
	}
	private void generateBeacon() {
		int x = location.getBlockX();
		int y = location.getBlockY() - 1;
		int z = location.getBlockZ();
		 
		// TO DO, make a check for any blocks above the beacon, and replace anything that isnt air into glass
		World world = location.getWorld();
		Block topBlock = world.getBlockAt(x, y , z);
		Block middleBlock = world.getBlockAt(x, y-1, z);
		blockMap.put("top", topBlock.getType());
		blockMap.put("middle", middleBlock.getType());
		
		
		for (int yPoint = y+1; yPoint <= y+64; yPoint++) {
			Block aboveBlock = world.getBlockAt(x, yPoint, z);
			if (!aboveBlock.getType().equals(Material.AIR)) { // if its not air
				blockMap.put("top"+yPoint, aboveBlock.getType());
				aboveBlock.setType(glass);
			}
		}
		topBlock.setType(glass);
		middleBlock.setType(Material.BEACON); // beacon block, under the glass
		
		for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) {  // creates 3x3 grid of iron blocks centering on the location
		    for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {  
		    	Block bottomBlock = world.getBlockAt(xPoint, y-2, zPoint);
		    	blockMap.put("bottom" + xPoint + zPoint, bottomBlock.getType());
		    	bottomBlock .setType(Material.IRON_BLOCK);
		       	      
		    }
		} 
	}
	
}
