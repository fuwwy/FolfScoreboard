package me.checkium.folfscoreboard.config;

import me.checkium.folfscoreboard.FolfScoreboard;
import me.checkium.folfscoreboard.ScoreboardLine;
import me.checkium.folfscoreboard.ScoreboardLinePool;
import me.checkium.folfscoreboard.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Configuration file
 * This is trash and should probably be changed, maybe when FolfAPI becomes a thing
 */
public class ScoreboardConfig {
    private Logger logger = new Logger("Config");
    private ScoreboardLinePool lines;
    private String title;
    private boolean debug;
    private double updateSec;


    public ScoreboardConfig() {
        logger.info("Carregando configuração...");
        if (!new File(FolfScoreboard.getPlugin().getDataFolder(), "config.yml").exists()) {
            logger.info("Configuração não encontrada, criando novo arquivo...");
            FolfScoreboard.getPlugin().getConfig().options().copyDefaults(true);
            FolfScoreboard.getPlugin().saveDefaultConfig();
            FolfScoreboard.getPlugin().reloadConfig();
        }
        title = ChatColor.translateAlternateColorCodes('&', FolfScoreboard.getPlugin().getConfig().getString("title"));
        lines = new ScoreboardLinePool();
        FolfScoreboard.getPlugin().getConfig().getStringList("lines").stream().map(ScoreboardLine::new).forEach(scoreboardLine -> lines.add(scoreboardLine));
        debug = FolfScoreboard.getPlugin().getConfig().getBoolean("debug");
        updateSec = FolfScoreboard.getPlugin().getConfig().getDouble("updateEveryXSeconds");
    }

    public boolean isDebug() {
        return debug;
    }

    public double getUpdateSec() {
        return updateSec;
    }

    public String getTitle() {
        return title;
    }

    public ScoreboardLinePool getLines() {
        return lines;
    }
/*
    public List<String> getLinesFor(Player p) {
        ArrayList<ChatColor> unusedColors = new ArrayList<>(Arrays.asList(ChatColor.values()));
        List<String> playerLines = new ArrayList<>();
        double maxLength = getLengthFor(title);
        for (String line : lines) {
            if (line.isEmpty() || line.equals(" ")) {
                ChatColor color = unusedColors.get(0);
                unusedColors.remove(0);
                line = color + " ";
                playerLines.add(line);
            } else if (line.startsWith("#") && line.endsWith("#")) {
                for (String s : getHookLines(line, p)) {
                    if (FolfScoreboard.getPlugin().isUseMVdW())
                        s = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, s);
                    if (FolfScoreboard.getPlugin().isUsePAPI())
                        s = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders((OfflinePlayer) p, s);
                    s = ChatColor.translateAlternateColorCodes('&', s);
                    playerLines.add(s);
                    if (s.length() > maxLength) maxLength = ChatColor.stripColor(s).length();
                }
            } else {
                if (FolfScoreboard.getPlugin().isUseMVdW())
                    line = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, line);
                if (FolfScoreboard.getPlugin().isUsePAPI())
                    line = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders((OfflinePlayer) p, line);
                line = ChatColor.translateAlternateColorCodes('&', line);
                playerLines.add(line);
            }
        }
        for (String playerLine : playerLines) {
            if (getLengthFor(playerLine) > maxLength) {
                maxLength = getLengthFor(playerLine);
                if (playerLine.startsWith("#") && playerLine.endsWith("$")) maxLength = maxLength - 2;
                if (playerLine.startsWith("#") && playerLine.endsWith("#")) maxLength = maxLength - 2;
            }
        }
        for (String playerLine : playerLines) {
            if (playerLine.startsWith("#") && playerLine.endsWith("$"))
                playerLines.set(playerLines.indexOf(playerLine), center(playerLine.substring(1, playerLine.length() - 1), maxLength));
        }
        return playerLines;
    }

    private String center(String source, double size) {
        double each = (size - source.length()) > 0 ? (size - source.length()) / 2 : size / 4;
        if (each > 0) {
            StringBuilder sourceBuilder = new StringBuilder(source);
            for (int i = 0; i < each; i++) {
                sourceBuilder = new StringBuilder(" " + sourceBuilder + " ");
            }
            int add = (int) Math.round(size / 3);
            for (int i = 0; i < add; i++) {
                sourceBuilder = new StringBuilder(" " + sourceBuilder);
            }
            source = sourceBuilder.toString();

        }
        return source;
    }

    private double getLengthFor(String s) {
        double length = 0;
        double currentAdd = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ChatColor.COLOR_CHAR || (i != 0 && s.charAt(i - 1) == ChatColor.COLOR_CHAR)) {
                if (c == ChatColor.COLOR_CHAR) {
                    char colorCode = s.charAt(i + 1);
                    currentAdd = colorCode == 'l' ? 1.2 : 1;
                }
            } else if (c == '.') {
                length += 0;
            } else {
                length += currentAdd;
               // length += currentlyDouble ? 1.2 : 1;
            }
        }
        return length;
    }

    private List<String> getHookLines(String line, Player p) {
        String hook = line.replaceAll("#", "");
        try {
            switch (hook.toLowerCase()) {
                case "factionshook":
                    return FolfScoreboard.getPlugin().getFactionsHook().getLinesFor(p);
                default:
                    return Collections.singletonList(line);
            }
        } catch (NullPointerException e) {
            return Collections.singletonList(line);
        }
    }*/

}
