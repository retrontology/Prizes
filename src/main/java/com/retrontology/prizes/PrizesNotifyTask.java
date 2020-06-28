/*    */ package com.retrontology.prizes;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class PrizesNotifyTask
/*    */   extends BukkitRunnable {
/*    */   private Prizes plugin;
/*  9 */   private PrizesNotify pnotify = new PrizesNotify();
/*    */ 
/*    */ 
/*    */   
/*    */   public PrizesNotifyTask(Prizes plugin) {}
/*    */ 
/*    */   
/*    */   public void run() {
/* 17 */     Bukkit.getPluginManager().callEvent(this.pnotify);
/*    */   }
/*    */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesNotifyTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */