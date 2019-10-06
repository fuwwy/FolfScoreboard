package tech.folf.folfscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tech.folf.folfscoreboard.commands.Score;
import tech.folf.folfscoreboard.config.ScoreboardConfig;
import tech.folf.folfscoreboard.listeners.ScoreboardEventListener;
import tech.folf.folfscoreboard.processors.impl.ChatColorProcessor;
import tech.folf.folfscoreboard.processors.impl.FactionsHookProcessor;
import tech.folf.folfscoreboard.processors.impl.PlaceholderAPIProcessor;
import tech.folf.folfscoreboard.providers.DefaultScoreboardProvider;
import tech.folf.folfscoreboard.providers.RegionalScoreboardProvider;
import tech.folf.folfscoreboard.providers.ScoreboardProvider;
import tech.folf.folfscoreboard.utils.Logger;

/**
 * Old main class
 * Needs heavy updating to new system, for ex. hooks vs replacers
 */
public class FolfScoreboard extends JavaPlugin {

    private Logger logger = new Logger();
    private static FolfScoreboard plugin;
    private ScoreboardConfig scoreboardConfig;
    private ScoreboardEventListener scoreboardListener;
    private ScoreboardProvider scoreboardProvider;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Ativando FolfScoreboard v" + getDescription().getVersion());
        initConfig();
        registerProcessors();

        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            logger.info("WorldGuard encontrado, ativando scoreboards regionais...");
            setScoreboardProvider(new RegionalScoreboardProvider(new DefaultScoreboardProvider()));
        } else {
            setScoreboardProvider(new DefaultScoreboardProvider());
        }
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

    public ScoreboardProvider getScoreboardProvider() {
        return scoreboardProvider;
    }

    public void setScoreboardProvider(ScoreboardProvider scoreboardProvider) {
        this.scoreboardProvider = scoreboardProvider;
    }
}
