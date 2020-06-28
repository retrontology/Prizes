/*    */ package com.retrontology.prizes;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerLoginEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrizesListener
/*    */   implements Listener
/*    */ {
/*    */   Prizes plugin;
/*    */   
/*    */   public PrizesListener(Prizes plugin) {
/* 21 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onLogin(PlayerLoginEvent event) {
/* 28 */     final Player player = event.getPlayer();
/*    */     
/* 30 */     BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
/* 31 */     scheduler.scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable()
/*    */         {
/*    */           public void run() {
/* 34 */             List<String> list = PrizesListener.this.plugin.getPlayerPrizes(player.getUniqueId());
/* 35 */             if (!list.isEmpty()) {
/* 36 */               player.sendMessage(ChatColor.AQUA + "You have " + list.size() + " prize(s) waiting to be claimed");
/* 37 */               player.sendMessage(ChatColor.AQUA + "Type " + ChatColor.YELLOW + "/prizes check" + ChatColor.AQUA + " to check your prize(s) and " + ChatColor.YELLOW + "/prizes claim" + ChatColor.AQUA + " to claim them");
/*    */             } 
/*    */           }
/* 40 */         },  80L);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onNotify(PrizesNotify Event) {
/* 46 */     this.plugin.getLogger().info("Notifying players of pending prizes");
/* 47 */     for (Player player : this.plugin.getServer().getOnlinePlayers()) {
/* 48 */       List<String> list = this.plugin.getPlayerPrizes(player.getUniqueId());
/* 49 */       if (!list.isEmpty()) {
/* 50 */         player.sendMessage(ChatColor.AQUA + "You have " + list.size() + " prize(s) waiting to be claimed");
/* 51 */         player.sendMessage(ChatColor.AQUA + "Type " + ChatColor.YELLOW + "/prizes check" + ChatColor.AQUA + " to check your prize(s) and " + ChatColor.YELLOW + "/prizes claim" + ChatColor.AQUA + " to claim them");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */