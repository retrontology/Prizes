package com.retrontology.prizes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrizesContest {
	
	/* Class Variables */
	private Prizes plugin;
	private File filedir;
	private String contest;

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
	public List<File> getFileList(){ return new ArrayList<File>(Arrays.asList(filedir.listFiles())); }
	
	// Make winners file from collection of OfflinePlayers
	// (for Top Survivor)
	// returns false if the file already exists
	public boolean makeFileFromList(List<String> list){
		Date date = new Date();
		File file = getFile(""+date.getTime());
		if(!file.exists()){
			try {
		        file.createNewFile();
		        plugin.getServer().getLogger().info("[Prizes] File Created: Contests" + File.separator + contest + File.separator + date.getTime() + ".yml");
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			for(int i = 1; (i <= plugin.getPrizesConfig().getNumberOfPrizes(contest)) && (i <= list.size()); i++){
				config.set(i+".Name", list.get(i-1));
				config.set(i+".Claimed", false);
				try {
			        config.save(file);
			        plugin.getServer().getLogger().info("[Prizes] " + list.get(i-1) + " has been set as place " + i + " in " );
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
	public List<String> checkPrizes(String player){
		List<String> prizestrings = new ArrayList<String>();
		for(File contestfile : getFileList()){
			FileConfiguration config = YamlConfiguration.loadConfiguration(contestfile);
			for(int i = 1; (config.getString(""+i) != null); i++){
				if(player.equalsIgnoreCase(config.getString(i+".Name")) && !config.getBoolean(i+".Claimed")){
					prizestrings.add(contest + ": " + i);
				}
			}
		}
		return prizestrings;
	}
	
	// Claim a players prizes
	// Returns false if the player can't hold as many items as they can claim
	public boolean claimPrizes(Player player){
		boolean invfull = false;
		int freespace = 0;
		for (ItemStack i : player.getInventory()) { if (i == null) { freespace++; } }
		if(freespace == 0){ invfull = true; }
		for(File file : getFileList()){
			if(invfull){ break; }
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			for(int i = 1; (config.getString(""+i) != null); i++){
				if(player.getName().equalsIgnoreCase(config.getString(i+".Name")) && !config.getBoolean(i+".Claimed")){
					List<ItemStack> list = plugin.getPrizesConfig().getPrizeItemList(contest, i);
					if(list.size()>freespace){
						invfull = true;
						break;
					}else{
						for(ItemStack items : list){ player.getInventory().addItem(items); }
						freespace -= list.size();
						player.giveExp(plugin.getPrizesConfig().getPrizeXP(contest, i));
						config.set(i+".Claimed", true);
						try {
					        config.save(file);
					    } catch (IOException e) {
					        e.printStackTrace();
					    }
					}
				}
			}
		}
		return !invfull;
	}
	
}
