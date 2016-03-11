package com.retrontology.prizes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PrizesListener implements Listener {
	
	/* Class Variables */
	
	Prizes plugin;
	
	/* Constructor */
	public PrizesListener(Prizes plugin) {
		this.plugin = plugin;
	}
	
	/* Events */
	// Player Login
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		final Player player = event.getPlayer();
		// Needs to wait a bit for player to register, otherwise it will thrown an exception
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	List<String> list = plugin.getPlayerPrizes(player.getName());
	        		if(!list.isEmpty()){
	        			player.sendMessage(ChatColor.AQUA + "You have " + list.size() + " prizes waiting to be claimed");
	        			player.sendMessage(ChatColor.AQUA + "Type /prizes check to check your prizes and /prizes claim to claim them");
	        		}
	            }
	        }, 10L);
	}

}
