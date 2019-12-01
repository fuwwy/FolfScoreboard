package tech.folf.folfscoreboard.processors.impl;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import tech.folf.folfscoreboard.ScoreboardLine;
import tech.folf.folfscoreboard.ScoreboardLinePool;
import tech.folf.folfscoreboard.processors.ScoreboardProcessor;

/**
 * Class responsible for replacing PAPI placeholders
 */
public class PlaceholderAPIProcessor extends ScoreboardProcessor {

    @Override
    public void executeProcessor(ScoreboardLinePool l, Player p) {
        l.getRawList().forEach(scoreboardLine -> executeProcessor(scoreboardLine, p));
    }

    @Override
    public void executeProcessor(ScoreboardLine l, Player p) {
        l.setLine(PlaceholderAPI.setPlaceholders((OfflinePlayer) p, l.getLine()));
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
