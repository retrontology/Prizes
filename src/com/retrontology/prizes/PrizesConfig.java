package com.retrontology.prizes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class PrizesConfig {
	
	/* Class Variables */
	private File file;
	private FileConfiguration config;
	private Prizes plugin;
	
	/* Constructor */
	public PrizesConfig(Prizes plugin){
			// Set Class Variables
			this.plugin = plugin;
			// Init directory if it's not there
			File filedir = new File(this.plugin.getServer().getPluginManager().getPlugin("Prizes").getDataFolder().toString());
	        if (!filedir.exists()) { filedir.mkdir(); }
	        // Load config or copy over default if there isn't one
	        file = new File(filedir, File.separator+"config.yml");
	        if(!file.exists()){
				this.plugin.saveDefaultConfig();
				config = YamlConfiguration.loadConfiguration(file);
				this.plugin.getServer().getLogger().info("[Prizes] No config file was found so the default file was copied over");
			}else{ config = YamlConfiguration.loadConfiguration(file); }
	}
	
	/* Get Methods */
	
	public List<ItemStack> getPrizeItemList(String splug, int place){
		return (List<ItemStack>) config.getList(splug+"."+place+".Items");
	}
	
	public int getPrizeXP(String splug, int place){
		return config.getInt(splug+"."+place+".XP");
	}
	
	public int getNumberOfPrizes(String splug){
		return config.getInt(splug+".NumberOfPrizes");
	}
	
	/* Set Methods */
	
	public boolean setList(String path, List list){
		config.set(path, list);
		return save();
	}
	
	public boolean set(String path, Object o){
		config.set(path, o);
		return save();
	}
	
	
	/* Class Methods */
	
	// Save config to file (usually used after setting a value)
	public boolean save(){
		try {
	        config.save(file);
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
