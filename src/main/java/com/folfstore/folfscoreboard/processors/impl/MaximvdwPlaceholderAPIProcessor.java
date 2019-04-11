package com.folfstore.folfscoreboard.processors.impl;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import org.bukkit.entity.Player;

/**
 * Class responsible for replacing PAPI placeholders
 */
public class MaximvdwPlaceholderAPIProcessor extends ScoreboardProcessor {

    @Override
    public void executeProcessor(ScoreboardLinePool l, Player p) {
        l.getRawList().forEach(scoreboardLine -> scoreboardLine.setLine(PlaceholderAPI.replacePlaceholders(p, scoreboardLine.getLine())));
    }

    @Override
    public String getId() {
        return "MvdwPlaceholderAPIProcessor";
    }

    @Override
    public String getPlugin() {
        return "MVdWPlaceholderAPI";
    }

    @Override
    public String getMatcher() {
        return "{(.*)}";
    }
}
