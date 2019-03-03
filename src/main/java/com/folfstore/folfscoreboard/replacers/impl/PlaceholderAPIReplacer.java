package com.folfstore.folfscoreboard.replacers.impl;

import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.replacers.LineReplacer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Class responsible for replacing PAPI placeholders
 */
public class PlaceholderAPIReplacer extends LineReplacer {

    @Override
    public ScoreboardLine replace(ScoreboardLine l, Player p) {
        return l.setLine(PlaceholderAPI.setPlaceholders((OfflinePlayer) p, l.getLine()));
    }

    @Override
    public String getId() {
        return "PlaceholderAPI";
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
