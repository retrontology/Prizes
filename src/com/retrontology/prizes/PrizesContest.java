/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package com.retrontology.prizes;

import com.retrontology.prizes.Prizes;
import com.retrontology.prizes.PrizesConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PrizesContest {
    private Prizes plugin;
    private File filedir;
    private String contest;

    public PrizesContest(Prizes plugin, String contest) {
        this.plugin = plugin;
        this.contest = contest;
        this.filedir = new File(this.plugin.getDataFolder(), String.valueOf(File.separator) + "Contests" + File.separator + this.contest);
    }

    public boolean exists() {
        return this.filedir.exists();
    }

    public boolean fileExists(String s) {
        return this.getFile(s).exists();
    }

    public boolean makeDir() {
        if (!this.exists()) {
            this.filedir.mkdirs();
            return true;
        }
        return false;
    }

    public String getPlayer(String file, int place) {
        return YamlConfiguration.loadConfiguration((File)this.getFile(file)).getString(String.valueOf(place) + ".Name");
    }

    public boolean getClaimed(String file, int place) {
        return YamlConfiguration.loadConfiguration((File)this.getFile(file)).getBoolean(String.valueOf(place) + ".Claimed");
    }

    public boolean setPlayer(String file, int place, String player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)this.getFile(file));
        OfflinePlayer offplayer = this.plugin.getServer().getOfflinePlayer(player);
        if (offplayer.getFirstPlayed() != 0) {
            config.set(String.valueOf(place) + ".Name", (Object)offplayer.getName());
            config.set(String.valueOf(place) + ".UUID", (Object)offplayer.getUniqueId().toString());
            try {
                config.save(this.getFile(file));
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean setClaimed(String file, int place, boolean b) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)this.getFile(file));
        config.set(String.valueOf(place) + ".Claimed", (Object)b);
        try {
            config.save(this.getFile(file));
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile(String s) {
        return new File(this.filedir, String.valueOf(File.separator) + s + ".yml");
    }

    public List<File> getFileList() {
        return new ArrayList<File>(Arrays.asList(this.filedir.listFiles()));
    }

    public boolean makeFileFromList(List<UUID> list) {
        Date date = new Date();
        File file = this.getFile("" + date.getTime());
        if (!file.exists()) {
            try {
                file.createNewFile();
                this.plugin.getServer().getLogger().info("[Prizes] File Created: Contests" + File.separator + this.contest + File.separator + date.getTime() + ".yml");
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)file);
            int i = 1;
            while (i <= this.plugin.getPrizesConfig().getNumberOfPrizes(this.contest) && i <= list.size()) {
                OfflinePlayer offplayer = this.plugin.getServer().getOfflinePlayer(list.get(i - 1));
                config.set(String.valueOf(i) + ".Name", (Object)offplayer.getName());
                config.set(String.valueOf(i) + ".UUID", (Object)list.get(i - 1).toString());
                config.set(String.valueOf(i) + ".Claimed", (Object)false);
                try {
                    config.save(file);
                    this.plugin.getServer().getLogger().info("[Prizes] " + list.get(i - 1) + " has been set as place " + i + " in ");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                ++i;
            }
            return true;
        }
        return false;
    }

    public List<String> checkPrizes(UUID player) {
        ArrayList<String> prizestrings = new ArrayList<String>();
        for (File contestfile : this.getFileList()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)contestfile);
            int i = 1;
            while (config.getString("" + i) != null) {
                if (player.equals(UUID.fromString(config.getString(String.valueOf(i) + ".UUID"))) && !config.getBoolean(String.valueOf(i) + ".Claimed")) {
                    prizestrings.add(String.valueOf(this.contest) + ": " + i);
                }
                ++i;
            }
        }
        return prizestrings;
    }

    public boolean claimPrizes(Player player) {
        boolean invfull = false;
        int freespace = 0;
        for (ItemStack i : player.getInventory()) {
            if (i != null) continue;
            ++freespace;
        }
        if (freespace == 0) {
            invfull = true;
        }
        block3 : for (File file : this.getFileList()) {
            if (invfull) break;
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)file);
            int i2 = 1;
            while (config.getString("" + i2) != null) {
                if (player.getUniqueId().equals(UUID.fromString(config.getString(String.valueOf(i2) + ".UUID"))) && !config.getBoolean(String.valueOf(i2) + ".Claimed")) {
                    String s;
                    List<ItemStack> list = this.plugin.getPrizesConfig().getPrizeItemList(this.contest, i2);
                    if (list.size() > freespace) {
                        invfull = true;
                        continue block3;
                    }
                    for (ItemStack items : list) {
                        player.getInventory().addItem(new ItemStack[]{items});
                    }
                    freespace -= list.size();
                    player.giveExp(this.plugin.getPrizesConfig().getPrizeXP(this.contest, i2));
                    Iterator iterator = this.plugin.getPrizesConfig().getAnnouncement(this.contest, i2).iterator();
                    while (iterator.hasNext()) {
                        s = (String)iterator.next();
                        s = s.replaceAll("<player>", player.getName());
                        this.plugin.getServer().broadcastMessage(s);
                    }
                    iterator = this.plugin.getPrizesConfig().getMessages(this.contest, i2).iterator();
                    while (iterator.hasNext()) {
                        s = (String)iterator.next();
                        s = s.replaceAll("<player>", player.getName());
                        player.sendMessage(s);
                    }
                    config.set(String.valueOf(i2) + ".Claimed", (Object)true);
                    try {
                        config.save(file);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ++i2;
            }
        }
        return !invfull;
    }

    public boolean equals(String s) {
        return s.equals(this.contest);
    }
}

