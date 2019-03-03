package me.checkium.folfscoreboard.replacers.impl;

import me.checkium.folfscoreboard.ScoreboardLine;
import me.checkium.folfscoreboard.replacers.LineReplacer;
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
