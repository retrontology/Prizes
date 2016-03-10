package com.retrontology.prizes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
		if(cmd.getName().equalsIgnoreCase("prizes")){
			Player player = (Player) sender;
			if(args.length > 0){
				// Check Prizes for player
				if(args[0].equalsIgnoreCase("check")){
					// View self
					if(args.length == 1){
						if(player.hasPermission("prizes.citizen")){
							List<String> list = new ArrayList<String>();
							for(PrizesContest contest : plugin.getPrizesContests()){ list.addAll(contest.checkPrizes(player.getName()));}
							if(!list.isEmpty()){
								player.sendMessage(ChatColor.AQUA + "You have " + list.size() + " prizes waiting to be claimed:");
								player.sendMessage(ChatColor.AQUA + " ==== " + player.getName() + " ==== ");
								for(String s : list){ player.sendMessage(ChatColor.AQUA + "  " + s); }
								return true;
							}else{
								player.sendMessage(ChatColor.RED + "You don't have any prizes to claim :(");
								return true;
							}
						}else{
							player.sendMessage(ChatColor.RED + "Become a citizen to check your prizes");
							return true;
						}
					// View Player
					}else if(args.length == 2){
						if(player.hasPermission("prizes.admin")){
							List<String> list = new ArrayList<String>();
							for(PrizesContest contest : plugin.getPrizesContests()){ list.addAll(contest.checkPrizes(args[1]));}
							if(!list.isEmpty()){
								player.sendMessage(ChatColor.AQUA + args[1] + " has " + list.size() + " prizes waiting to be claimed:");
								player.sendMessage(ChatColor.AQUA + " ==== " + args[1] + " ==== ");
								for(String s : list){ player.sendMessage(ChatColor.AQUA + " - " + s); }
								return true;
							}else{
								player.sendMessage(ChatColor.RED + args[1] + " doesn't have any prizes to claim :(");
								return true;
							}
						}else{
							player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
							return true;
						}
					}else{
						player.sendMessage(ChatColor.YELLOW + "Usage:");
						player.sendMessage(ChatColor.YELLOW + "/prizes check");
						return true;
					}
				}
				
				// Claim Prizes
				if(args[0].equalsIgnoreCase("claim")){
					if(args.length == 1){
						if(player.hasPermission("prizes.citizen")){
							List<String> list = new ArrayList<String>();
							for(PrizesContest contest : plugin.getPrizesContests()){ list.addAll(contest.checkPrizes(player.getName()));}
							if(!list.isEmpty()){
								for(PrizesContest contest : plugin.getPrizesContests()){
									if(!contest.claimPrizes(player)) {
										player.sendMessage(ChatColor.RED + "You don't have enough space in your inv to claim all of your prizes");
										return true;
									}
								}
							}else{
								player.sendMessage(ChatColor.RED + "You don't have any prizes to claim :(");
								return true;
							}
							player.sendMessage(ChatColor.AQUA + "All of your prizes have been claimed!");
							return true;
						}else{
							player.sendMessage(ChatColor.RED + "Become a citizen to claim your prizes");
							return true;
						}
					}else{
						player.sendMessage(ChatColor.YELLOW + "Usage:");
						player.sendMessage(ChatColor.YELLOW + "/prizes claim");
						return true;
					}
				}
			}
		}
		return false;
	}

}
