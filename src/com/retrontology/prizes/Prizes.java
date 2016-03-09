package com.retrontology.prizes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class Prizes extends JavaPlugin {
	
	/* Class Variables */
	private PrizesConfig pconfig;
	private static Server server;

	/* Startup */
	@Override
	public void onEnable() {
		// Init class variables
		pconfig = new PrizesConfig(this);
		server = this.getServer();
	}
	
	
	/* Shutdown */
	@Override
	public void onDisable() {
	}
	
	
	/* Class Methods */
	
	// Get PrizesConfig
	public PrizesConfig getPrizesConfig(){ return pconfig; }
	
	// Get all PrizesContest(s)
	public List<PrizesContest> getPrizesContests(){
		List<PrizesContest> list = new ArrayList<PrizesContest>();
		for(File file : new File(this.getDataFolder(), File.separator + "Contests").listFiles()){
			if(file.isDirectory()){ list.add(new PrizesContest(this, file.getName())); }
		}
		return list;
	}
	
	// Make file
	public static boolean makeFileFromList(String contest, List<OfflinePlayer> list){
		PrizesContest pcontest = new PrizesContest((Prizes) server.getPluginManager().getPlugin("Prizes"), contest);
		if(pcontest.exists()){
			pcontest.makeFileFromList(list);
			return true;
		}else{ 
			server.getLogger().info("[Prizes] The specified Contest was not found");
			return false; 
		}
	}
	
	// Register Contest
	public static boolean registerContest(String contest){
		PrizesContest pcontest = new PrizesContest((Prizes) server.getPluginManager().getPlugin("Prizes"), contest);
		return pcontest.makeDir();
	}
	
}
