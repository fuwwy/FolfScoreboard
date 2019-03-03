package com.folfstore.folfscoreboard;

import com.folfstore.folfscoreboard.config.ScoreboardConfig;
import com.folfstore.folfscoreboard.hooks.impl.FactionsHook;
import com.folfstore.folfscoreboard.commands.Score;
import com.folfstore.folfscoreboard.replacers.impl.ChatColorReplacer;
import com.folfstore.folfscoreboard.replacers.impl.PlaceholderAPIReplacer;
import com.folfstore.folfscoreboard.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Old main class
 * Needs heavy updating to new system, for ex. hooks vs replacers
 */
public class FolfScoreboard extends JavaPlugin {

    Logger logger = new Logger();
    private static FolfScoreboard plugin;
    ScoreboardConfig scoreboardConfig;
    ScoreboardRegisterer r;
    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Ativando FolfScoreboard v" + getDescription().getVersion());
        scoreboardConfig = new ScoreboardConfig();
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            logger.info("Encontrado MVdWPlaceholderAPI, hooking...");
           //TODO useMVdW = true;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            logger.info("Encontrado PlaceholderAPI, hooking...");
            new PlaceholderAPIReplacer();
        }
        new ChatColorReplacer();

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            logger.info("Encontrado Factions, hooking...");
            new FactionsHook();
        }
        logger.info("Iniciando o registrador de scoreboards...");
        r = new ScoreboardRegisterer();
        Bukkit.getPluginManager().registerEvents(r, this);
        getCommand("score").setExecutor(new Score());
    }

    public ScoreboardRegisterer getR() {
        return r;
    }

    public static FolfScoreboard getPlugin() {
        return plugin;
    }

    public ScoreboardConfig getScoreboardConfig() {
        return scoreboardConfig;
    }
}
