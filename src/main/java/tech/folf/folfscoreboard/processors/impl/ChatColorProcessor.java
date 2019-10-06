package tech.folf.folfscoreboard.processors.impl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.folf.folfscoreboard.ScoreboardLine;
import tech.folf.folfscoreboard.ScoreboardLinePool;
import tech.folf.folfscoreboard.processors.ScoreboardProcessor;

public class ChatColorProcessor extends ScoreboardProcessor {

    @Override
    public void executeProcessor(ScoreboardLinePool l, Player p) {
        l.getRawList().forEach(scoreboardLine -> executeProcessor(scoreboardLine, p));
    }

    @Override
    public void executeProcessor(ScoreboardLine l, Player p) {
        l.setLine(ChatColor.translateAlternateColorCodes('&', l.getLine()));
    }

    @Override
    public String getId() {
        return "ChatColorProcessor";
    }

    @Override
    public String getMatcher() {
        return "&(.*)";
    }

}
