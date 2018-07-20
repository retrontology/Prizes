/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.retrontology.prizes;

import com.retrontology.prizes.Prizes;
import com.retrontology.prizes.PrizesContest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrizesCommandExecutor
implements CommandExecutor {
    Prizes plugin;

    public PrizesCommandExecutor(Prizes plugin) {
        this.plugin = plugin;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("prizes") == false) return false;
        player = (Player)sender;
        if (args.length < 1) return false;
        if (!args[0].equalsIgnoreCase("check")) ** GOTO lbl26
        if (args.length != 1) ** GOTO lbl12
        if (!player.hasPermission("prizes.citizen")) {
            player.sendMessage((Object)ChatColor.RED + "Become a citizen to check your prizes");
            return true;
        }
        list = new ArrayList<String>();
        var8_10 = this.plugin.getPrizesContests().iterator();
        ** GOTO lbl100
lbl12: // 1 sources:
        if (args.length != 2) {
            player.sendMessage((Object)ChatColor.YELLOW + "Usage:");
            player.sendMessage((Object)ChatColor.YELLOW + "/prizes check");
            return true;
        }
        if (!player.hasPermission("prizes.admin")) {
            player.sendMessage((Object)ChatColor.RED + "What do you think you are doing :I");
            return true;
        }
        offplayer = this.plugin.getServer().getOfflinePlayer(args[1]);
        if (!offplayer.hasPlayedBefore()) {
            player.sendMessage((Object)ChatColor.RED + args[1] + " hasn't played on this server");
            return true;
        }
        list = new ArrayList<String>();
        var9_20 = this.plugin.getPrizesContests().iterator();
        ** GOTO lbl112
lbl26: // 1 sources:
        if (!args[0].equalsIgnoreCase("claim")) ** GOTO lbl37
        if (args.length != 1) {
            player.sendMessage((Object)ChatColor.YELLOW + "Usage:");
            player.sendMessage((Object)ChatColor.YELLOW + "/prizes claim");
            return true;
        }
        if (!player.hasPermission("prizes.citizen")) {
            player.sendMessage((Object)ChatColor.RED + "Become a citizen to claim your prizes");
            return true;
        }
        list = new ArrayList<String>();
        s = this.plugin.getPrizesContests().iterator();
        ** GOTO lbl124
lbl37: // 1 sources:
        if (args[0].equalsIgnoreCase("register")) {
            if (!player.hasPermission("prizes.admin")) {
                player.sendMessage((Object)ChatColor.RED + "What do you think you are doing :I");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage((Object)ChatColor.YELLOW + "Usage:");
                player.sendMessage((Object)ChatColor.YELLOW + "/prizes register <contest>");
                return true;
            }
            pcontest = new PrizesContest(this.plugin, args[1]);
            if (pcontest.exists()) {
                player.sendMessage((Object)ChatColor.RED + "The contest " + args[1] + " already exists");
                return true;
            }
            if (pcontest.makeDir()) {
                player.sendMessage((Object)ChatColor.GREEN + "The contest " + args[1] + " has been created");
                this.plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " has been created");
                return true;
            }
            player.sendMessage((Object)ChatColor.RED + "The contest " + args[1] + " could not be created for some reason");
            this.plugin.getServer().getLogger().info("[Prizes] The contest " + args[1] + " could not be created for some reason");
        }
        if (args[0].equalsIgnoreCase("make")) {
            if (!player.hasPermission("prizes.admin")) {
                player.sendMessage((Object)ChatColor.RED + "What do you think you are doing :I");
                return true;
            }
            if (args.length != 3) {
                player.sendMessage((Object)ChatColor.YELLOW + "Usage:");
                player.sendMessage((Object)ChatColor.YELLOW + "/prizes make <contest>  <file>");
                return true;
            }
            pcontest = new PrizesContest(this.plugin, args[1]);
            if (!pcontest.exists()) {
                player.sendMessage((Object)ChatColor.RED + "The contest " + args[1] + " does not exist");
                return true;
            }
            file = pcontest.getFile(args[2]);
            if (file.exists()) {
                player.sendMessage((Object)ChatColor.RED + "The file " + args[2] + " already exists in the " + args[1] + " contest");
                return true;
            }
            try {
                file.createNewFile();
                player.sendMessage((Object)ChatColor.GREEN + "The file " + args[2] + " has been created for the " + args[1] + " contest");
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                player.sendMessage((Object)ChatColor.RED + "The file " + args[2] + " could not be created for the " + args[1] + " contest");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("set") == false) return false;
        if (!player.hasPermission("prizes.admin")) {
            player.sendMessage((Object)ChatColor.RED + "What do you think you are doing :I");
            return true;
        }
        if (args.length != 5) {
            player.sendMessage((Object)ChatColor.YELLOW + "Usage:");
            player.sendMessage((Object)ChatColor.YELLOW + "/prizes set <contest> <file> <place> <player>");
            player.sendMessage((Object)ChatColor.YELLOW + "/prizes set <contest> <file> <place> <true/false>");
            return true;
        }
        pcontest = new PrizesContest(this.plugin, args[1]);
        if (!pcontest.exists()) {
            player.sendMessage((Object)ChatColor.RED + "The contest " + args[1] + " does not exist");
            return true;
        }
        if (!pcontest.getFile(args[2]).exists()) {
            player.sendMessage((Object)ChatColor.RED + "The file " + args[1] + " does not exist in the " + args[0] + " contest");
            return true;
        }
        place = 0;
        i = 0;
        ** GOTO lbl140
lbl-1000: // 1 sources:
        {
            contest = var8_10.next();
            list.addAll(contest.checkPrizes(player.getUniqueId()));
lbl100: // 2 sources:
            ** while (var8_10.hasNext())
        }
lbl101: // 1 sources:
        if (list.isEmpty()) {
            player.sendMessage((Object)ChatColor.RED + "You don't have any prizes to claim :(");
            return true;
        }
        player.sendMessage((Object)ChatColor.AQUA + "You have " + list.size() + " prizes waiting to be claimed:");
        player.sendMessage((Object)ChatColor.AQUA + " ==== " + player.getName() + " ==== ");
        for (String s : list) {
            player.sendMessage((Object)ChatColor.AQUA + "  " + s);
        }
        return true;
lbl-1000: // 1 sources:
        {
            contest = var9_20.next();
            list.addAll(contest.checkPrizes(offplayer.getUniqueId()));
lbl112: // 2 sources:
            ** while (var9_20.hasNext())
        }
lbl113: // 1 sources:
        if (list.isEmpty()) {
            player.sendMessage((Object)ChatColor.RED + args[1] + " doesn't have any prizes to claim :(");
            return true;
        }
        player.sendMessage((Object)ChatColor.AQUA + args[1] + " has " + list.size() + " prize(s) waiting to be claimed:");
        player.sendMessage((Object)ChatColor.AQUA + " ==== " + args[1] + " ==== ");
        for (String s : list) {
            player.sendMessage((Object)ChatColor.AQUA + " - " + s);
        }
        return true;
lbl-1000: // 1 sources:
        {
            contest = s.next();
            list.addAll(contest.checkPrizes(player.getUniqueId()));
lbl124: // 2 sources:
            ** while (s.hasNext())
        }
lbl125: // 1 sources:
        if (list.isEmpty()) {
            player.sendMessage((Object)ChatColor.RED + "You don't have any prizes to claim :(");
            return true;
        }
        for (PrizesContest contest : this.plugin.getPrizesContests()) {
            if (contest.claimPrizes(player)) continue;
            player.sendMessage((Object)ChatColor.RED + "You don't have enough space in your inv to claim all of your prizes");
            return true;
        }
        player.sendMessage((Object)ChatColor.AQUA + "All of your prizes have been claimed!");
        return true;
lbl-1000: // 1 sources:
        {
            if (Character.getNumericValue(args[3].charAt(i)) > 9) {
                player.sendMessage((Object)ChatColor.RED + "Please enter a valid integer for the contest place");
                return true;
            }
            place *= 10;
            place += Character.getNumericValue(args[3].charAt(i));
            ++i;
lbl140: // 2 sources:
            ** while (i < args[3].length())
        }
lbl141: // 1 sources:
        if (args[4].equalsIgnoreCase("true") || args[4].equalsIgnoreCase("false")) {
            if (pcontest.getPlayer(args[2], place) == null) {
                player.sendMessage((Object)ChatColor.RED + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " does not have a player set to it");
                return true;
            }
            v0 = flag = args[4].equalsIgnoreCase("true") != false;
            if (pcontest.setClaimed(args[2], place, flag)) {
                player.sendMessage((Object)ChatColor.GREEN + "The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
                this.plugin.getLogger().info("[Prizes] The place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + flag);
                return true;
            }
            player.sendMessage((Object)ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
            return true;
        }
        if (pcontest.setPlayer(args[2], place, args[4]) && pcontest.setClaimed(args[2], place, false)) {
            player.sendMessage((Object)ChatColor.GREEN + "The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
            this.plugin.getLogger().info("[Prizes] The player in place " + place + " in file " + args[2] + " in the contest " + args[1] + " has been set to " + args[4]);
            return true;
        }
        player.sendMessage((Object)ChatColor.RED + "The file " + args[2] + " in the contest " + args[1] + " could not be saved for some reason");
        return true;
    }
}

