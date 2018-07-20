/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.retrontology.prizes;

import com.retrontology.prizes.PrizesCommandExecutor;
import com.retrontology.prizes.PrizesConfig;
import com.retrontology.prizes.PrizesContest;
import com.retrontology.prizes.PrizesListener;
import com.retrontology.prizes.PrizesNotifyTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class Prizes
extends JavaPlugin {
    private PrizesConfig pconfig;
    private static Server server;
    private static Prizes plugin;

    public void onEnable() {
        this.pconfig = new PrizesConfig(this);
        server = this.getServer();
        plugin = this;
        File filecontests = new File(plugin.getDataFolder(), String.valueOf(File.separator) + "Contests");
        if (!filecontests.exists()) {
            filecontests.mkdirs();
        }
        server.getPluginManager().registerEvents((Listener)new PrizesListener(this), (Plugin)this);
        BukkitScheduler scheduler = server.getScheduler();
        scheduler.scheduleSyncRepeatingTask((Plugin)this, (BukkitRunnable)new PrizesNotifyTask(this), 0, 12000);
        this.getCommand("prizes").setExecutor((CommandExecutor)new PrizesCommandExecutor(this));
    }

    public void onDisable() {
    }

    public PrizesConfig getPrizesConfig() {
        return this.pconfig;
    }

    public List<PrizesContest> getPrizesContests() {
        ArrayList<PrizesContest> list = new ArrayList<PrizesContest>();
        File[] arrfile = new File(this.getDataFolder(), String.valueOf(File.separator) + "Contests").listFiles();
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File file = arrfile[n2];
            if (file.isDirectory()) {
                list.add(new PrizesContest(this, file.getName()));
            }
            ++n2;
        }
        return list;
    }

    public List<String> getPlayerPrizes(UUID player) {
        ArrayList<String> list = new ArrayList<String>();
        for (PrizesContest contest : plugin.getPrizesContests()) {
            list.addAll(contest.checkPrizes(player));
        }
        return list;
    }

    public static boolean makeFileFromUUIDList(String contest, List<UUID> list) {
        PrizesContest pcontest = new PrizesContest((Prizes)server.getPluginManager().getPlugin("Prizes"), contest);
        if (pcontest.exists()) {
            pcontest.makeFileFromList(list);
            server.broadcastMessage((Object)ChatColor.AQUA + "The " + contest + " contest is now over!");
            server.broadcastMessage((Object)ChatColor.AQUA + "Use /prizes check to see if you won anything and /prizes claim to claim them");
            return true;
        }
        server.getLogger().info("[Prizes] The specified Contest was not found");
        return false;
    }

    public static boolean registerContest(String contest) {
        PrizesContest pcontest = new PrizesContest((Prizes)server.getPluginManager().getPlugin("Prizes"), contest);
        return pcontest.makeDir();
    }
}

