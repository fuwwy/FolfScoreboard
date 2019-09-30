package com.folfstore.folfscoreboard.processors;

import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import org.bukkit.entity.Player;

public interface IScoreboardProcessor {
    void executeProcessor(ScoreboardLinePool l, Player p);

    void executeProcessor(ScoreboardLine l, Player p);

    String getId();

    boolean shouldRegister();

    boolean isRegistered();

    String getPlugin();

    String getMatcher();
}
