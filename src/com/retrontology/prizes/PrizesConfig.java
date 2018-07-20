/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.retrontology.prizes;

import com.retrontology.prizes.Prizes;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PrizesConfig {
    private File file;
    private FileConfiguration config;
    private Prizes plugin;

    public PrizesConfig(Prizes plugin) {
        this.plugin = plugin;
        File filedir = new File(this.plugin.getServer().getPluginManager().getPlugin("Prizes").getDataFolder().toString());
        if (!filedir.exists()) {
            filedir.mkdir();
        }
        this.file = new File(filedir, String.valueOf(File.separator) + "config.yml");
        if (!this.file.exists()) {
            this.plugin.saveDefaultConfig();
            this.config = YamlConfiguration.loadConfiguration((File)this.file);
            this.plugin.getServer().getLogger().info("[Prizes] No config file was found so the default file was copied over");
        } else {
            this.config = YamlConfiguration.loadConfiguration((File)this.file);
        }
    }

    public List<ItemStack> getPrizeItemList(String splug, int place) {
        return this.config.getList(String.valueOf(splug) + "." + place + ".Items");
    }

    public int getPrizeXP(String splug, int place) {
        return this.config.getInt(String.valueOf(splug) + "." + place + ".XP");
    }

    public int getNumberOfPrizes(String splug) {
        return this.config.getInt(String.valueOf(splug) + ".NumberOfPrizes");
    }

    public List<String> getCommands(String splug, int place) {
        return this.config.getStringList(String.valueOf(splug) + "." + place + ".Commands");
    }

    public List<String> getAnnouncement(String splug, int place) {
        return this.config.getStringList(String.valueOf(splug) + "." + place + ".Announcements");
    }

    public List<String> getMessages(String splug, int place) {
        return this.config.getStringList(String.valueOf(splug) + "." + place + ".Messages");
    }

    public boolean setList(String path, List list) {
        this.config.set(path, (Object)list);
        return this.save();
    }

    public boolean set(String path, Object o) {
        this.config.set(path, o);
        return this.save();
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

