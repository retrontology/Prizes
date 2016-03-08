package com.retrontology.prizes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Prizes extends JavaPlugin {
	
	/* Class Variables */
	PrizesConfig pconfig;

	/* Startup */
	@Override
	public void onEnable() {
		// Init class variables
		pconfig = new PrizesConfig(this);
	}
	
	
	/* Shutdown */
	@Override
	public void onDisable() {
		
	}
	
	
	/* Class Methods */
	
	
}
