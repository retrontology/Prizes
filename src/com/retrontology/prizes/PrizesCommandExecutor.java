package com.retrontology.prizes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
			if(args.length >= 1){
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
								player.sendMessage(ChatColor.AQUA + args[1] + " has " + list.size() + " prize(s) waiting to be claimed:");
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
				// Register a Contest
				if(args[0].equalsIgnoreCase("register")){
					if(player.hasPermission("prizes.admin")){
						if(args.length == 2){
							PrizesContest pcontest = new PrizesContest(plugin, args[1]);
							if(!pcontest.exists()){
								if(pcontest.makeDir()){
									player.sendMessage(ChatColor.GREEN + "The contest " + args[1] + " has been created");
									plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " has been created");
									return true;
								}else{
									player.sendMessage(ChatColor.RED + "The contest " + args[1] + " could not be created for some reason");
									plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " could not be created for some reason");
								}
							}else{
								player.sendMessage(ChatColor.RED + "The contest " + args[1] + " already exists");
								return true;
							}
						}else{
							player.sendMessage(ChatColor.YELLOW + "Usage:");
							player.sendMessage(ChatColor.YELLOW + "/prizes register <contest>");
							return true;
						}
					}else{
						player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
						return true;
					}
				}
				// Make a blank file in a contest
				if(args[0].equalsIgnoreCase("make")){
					if(player.hasPermission("prizes.admin")){
						if(args.length == 3){
							PrizesContest pcontest = new PrizesContest(plugin, args[1]);
							if(pcontest.exists()){
								File file = pcontest.getFile(args[2]);
								if(!file.exists()){
									try {
								        file.createNewFile();
								        player.sendMessage(ChatColor.GREEN + "The file " + args[1] + " has been created for the " + args[0] + " contest");
								        return true;
								    } catch (IOException e) {
								        e.printStackTrace();
								        player.sendMessage(ChatColor.RED + "The file " + args[1] + " could not be created for the " + args[0] + " contest");
								        return true;
								    }
								}else{
									player.sendMessage(ChatColor.RED + "The file " + args[1] + " already exists in the " + args[0] + " contest");
									return true;
								}
							}else{
								player.sendMessage(ChatColor.RED + "The contest " + args[1] + " does not exist");
								return true;
							}
						}else{
							player.sendMessage(ChatColor.YELLOW + "Usage:");
							player.sendMessage(ChatColor.YELLOW + "/prizes make <contest>  <file>");
							return true;
						}
					}else{
						player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
						return true;
					}
				}
				// Set command
				if(args[0].equalsIgnoreCase("set")){
					if(player.hasPermission("prizes.admin")){
						if(args.length == 5){
							PrizesContest pcontest = new PrizesContest(plugin, args[1]);
							if(pcontest.exists()){
								if(pcontest.getFile(args[2]).exists()){
									// Get int from place arg
									int place = 0;
									for(int i = 0; i < args[3].length(); i++){
										// Signal for player info if not a number
										if(Character.getNumericValue(args[3].charAt(i)) > 9){
											player.sendMessage(ChatColor.RED + "Please enter a valid integer for the contest place");
											return true;
										}else{
											// Move number left
											place *= 10;
											// Add next digit
											place += (Character.getNumericValue(args[3].charAt(i)));
										}
									}
									// Distinguish between player and boolean
									if(args[4].equalsIgnoreCase("true") || args[4].equalsIgnoreCase("false")){
										if(pcontest.getPlayer(args[2], place) == null){
											player.sendMessage(ChatColor.RED + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " does not have a player set to it");
											return true;
										}else{
											boolean flag = (args[4].equalsIgnoreCase("true")) ? true : false;
											if(pcontest.setClaimed(args[2], place, flag)){
												player.sendMessage(ChatColor.GREEN + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
												plugin.getLogger().info("[Prizes] The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
												return true;
											}else{
												player.sendMessage(ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
												return true;
											}
										}
									}else{
										if(pcontest.setPlayer(args[2], place, args[4]) && pcontest.setClaimed(args[2], place, false)){
											player.sendMessage(ChatColor.GREEN + "The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
											plugin.getLogger().info("[Prizes] The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
											return true;
										}else{
											player.sendMessage(ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
											return true;
										}
									}
								}else{
									player.sendMessage(ChatColor.RED + "The file " + args[1] + " does not exist in the " + args[0] + " contest");
									return true;
								}
							}else{
								player.sendMessage(ChatColor.RED + "The contest " + args[1] + " does not exist");
								return true;
							}
						}else{
							player.sendMessage(ChatColor.YELLOW + "Usage:");
							player.sendMessage(ChatColor.YELLOW + "/prizes set <contest> <file> <place> <player>");
							player.sendMessage(ChatColor.YELLOW + "/prizes set <contest> <file> <place> <true/false>");
							return true;
						}
					}else{
						player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
						return true;
					}
				}
			}
		}
		return false;
	}

}
