package com.folfstore.folfscoreboard.providers;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessorManager;
import org.bukkit.entity.Player;

public class DefaultScoreboardProvider implements ScoreboardProvider {
    @Override
    public ScoreboardLinePool getScoreboardFor(Player player) {
        ScoreboardLinePool configuredLines = FolfScoreboard.getPlugin().getScoreboardConfig().getLines().clone();

        for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
            scoreboardProcessor.executeProcessor(configuredLines, player);
        }

        return configuredLines;
    }
}
