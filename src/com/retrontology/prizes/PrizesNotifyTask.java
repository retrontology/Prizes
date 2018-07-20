/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Event
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.retrontology.prizes;

import com.retrontology.prizes.Prizes;
import com.retrontology.prizes.PrizesNotify;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

public class PrizesNotifyTask
extends BukkitRunnable {
    private Prizes plugin;
    private PrizesNotify pnotify = new PrizesNotify();

    public PrizesNotifyTask(Prizes plugin) {
    }

    public void run() {
        Bukkit.getPluginManager().callEvent((Event)this.pnotify);
    }
}

