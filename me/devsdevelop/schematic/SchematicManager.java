package me.devsdevelop.schematic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sun.javafx.geom.Rectangle;

import me.devsdevelop.DodgeCreeper;
import me.devsdevelop.utils.Utils;

public class SchematicManager {

	
	private DodgeCreeper plugin;
	private Location location;
	
	private int maxBlocks;
	private Clipboard clipboard;
	public SchematicManager (DodgeCreeper plugin) {
		this.plugin = plugin;
		maxBlocks = 100000; // 1 hundred thousand
	}
	public boolean loadSchematic(Player player) {
		location = player.getLocation();
		File schematic = new File(plugin.getDataFolder() + File.separator + "/schematics/BArena.schem"); // biome arena
		ClipboardFormat format = ClipboardFormats.findByFile(schematic);
		try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) { // find the schematic
		    clipboard = reader.read();
		} catch (FileNotFoundException e) {
			
			Bukkit.getLogger().severe(Utils.chat("&cSchematic not found"));  // catch if schematic not found
			e.printStackTrace();
			return false;
		} catch (IOException e) {		
			e.printStackTrace();
			return false;
		}
		
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession // find the session where the schematic will be pasted
				(new BukkitWorld(location.getWorld()), maxBlocks)) {
		    Operation operation = new ClipboardHolder(clipboard)
		            .createPaste(editSession)		// paste to this world / session
		            .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())) // offset of paste
		            // configure here
		            .build(); // construct the build
		    try {
				Operations.complete(operation);
				return true;
			} catch (WorldEditException e) {
				Bukkit.getLogger().severe(Utils.chat("&cSomething went wrong while constructing schematic"));
				e.printStackTrace();
				return false;
			}
		}
	}
	public Location getLocation() {
		return location;
	}
}
