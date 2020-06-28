/*     */ package com.retrontology.prizes;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrizesContest
/*     */ {
/*     */   private Prizes plugin;
/*     */   private File filedir;
/*     */   private String contest;
/*     */   
/*     */   public PrizesContest(Prizes plugin, String contest) {
/*  27 */     this.plugin = plugin;
/*  28 */     this.contest = contest;
/*  29 */     this.filedir = new File(this.plugin.getDataFolder(), String.valueOf(File.separator) + "Contests" + File.separator + this.contest);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/*  35 */     return this.filedir.exists();
/*     */   }
/*     */   public boolean fileExists(String s) {
/*  38 */     return getFile(s).exists();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean makeDir() {
/*  43 */     if (!exists()) {
/*  44 */       this.filedir.mkdirs();
/*  45 */       return true;
/*  46 */     }  return false;
/*     */   }
/*     */   
/*     */   public String getPlayer(String file, int place) {
/*  50 */     return YamlConfiguration.loadConfiguration(getFile(file)).getString(String.valueOf(place) + ".Name");
/*     */   }
/*     */   public boolean getClaimed(String file, int place) {
/*  53 */     return YamlConfiguration.loadConfiguration(getFile(file)).getBoolean(String.valueOf(place) + ".Claimed");
/*     */   }
/*     */   
/*     */   public boolean setPlayer(String file, int place, String player) {
/*  57 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(getFile(file));
/*  58 */     OfflinePlayer offplayer = this.plugin.getServer().getOfflinePlayer(player);
/*  59 */     if (offplayer.getFirstPlayed() != 0L) {
/*  60 */       yamlConfiguration.set(String.valueOf(place) + ".Name", offplayer.getName());
/*  61 */       yamlConfiguration.set(String.valueOf(place) + ".UUID", offplayer.getUniqueId().toString());
/*     */       try {
/*  63 */         yamlConfiguration.save(getFile(file));
/*  64 */         return true;
/*  65 */       } catch (IOException e) {
/*  66 */         e.printStackTrace();
/*  67 */         return false;
/*     */       } 
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setClaimed(String file, int place, boolean b) {
/*  76 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(getFile(file));
/*  77 */     yamlConfiguration.set(String.valueOf(place) + ".Claimed", Boolean.valueOf(b));
/*     */     try {
/*  79 */       yamlConfiguration.save(getFile(file));
/*  80 */       return true;
/*  81 */     } catch (IOException e) {
/*  82 */       e.printStackTrace();
/*  83 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public File getFile(String s) {
/*  88 */     return new File(this.filedir, String.valueOf(File.separator) + s + ".yml");
/*     */   }
/*     */   public List<File> getFileList() {
/*  91 */     return new ArrayList<>(Arrays.asList(this.filedir.listFiles()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean makeFileFromList(List<UUID> list) {
/*  97 */     Date date = new Date();
/*  98 */     File file = getFile(date.getTime());
/*  99 */     if (!file.exists()) {
/*     */       try {
/* 101 */         file.createNewFile();
/* 102 */         this.plugin.getServer().getLogger().info("[Prizes] File Created: Contests" + File.separator + this.contest + File.separator + date.getTime() + ".yml");
/* 103 */       } catch (IOException e) {
/* 104 */         e.printStackTrace();
/* 105 */         return false;
/*     */       } 
/* 107 */       YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/* 108 */       for (int i = 1; i <= this.plugin.getPrizesConfig().getNumberOfPrizes(this.contest) && i <= list.size(); i++) {
/* 109 */         OfflinePlayer offplayer = this.plugin.getServer().getOfflinePlayer(list.get(i - 1));
/* 110 */         yamlConfiguration.set(String.valueOf(i) + ".Name", offplayer.getName());
/* 111 */         yamlConfiguration.set(String.valueOf(i) + ".UUID", ((UUID)list.get(i - 1)).toString());
/* 112 */         yamlConfiguration.set(String.valueOf(i) + ".Claimed", Boolean.valueOf(false));
/*     */         try {
/* 114 */           yamlConfiguration.save(file);
/* 115 */           this.plugin.getServer().getLogger().info("[Prizes] " + list.get(i - 1) + " has been set as place " + i + " in ");
/* 116 */         } catch (IOException e) {
/* 117 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/* 120 */       return true;
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> checkPrizes(UUID player) {
/* 128 */     List<String> prizestrings = new ArrayList<>();
/* 129 */     for (File contestfile : getFileList()) {
/* 130 */       YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(contestfile);
/* 131 */       for (int i = 1; yamlConfiguration.getString(i) != null; i++) {
/* 132 */         if (player.equals(UUID.fromString(yamlConfiguration.getString(String.valueOf(i) + ".UUID"))) && !yamlConfiguration.getBoolean(String.valueOf(i) + ".Claimed")) {
/* 133 */           prizestrings.add(String.valueOf(this.contest) + ": " + i);
/*     */         }
/*     */       } 
/*     */     } 
/* 137 */     return prizestrings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean claimPrizes(Player player) {
/* 143 */     boolean invfull = false;
/* 144 */     int freespace = 0;
/* 145 */     for (ItemStack i : player.getInventory()) { if (i == null) freespace++;  }
/* 146 */      if (freespace == 0) invfull = true; 
/* 147 */     for (File file : getFileList()) {
/* 148 */       if (invfull)
/* 149 */         break;  YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/* 150 */       for (int i = 1; yamlConfiguration.getString(i) != null; i++) {
/* 151 */         if (player.getUniqueId().equals(UUID.fromString(yamlConfiguration.getString(String.valueOf(i) + ".UUID"))) && !yamlConfiguration.getBoolean(String.valueOf(i) + ".Claimed")) {
/* 152 */           List<ItemStack> list = this.plugin.getPrizesConfig().getPrizeItemList(this.contest, i);
/* 153 */           if (list.size() > freespace) {
/* 154 */             invfull = true;
/*     */             break;
/*     */           } 
/* 157 */           for (ItemStack items : list) { player.getInventory().addItem(new ItemStack[] { items }); }
/* 158 */            freespace -= list.size();
/* 159 */           player.giveExp(this.plugin.getPrizesConfig().getPrizeXP(this.contest, i));
/* 160 */           for (String s : this.plugin.getPrizesConfig().getAnnouncement(this.contest, i)) {
/* 161 */             s = s.replaceAll("<player>", player.getName());
/* 162 */             this.plugin.getServer().broadcastMessage(s);
/*     */           } 
/* 164 */           for (String s : this.plugin.getPrizesConfig().getMessages(this.contest, i)) {
/* 165 */             s = s.replaceAll("<player>", player.getName());
/* 166 */             player.sendMessage(s);
/*     */           } 
/* 168 */           yamlConfiguration.set(String.valueOf(i) + ".Claimed", Boolean.valueOf(true));
/*     */           try {
/* 170 */             yamlConfiguration.save(file);
/* 171 */           } catch (IOException e) {
/* 172 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return !invfull;
/*     */   }
/*     */   
/*     */   public boolean equals(String s) {
/* 182 */     return s.equals(this.contest);
/*     */   }
/*     */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesContest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */