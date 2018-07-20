/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.retrontology.prizes;

import com.retrontology.prizes.Prizes;
import com.retrontology.prizes.PrizesNotify;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class PrizesListener
implements Listener {
    Prizes plugin;

    public PrizesListener(Prizes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                List<String> list = PrizesListener.this.plugin.getPlayerPrizes(player.getUniqueId());
                if (!list.isEmpty()) {
                    player.sendMessage((Object)ChatColor.AQUA + "You have " + list.size() + " prize(s) waiting to be claimed");
                    player.sendMessage((Object)ChatColor.AQUA + "Type " + (Object)ChatColor.YELLOW + "/prizes check" + (Object)ChatColor.AQUA + " to check your prize(s) and " + (Object)ChatColor.YELLOW + "/prizes claim" + (Object)ChatColor.AQUA + " to claim them");
                }
            }
        }, 80);
    }

    @EventHandler
    public void onNotify(PrizesNotify Event) {
        this.plugin.getLogger().info("Notifying players of pending prizes");
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            List<String> list = this.plugin.getPlayerPrizes(player.getUniqueId());
            if (list.isEmpty()) continue;
            player.sendMessage((Object)ChatColor.AQUA + "You have " + list.size() + " prize(s) waiting to be claimed");
            player.sendMessage((Object)ChatColor.AQUA + "Type " + (Object)ChatColor.YELLOW + "/prizes check" + (Object)ChatColor.AQUA + " to check your prize(s) and " + (Object)ChatColor.YELLOW + "/prizes claim" + (Object)ChatColor.AQUA + " to claim them");
        }
    }

}

