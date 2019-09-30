package com.folfstore.folfscoreboard.processors.impl;

import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatColorProcessor extends ScoreboardProcessor {

    @Override
    public void executeProcessor(ScoreboardLinePool l, Player p) {
        l.getRawList().forEach(scoreboardLine -> executeProcessor(scoreboardLine, p));
    }

    @Override
    public void executeProcessor(ScoreboardLine l, Player p) {
        System.out.println(l.getLine());
        l.setLine(ChatColor.translateAlternateColorCodes('&', l.getLine()));
        System.out.println(l.getLine());
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
