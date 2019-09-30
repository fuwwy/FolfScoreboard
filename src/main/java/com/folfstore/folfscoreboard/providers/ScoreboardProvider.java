package com.folfstore.folfscoreboard.providers;

import com.folfstore.folfscoreboard.ScoreboardLinePool;
import org.bukkit.entity.Player;

public interface ScoreboardProvider {
    ScoreboardLinePool getScoreboardFor(Player player);
}
