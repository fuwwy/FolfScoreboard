package com.folfstore.folfscoreboard.processors.impl;

import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Class responsible for replacing PAPI placeholders
 */
public class PlaceholderAPIProcessor extends ScoreboardProcessor {

    @Override
    public void executeProcessor(ScoreboardLinePool l, Player p) {
        l.getRawList().forEach(scoreboardLine -> PlaceholderAPI.setPlaceholders((OfflinePlayer) p, scoreboardLine.getLine()));
    }

    @Override
    public String getId() {
        return "PlaceholderAPIProcessor";
    }

    @Override
    public String getPlugin() {
        return "PlaceholderAPI";
    }

    @Override
    public String getMatcher() {
        return "%(.*)%";
    }
}
