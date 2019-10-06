package tech.folf.folfscoreboard.providers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tech.folf.folfscoreboard.FolfScoreboard;
import tech.folf.folfscoreboard.ScoreboardLine;
import tech.folf.folfscoreboard.ScoreboardLinePool;
import tech.folf.folfscoreboard.processors.ScoreboardProcessor;
import tech.folf.folfscoreboard.processors.ScoreboardProcessorManager;
import tech.folf.folfscoreboard.utils.Logger;

import java.io.File;
import java.io.IOException;

public class DefaultScoreboardProvider implements ScoreboardProvider {
    ScoreboardLine title;
    ScoreboardLinePool lines;

    public DefaultScoreboardProvider() {
        Logger logger = new Logger("DefaultScoreboardProvider");
        logger.info("Carregando configuração...");

        File configFile = new File(FolfScoreboard.getPlugin().getDataFolder(), "scoreboard.yml");
        if (!configFile.exists()) {
            logger.info("Configuração não encontrada, criando novo arquivo...");
            FolfScoreboard.getPlugin().saveResource("scoreboard.yml", false);
        }
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        title = new ScoreboardLine(config.getString("title"), 0);
        lines = new ScoreboardLinePool();
        config.getStringList("lines").stream().map(line -> new ScoreboardLine(line, lines.size())).forEach(scoreboardLine -> lines.add(scoreboardLine));
    }

    @Override
    public ScoreboardLinePool getScoreboardFor(Player player) {
        ScoreboardLinePool configuredLines = lines.clone();

        for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
            scoreboardProcessor.executeProcessor(configuredLines, player);
        }

        return configuredLines;
    }

    @Override
    public String getScoreboardTitleFor(Player player) {
        ScoreboardLine line = title.clone();
        for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
            scoreboardProcessor.executeProcessor(line, player);
        }

        return line.getLine();
    }
}
