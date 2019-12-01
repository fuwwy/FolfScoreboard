package tech.folf.folfscoreboard.config;

import org.bukkit.configuration.file.FileConfiguration;
import tech.folf.folfscoreboard.FolfScoreboard;
import tech.folf.folfscoreboard.utils.Logger;

import java.io.File;

public class ScoreboardConfig {
    private boolean debug;
    private double updateInterval;
    private String provider;

    public ScoreboardConfig() {
        Logger logger = new Logger("ScoreboardConfig");
        logger.info("Creating configuration...");
        if (!new File(FolfScoreboard.getPlugin().getDataFolder(), "config.yml").exists()) {
            logger.info("Configuration not found, creating a new file...");
            FolfScoreboard.getPlugin().getConfig().options().copyDefaults(true);
            FolfScoreboard.getPlugin().saveDefaultConfig();
            FolfScoreboard.getPlugin().reloadConfig();
        }

        FileConfiguration config = FolfScoreboard.getPlugin().getConfig();
        debug = config.getBoolean("debug", false);
        updateInterval = config.getDouble("updateEveryXSeconds", 1);
        provider = config.getString("provider", "default");
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public double getUpdateInterval() {
        return updateInterval;
    }

    public String getProvider() {
        return provider;
    }
}
