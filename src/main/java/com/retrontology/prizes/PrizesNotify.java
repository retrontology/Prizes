/*    */ package com.retrontology.prizes;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PrizesNotify
/*    */   extends Event {
/*  8 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public HandlerList getHandlers() {
/* 11 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 15 */     return handlers;
/*    */   }
/*    */ }


/* Location:              /home/blazer/Downloads/Prizes.jar!/com/retrontology/prizes/PrizesNotify.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */