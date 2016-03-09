package com.retrontology.prizes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
		filedir = new File(this.plugin.getDataFolder(), File.separator+"Contests"+File.separator+this.contest);
	}
	
	/* Class Methods */
	
	// Check to see if directory exists
	public boolean exists(){ return filedir.exists(); }
	
	// Check to see if a File exists
	public boolean fileExists(String s){ return getFile(s).exists(); }
	
	// Make directory if it doesn't exist
	// returns false if the directory already exists
	public boolean makeDir(){
		if(!exists()){
			filedir.mkdirs();
			return true;
		}else{ return false; }
	}
	
	// Get File in contest by name
	public File getFile(String s){ return new File(filedir, File.separator+s+".yml"); }
	
	// Get collection of files in directory
	
	
	// Make winners file from collection of OfflinePlayers
	// (for Top Survivor)
	// returns false if the file already exists
	public boolean makeFileFromList(List<OfflinePlayer> list){
		Date date = new Date();
		File file = getFile(""+date.getTime());
		if(!file.exists()){
			try {
		        file.createNewFile();
		        plugin.getServer().getLogger().info("[Prizes] File Created: Contests" + File.separator + filedir.getName() + File.separator + date.getTime() + ".yml");
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			for(int i = 1; (i <= plugin.getPrizesConfig().getNumberOfPrizes(filedir.getName())) && (i <= list.size()); i++){
				config.set(""+i, list.get(i-1).getName());
				try {
			        config.save(file);
			        plugin.getServer().getLogger().info("[Prizes] " + list.get(i-1).getName() + " has been set as place " + i + " in " );
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	
	// Check to see what prizes a player has
	
}
