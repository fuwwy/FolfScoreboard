package com.folfstore.folfscoreboard.providers;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessorManager;
import com.folfstore.folfscoreboard.utils.Logger;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RegionalScoreboardProvider implements ScoreboardProvider {
    private ScoreboardProvider defaultProvider;
    private HashMap<String, ScoreboardLine> regionalTitles = new HashMap<>();
    private HashMap<String, ScoreboardLinePool> regionalLines = new HashMap<>();

    public RegionalScoreboardProvider(ScoreboardProvider defaultProvider) {
        Logger logger = new Logger("RegionalScoreboardProvider");
        this.defaultProvider = defaultProvider;
        logger.info("Carregando configuração...");

        File configDirectory = new File(FolfScoreboard.getPlugin().getDataFolder(), "regionalScoreboards");
        if (!configDirectory.exists()) {
            configDirectory.mkdirs();
            FolfScoreboard.getPlugin().saveResource("regionalScoreboards/mine.yml", false);
            FolfScoreboard.getPlugin().saveResource("regionalScoreboards/arena.yml", false);
        }

        for (File file : configDirectory.listFiles()) {
            if (file.getName().endsWith(".yml")) {
                String id = file.getName().substring(0, file.getName().length() - 4);
                YamlConfiguration config = new YamlConfiguration();
                try {
                    config.load(file);
                    regionalTitles.put(id, new ScoreboardLine(config.getString("title"), 0));
                    ScoreboardLinePool lines = new ScoreboardLinePool();
                    config.getStringList("lines").stream().map(line -> new ScoreboardLine(line, lines.size())).forEach(lines::add);
                    regionalLines.put(id, lines);
                } catch (IOException | InvalidConfigurationException e) {
                    logger.info("A config " + file.getName() + " é invalida: ");
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    @Override
    public ScoreboardLinePool getScoreboardFor(Player player) {
        String id = getPlayerRegionId(player);
        ScoreboardLinePool lines = regionalLines.getOrDefault(id, null);

        if (lines != null) {
            lines = lines.clone();
            for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
                scoreboardProcessor.executeProcessor(lines, player);
            }
            return lines;
        }


        return defaultProvider.getScoreboardFor(player);
    }

    @Override
    public String getScoreboardTitleFor(Player player) {
        String id = getPlayerRegionId(player);

        ScoreboardLine titleLine = regionalTitles.getOrDefault(id, null);
        if (titleLine != null) {
            titleLine = titleLine.clone();
            for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
                scoreboardProcessor.executeProcessor(titleLine, player);
            }
            return titleLine.getLine();
        }

        return defaultProvider.getScoreboardTitleFor(player);
    }

    private String getPlayerRegionId(Player player) {
        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet regions = query.getApplicableRegions(player.getLocation());
        String selected = null;
        if (regions.size() > 0) {
            for (ProtectedRegion region : regions) {
                if (regionalTitles.containsKey(region.getId())) {
                    selected = region.getId();
                    break;
                }
            }
        }
        return selected;
    }
}
