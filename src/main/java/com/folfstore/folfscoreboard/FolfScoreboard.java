package com.folfstore.folfscoreboard;

import com.folfstore.folfscoreboard.commands.Score;
import com.folfstore.folfscoreboard.config.ScoreboardConfig;
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
    private ScoreboardEventListener r;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Ativando FolfScoreboard v" + getDescription().getVersion());
        scoreboardConfig = new ScoreboardConfig();
        new FactionsHookProcessor();
        new PlaceholderAPIProcessor();
        new ChatColorProcessor();
        logger.info("Iniciando o registrador de scoreboards...");
        r = new ScoreboardEventListener();
        Bukkit.getPluginManager().registerEvents(r, this);
        getCommand("score").setExecutor(new Score());
    }

    public ScoreboardEventListener getR() {
        return r;
    }

    public static FolfScoreboard getPlugin() {
        return plugin;
    }

    public ScoreboardConfig getScoreboardConfig() {
        return scoreboardConfig;
    }
}
