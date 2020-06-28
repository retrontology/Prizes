/*    */ package com.retrontology.prizes;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Prizes
/*    */   extends JavaPlugin
/*    */ {
/*    */   private PrizesConfig pconfig;
/*    */   private static Server server;
/*    */   private static Prizes plugin;
/*    */   
/*    */   public void onEnable() {
/* 25 */     this.pconfig = new PrizesConfig(this);
/* 26 */     server = getServer();
/* 27 */     plugin = this;
/*    */ 
/*    */     
/* 30 */     File filecontests = new File(plugin.getDataFolder(), String.valueOf(File.separator) + "Contests");
/* 31 */     if (!filecontests.exists()) filecontests.mkdirs();
/*    */ 
/*    */     
/* 34 */     server.getPluginManager().registerEvents(new PrizesListener(this), (Plugin)this);
/*    */ 
/*    */     
/* 37 */     BukkitScheduler scheduler = server.getScheduler();
/* 38 */     scheduler.scheduleSyncRepeatingTask((Plugin)this, new PrizesNotifyTask(this), 0L, 12000L);
/*    */ 
/*    */     
/* 41 */     getCommand("prizes").setExecutor(new PrizesCommandExecutor(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrizesConfig getPrizesConfig() {
/* 54 */     return this.pconfig;
/*    */   }
/*    */   
/*    */   public List<PrizesContest> getPrizesContests() {
/* 58 */     List<PrizesContest> list = new ArrayList<>(); byte b; int i; File[] arrayOfFile;
/* 59 */     for (i = (arrayOfFile = (new File(getDataFolder(), String.valueOf(File.separator) + "Contests")).listFiles()).length, b = 0; b < i; ) { File file = arrayOfFile[b];
/* 60 */       if (file.isDirectory()) list.add(new PrizesContest(this, file.getName()));  b++; }
/*    */     
/* 62 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getPlayerPrizes(UUID player) {
/* 67 */     List<String> list = new ArrayList<>();
/* 68 */     for (PrizesContest contest : plugin.getPrizesContests()) list.addAll(contest.checkPrizes(player)); 
/* 69 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean makeFileFromUUIDList(String contest, List<UUID> list) {
/* 74 */     PrizesContest pcontest = new PrizesContest((Prizes)server.getPluginManager().getPlugin("Prizes"), contest);
/* 75 */     if (pcontest.exists()) {
/* 76 */       pcontest.makeFileFromList(list);
/* 77 */       server.broadcastMessage(ChatColor.AQUA + "The " + contest + " contest is now over!");
/* 78 */       server.broadcastMessage(ChatColor.AQUA + "Use /prizes check to see if you won anything and /prizes claim to claim them");
/* 79 */       return true;
/*    */     } 
/* 81 */     server.getLogger().info("[Prizes] The specified Contest was not found");
/* 82 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean registerContest(String contest) {
/* 88 */     PrizesContest pcontest = new PrizesContest((Prizes)server.getPluginManager().getPlugin("Prizes"), contest);
/* 89 */     return pcontest.makeDir();
/*    */   }
/*    */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/Prizes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */