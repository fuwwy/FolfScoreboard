package com.folfstore.folfscoreboard;

import com.folfstore.folfscoreboard.commands.Score;
import com.folfstore.folfscoreboard.config.ScoreboardConfig;
import com.folfstore.folfscoreboard.listeners.ScoreboardEventListener;
import com.folfstore.folfscoreboard.processors.impl.ChatColorProcessor;
import com.folfstore.folfscoreboard.processors.impl.FactionsHookProcessor;
import com.folfstore.folfscoreboard.processors.impl.PlaceholderAPIProcessor;
import com.folfstore.folfscoreboard.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Old main class
 * Needs heavy updating to new system, for ex. hooks vs replacers
 */
public class FolfScoreboard extends JavaPlugin {

    private Logger logger = new Logger();
    private static FolfScoreboard plugin;
    private ScoreboardConfig scoreboardConfig;
    private ScoreboardEventListener scoreboardListener;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Ativando FolfScoreboard v" + getDescription().getVersion());
        initConfig();
        registerProcessors();
        initRegisterer();
        getCommand("score").setExecutor(new Score());
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                scoreboardListener.initPlayer(player);
            });
        }
    }

    private void initConfig() {
        scoreboardConfig = new ScoreboardConfig();
    }

    private void registerProcessors() {
        new FactionsHookProcessor();
        new PlaceholderAPIProcessor();
        new ChatColorProcessor();
    }

    private void initRegisterer() {
        logger.info("Iniciando o registrador de scoreboards...");
        scoreboardListener = new ScoreboardEventListener();
        Bukkit.getPluginManager().registerEvents(scoreboardListener, this);
    }

    public ScoreboardEventListener getScoreboardListener() {
        return scoreboardListener;
    }

    public static FolfScoreboard getPlugin() {
        return plugin;
    }

    public ScoreboardConfig getScoreboardConfig() {
        return scoreboardConfig;
    }
}
