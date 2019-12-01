package tech.folf.folfscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tech.folf.folfscoreboard.config.ScoreboardConfig;
import tech.folf.folfscoreboard.listeners.ScoreboardEventListener;
import tech.folf.folfscoreboard.processors.impl.ChatColorProcessor;
import tech.folf.folfscoreboard.processors.impl.FactionsHookProcessor;
import tech.folf.folfscoreboard.processors.impl.PlaceholderAPIProcessor;
import tech.folf.folfscoreboard.providers.DefaultScoreboardProvider;
import tech.folf.folfscoreboard.providers.RegionalScoreboardProvider;
import tech.folf.folfscoreboard.providers.ScoreboardProvider;
import tech.folf.folfscoreboard.updaters.ScoreboardUpdater;
import tech.folf.folfscoreboard.updaters.TeamUpdater;
import tech.folf.folfscoreboard.utils.Logger;

public class FolfScoreboard extends JavaPlugin {

    private Logger logger = new Logger();
    private static FolfScoreboard plugin;
    private ScoreboardConfig scoreboardConfig;
    private ScoreboardEventListener scoreboardListener;

    private ScoreboardProvider scoreboardProvider;
    private ScoreboardUpdater scoreboardUpdater;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Enabling FolfScoreboard v" + getDescription().getVersion());

        initialize();
        // TODO getCommand("score").setExecutor(new Score());
    }

    private void initialize() {
        initializeConfig();
        registerScoreboardProcessors();
        selectScoreboardProvider();
        selectScoreboardUpdater();
        registerUpdaterEvents();
        addExistingPlayersOnReload();
    }

    private void selectScoreboardProvider() {
        switch (scoreboardConfig.getProvider().toLowerCase()) {
            case "regional":
                this.scoreboardProvider = new RegionalScoreboardProvider(new DefaultScoreboardProvider());
                break;
            default:
                this.scoreboardProvider = new DefaultScoreboardProvider();
        }
    }

    private void addExistingPlayersOnReload() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                scoreboardListener.registerPlayer(player);
            });
        }
    }

    private void selectScoreboardUpdater() {
        // Currently there's only one updater so just set it
        this.scoreboardUpdater = new TeamUpdater();
    }

    private void initializeConfig() {
        scoreboardConfig = new ScoreboardConfig();
    }

    private void registerScoreboardProcessors() {
        new FactionsHookProcessor();
        new PlaceholderAPIProcessor();
        new ChatColorProcessor();
    }

    private void registerUpdaterEvents() {
        logger.info("Starting the scoreboard updater...");
        scoreboardListener = new ScoreboardEventListener(scoreboardUpdater);
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

}
