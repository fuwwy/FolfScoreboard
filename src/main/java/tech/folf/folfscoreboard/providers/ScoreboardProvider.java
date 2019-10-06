package tech.folf.folfscoreboard.providers;

import org.bukkit.entity.Player;
import tech.folf.folfscoreboard.ScoreboardLinePool;

public interface ScoreboardProvider {
    ScoreboardLinePool getScoreboardFor(Player player);

    String getScoreboardTitleFor(Player player);
}
