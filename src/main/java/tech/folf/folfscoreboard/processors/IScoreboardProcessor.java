package tech.folf.folfscoreboard.processors;

import org.bukkit.entity.Player;
import tech.folf.folfscoreboard.ScoreboardLine;
import tech.folf.folfscoreboard.ScoreboardLinePool;

public interface IScoreboardProcessor {
    void executeProcessor(ScoreboardLinePool l, Player p);

    void executeProcessor(ScoreboardLine l, Player p);

    String getId();

    boolean shouldRegister();

    boolean isRegistered();

    String getPlugin();

    String getMatcher();
}
