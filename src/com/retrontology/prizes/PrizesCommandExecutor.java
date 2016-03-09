package com.retrontology.prizes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrizesCommandExecutor implements CommandExecutor {
	
	/* Class Variables*/
	Prizes plugin;

	/* Constructor */
	public PrizesCommandExecutor(Prizes plugin) {
		this.plugin = plugin;
	}
	
	/* Command Executor */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		return false;
	}

}
