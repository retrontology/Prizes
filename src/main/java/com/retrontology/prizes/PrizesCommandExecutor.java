/*     */ package com.retrontology.prizes;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrizesCommandExecutor
/*     */   implements CommandExecutor
/*     */ {
/*     */   Prizes plugin;
/*     */   
/*     */   public PrizesCommandExecutor(Prizes plugin) {
/*  24 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/*  30 */     if (cmd.getName().equalsIgnoreCase("prizes")) {
/*  31 */       Player player = (Player)sender;
/*  32 */       if (args.length >= 1) {
/*     */         
/*  34 */         if (args[0].equalsIgnoreCase("check")) {
/*     */           
/*  36 */           if (args.length == 1) {
/*  37 */             if (player.hasPermission("prizes.citizen")) {
/*  38 */               List<String> list = new ArrayList<>();
/*  39 */               for (PrizesContest contest : this.plugin.getPrizesContests()) list.addAll(contest.checkPrizes(player.getUniqueId())); 
/*  40 */               if (!list.isEmpty()) {
/*  41 */                 player.sendMessage(ChatColor.AQUA + "You have " + list.size() + " prizes waiting to be claimed:");
/*  42 */                 player.sendMessage(ChatColor.AQUA + " ==== " + player.getName() + " ==== ");
/*  43 */                 for (String s : list) player.sendMessage(ChatColor.AQUA + "  " + s); 
/*  44 */                 return true;
/*     */               } 
/*  46 */               player.sendMessage(ChatColor.RED + "You don't have any prizes to claim :(");
/*  47 */               return true;
/*     */             } 
/*     */             
/*  50 */             player.sendMessage(ChatColor.RED + "Become a citizen to check your prizes");
/*  51 */             return true;
/*     */           } 
/*     */           
/*  54 */           if (args.length == 2) {
/*  55 */             if (player.hasPermission("prizes.admin")) {
/*  56 */               OfflinePlayer offplayer = this.plugin.getServer().getOfflinePlayer(args[1]);
/*  57 */               if (offplayer.hasPlayedBefore()) {
/*  58 */                 List<String> list = new ArrayList<>();
/*  59 */                 for (PrizesContest contest : this.plugin.getPrizesContests()) list.addAll(contest.checkPrizes(offplayer.getUniqueId())); 
/*  60 */                 if (!list.isEmpty()) {
/*  61 */                   player.sendMessage(ChatColor.AQUA + args[1] + " has " + list.size() + " prize(s) waiting to be claimed:");
/*  62 */                   player.sendMessage(ChatColor.AQUA + " ==== " + args[1] + " ==== ");
/*  63 */                   for (String s : list) player.sendMessage(ChatColor.AQUA + " - " + s); 
/*  64 */                   return true;
/*     */                 } 
/*  66 */                 player.sendMessage(ChatColor.RED + args[1] + " doesn't have any prizes to claim :(");
/*  67 */                 return true;
/*     */               } 
/*     */               
/*  70 */               player.sendMessage(ChatColor.RED + args[1] + " hasn't played on this server");
/*  71 */               return true;
/*     */             } 
/*     */             
/*  74 */             player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
/*  75 */             return true;
/*     */           } 
/*     */           
/*  78 */           player.sendMessage(ChatColor.YELLOW + "Usage:");
/*  79 */           player.sendMessage(ChatColor.YELLOW + "/prizes check");
/*  80 */           return true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  85 */         if (args[0].equalsIgnoreCase("claim")) {
/*  86 */           if (args.length == 1) {
/*  87 */             if (player.hasPermission("prizes.citizen")) {
/*  88 */               List<String> list = new ArrayList<>();
/*  89 */               for (PrizesContest contest : this.plugin.getPrizesContests()) list.addAll(contest.checkPrizes(player.getUniqueId())); 
/*  90 */               if (!list.isEmpty()) {
/*  91 */                 for (PrizesContest contest : this.plugin.getPrizesContests()) {
/*  92 */                   if (!contest.claimPrizes(player)) {
/*  93 */                     player.sendMessage(ChatColor.RED + "You don't have enough space in your inv to claim all of your prizes");
/*  94 */                     return true;
/*     */                   } 
/*     */                 } 
/*     */               } else {
/*  98 */                 player.sendMessage(ChatColor.RED + "You don't have any prizes to claim :(");
/*  99 */                 return true;
/*     */               } 
/* 101 */               player.sendMessage(ChatColor.AQUA + "All of your prizes have been claimed!");
/* 102 */               return true;
/*     */             } 
/* 104 */             player.sendMessage(ChatColor.RED + "Become a citizen to claim your prizes");
/* 105 */             return true;
/*     */           } 
/*     */           
/* 108 */           player.sendMessage(ChatColor.YELLOW + "Usage:");
/* 109 */           player.sendMessage(ChatColor.YELLOW + "/prizes claim");
/* 110 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 114 */         if (args[0].equalsIgnoreCase("register")) {
/* 115 */           if (player.hasPermission("prizes.admin")) {
/* 116 */             if (args.length == 2) {
/* 117 */               PrizesContest pcontest = new PrizesContest(this.plugin, args[1]);
/* 118 */               if (!pcontest.exists()) {
/* 119 */                 if (pcontest.makeDir()) {
/* 120 */                   player.sendMessage(ChatColor.GREEN + "The contest " + args[1] + " has been created");
/* 121 */                   this.plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " has been created");
/* 122 */                   return true;
/*     */                 } 
/* 124 */                 player.sendMessage(ChatColor.RED + "The contest " + args[1] + " could not be created for some reason");
/* 125 */                 this.plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " could not be created for some reason");
/*     */               } else {
/*     */                 
/* 128 */                 player.sendMessage(ChatColor.RED + "The contest " + args[1] + " already exists");
/* 129 */                 return true;
/*     */               } 
/*     */             } else {
/* 132 */               player.sendMessage(ChatColor.YELLOW + "Usage:");
/* 133 */               player.sendMessage(ChatColor.YELLOW + "/prizes register <contest>");
/* 134 */               return true;
/*     */             } 
/*     */           } else {
/* 137 */             player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
/* 138 */             return true;
/*     */           } 
/*     */         }
/*     */         
/* 142 */         if (args[0].equalsIgnoreCase("make")) {
/* 143 */           if (player.hasPermission("prizes.admin")) {
/* 144 */             if (args.length == 3) {
/* 145 */               PrizesContest pcontest = new PrizesContest(this.plugin, args[1]);
/* 146 */               if (pcontest.exists()) {
/* 147 */                 File file = pcontest.getFile(args[2]);
/* 148 */                 if (!file.exists()) {
/*     */                   try {
/* 150 */                     file.createNewFile();
/* 151 */                     player.sendMessage(ChatColor.GREEN + "The file " + args[2] + " has been created for the " + args[1] + " contest");
/* 152 */                     return true;
/* 153 */                   } catch (IOException e) {
/* 154 */                     e.printStackTrace();
/* 155 */                     player.sendMessage(ChatColor.RED + "The file " + args[2] + " could not be created for the " + args[1] + " contest");
/* 156 */                     return true;
/*     */                   } 
/*     */                 }
/* 159 */                 player.sendMessage(ChatColor.RED + "The file " + args[2] + " already exists in the " + args[1] + " contest");
/* 160 */                 return true;
/*     */               } 
/*     */               
/* 163 */               player.sendMessage(ChatColor.RED + "The contest " + args[1] + " does not exist");
/* 164 */               return true;
/*     */             } 
/*     */             
/* 167 */             player.sendMessage(ChatColor.YELLOW + "Usage:");
/* 168 */             player.sendMessage(ChatColor.YELLOW + "/prizes make <contest>  <file>");
/* 169 */             return true;
/*     */           } 
/*     */           
/* 172 */           player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
/* 173 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 177 */         if (args[0].equalsIgnoreCase("set")) {
/* 178 */           if (player.hasPermission("prizes.admin")) {
/* 179 */             if (args.length == 5) {
/* 180 */               PrizesContest pcontest = new PrizesContest(this.plugin, args[1]);
/* 181 */               if (pcontest.exists()) {
/* 182 */                 if (pcontest.getFile(args[2]).exists()) {
/*     */                   
/* 184 */                   int place = 0;
/* 185 */                   for (int i = 0; i < args[3].length(); i++) {
/*     */                     
/* 187 */                     if (Character.getNumericValue(args[3].charAt(i)) > 9) {
/* 188 */                       player.sendMessage(ChatColor.RED + "Please enter a valid integer for the contest place");
/* 189 */                       return true;
/*     */                     } 
/*     */                     
/* 192 */                     place *= 10;
/*     */                     
/* 194 */                     place += Character.getNumericValue(args[3].charAt(i));
/*     */                   } 
/*     */ 
/*     */                   
/* 198 */                   if (args[4].equalsIgnoreCase("true") || args[4].equalsIgnoreCase("false")) {
/* 199 */                     if (pcontest.getPlayer(args[2], place) == null) {
/* 200 */                       player.sendMessage(ChatColor.RED + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " does not have a player set to it");
/* 201 */                       return true;
/*     */                     } 
/* 203 */                     boolean flag = args[4].equalsIgnoreCase("true");
/* 204 */                     if (pcontest.setClaimed(args[2], place, flag)) {
/* 205 */                       player.sendMessage(ChatColor.GREEN + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
/* 206 */                       this.plugin.getLogger().info("[Prizes] The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
/* 207 */                       return true;
/*     */                     } 
/* 209 */                     player.sendMessage(ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
/* 210 */                     return true;
/*     */                   } 
/*     */ 
/*     */                   
/* 214 */                   if (pcontest.setPlayer(args[2], place, args[4]) && pcontest.setClaimed(args[2], place, false)) {
/* 215 */                     player.sendMessage(ChatColor.GREEN + "The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
/* 216 */                     this.plugin.getLogger().info("[Prizes] The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
/* 217 */                     return true;
/*     */                   } 
/* 219 */                   player.sendMessage(ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
/* 220 */                   return true;
/*     */                 } 
/*     */ 
/*     */                 
/* 224 */                 player.sendMessage(ChatColor.RED + "The file " + args[1] + " does not exist in the " + args[0] + " contest");
/* 225 */                 return true;
/*     */               } 
/*     */               
/* 228 */               player.sendMessage(ChatColor.RED + "The contest " + args[1] + " does not exist");
/* 229 */               return true;
/*     */             } 
/*     */             
/* 232 */             player.sendMessage(ChatColor.YELLOW + "Usage:");
/* 233 */             player.sendMessage(ChatColor.YELLOW + "/prizes set <contest> <file> <place> <player>");
/* 234 */             player.sendMessage(ChatColor.YELLOW + "/prizes set <contest> <file> <place> <true/false>");
/* 235 */             return true;
/*     */           } 
/*     */           
/* 238 */           player.sendMessage(ChatColor.RED + "What do you think you are doing :I");
/* 239 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesCommandExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */