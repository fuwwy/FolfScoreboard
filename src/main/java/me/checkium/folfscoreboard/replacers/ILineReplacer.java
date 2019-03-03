package me.checkium.folfscoreboard.replacers;

import me.checkium.folfscoreboard.ScoreboardLine;
import org.bukkit.entity.Player;

public interface ILineReplacer {
    ScoreboardLine replace(ScoreboardLine l, Player p);

    String getId();

    boolean register();

    boolean isRegistered();

    String getPlugin();

    String getMatcher();
}
