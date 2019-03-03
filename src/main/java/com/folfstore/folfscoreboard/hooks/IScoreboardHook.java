package com.folfstore.folfscoreboard.hooks;

import com.folfstore.folfscoreboard.ScoreboardLinePool;
import org.bukkit.entity.Player;

public interface IScoreboardHook {
    boolean register();

    boolean isRegistered();

    ScoreboardLinePool process(Player p);

    String getPlugin();

    String getId();

    String getMatcher();
}
