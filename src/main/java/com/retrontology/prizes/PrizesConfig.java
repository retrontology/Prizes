/*    */ package com.retrontology.prizes;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrizesConfig
/*    */ {
/*    */   private File file;
/*    */   private FileConfiguration config;
/*    */   private Prizes plugin;
/*    */   
/*    */   public PrizesConfig(Prizes plugin) {
/* 21 */     this.plugin = plugin;
/*    */     
/* 23 */     File filedir = new File(this.plugin.getServer().getPluginManager().getPlugin("Prizes").getDataFolder().toString());
/* 24 */     if (!filedir.exists()) filedir.mkdir();
/*    */     
/* 26 */     this.file = new File(filedir, String.valueOf(File.separator) + "config.yml");
/* 27 */     if (!this.file.exists())
/* 28 */     { this.plugin.saveDefaultConfig();
/* 29 */       this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
/* 30 */       this.plugin.getServer().getLogger().info("[Prizes] No config file was found so the default file was copied over"); }
/* 31 */     else { this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getPrizeItemList(String splug, int place) {
/* 37 */     return (List<ItemStack>) this.config.getList(String.valueOf(splug) + "." + place + ".Items");
/*    */   }
/*    */   
/*    */   public int getPrizeXP(String splug, int place) {
/* 41 */     return this.config.getInt(String.valueOf(splug) + "." + place + ".XP");
/*    */   }
/*    */   
/*    */   public int getNumberOfPrizes(String splug) {
/* 45 */     return this.config.getInt(String.valueOf(splug) + ".NumberOfPrizes");
/*    */   }
/*    */   
/*    */   public List<String> getCommands(String splug, int place) {
/* 49 */     return this.config.getStringList(String.valueOf(splug) + "." + place + ".Commands");
/*    */   }
/*    */   
/*    */   public List<String> getAnnouncement(String splug, int place) {
/* 53 */     return this.config.getStringList(String.valueOf(splug) + "." + place + ".Announcements");
/*    */   }
/*    */   
/*    */   public List<String> getMessages(String splug, int place) {
/* 57 */     return this.config.getStringList(String.valueOf(splug) + "." + place + ".Messages");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean setList(String path, List list) {
/* 63 */     this.config.set(path, list);
/* 64 */     return save();
/*    */   }
/*    */   
/*    */   public boolean set(String path, Object o) {
/* 68 */     this.config.set(path, o);
/* 69 */     return save();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean save() {
/*    */     try {
/* 78 */       this.config.save(this.file);
/* 79 */       return true;
/* 80 */     } catch (IOException e) {
/* 81 */       e.printStackTrace();
/* 82 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */