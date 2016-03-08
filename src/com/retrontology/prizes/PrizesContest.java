package com.retrontology.prizes;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class PrizesContest {
	
	/* Class Variables */
	private Prizes plugin;
	private File filedir;
	private String contest;
	private HashMap<String, FileConfiguration> configs;

	/* Constructor */
	public PrizesContest(Prizes plugin, String contest) {
		// Init variables
		this.plugin = plugin;
		this.contest = contest;
		filedir = new File(this.plugin.getDataFolder(), File.separator+this.contest);
	}
	
	/* Class Methods */
	
	// Check to see if directory exists
	public boolean exists(){
		return filedir.exists();
	}
	
	// Make directory if it doesn't exist
	// returns false if the directory already exists
	public boolean make(){
		if(!exists()){
			filedir.mkdirs();
			return true;
		}else{ return false; }
	}
	
	// Get collection of files in directory
	
	
	// Make winners file from collection of OfflinePlayers
	// (for Top Survivor)
	
	
	// Check to see what prizes a player has
}
