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
	private static Prizes plugin;

	/* Startup */
	@Override
	public void onEnable() {
		// Init class variables
		pconfig = new PrizesConfig(this);
		server = this.getServer();
		plugin = this;
		
		// Init folders
		File filecontests = new File(this.plugin.getDataFolder(), File.separator+"Contests");
		if(!filecontests.exists()){ filecontests.mkdirs(); }
		
		// Register Events
		server.getPluginManager().registerEvents(new PrizesListener(this), this);
		
		// Register Commands
		this.getCommand("prizes").setExecutor(new PrizesCommandExecutor(this));
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
	
	// Get list of Prizes for player
	public List<String> getPlayerPrizes(String player){
		List<String> list = new ArrayList<String>();
		for(PrizesContest contest : plugin.getPrizesContests()){ list.addAll(contest.checkPrizes(player));}
		return list;
	}
	
	// Make file from List of Strings
	public static boolean makeFileFromStringList(String contest, List<String> list){
		PrizesContest pcontest = new PrizesContest((Prizes) server.getPluginManager().getPlugin("Prizes"), contest);
		if(pcontest.exists()){
			pcontest.makeFileFromList(list);
			return true;
		}else{ 
			server.getLogger().info("[Prizes] The specified Contest was not found");
			return false; 
		}
	}
	
	// Make file from List of OfflinePlayers
	public static boolean makeFileFromList(String contest, List<OfflinePlayer> list){
		List<String> stringlist = new ArrayList<String>();
		for(OfflinePlayer player : list){ stringlist.add(player.getName()); }
		PrizesContest pcontest = new PrizesContest((Prizes) server.getPluginManager().getPlugin("Prizes"), contest);
		if(pcontest.exists()){
			pcontest.makeFileFromList(stringlist);
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
