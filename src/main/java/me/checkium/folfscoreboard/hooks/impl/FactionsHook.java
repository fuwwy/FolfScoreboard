package me.checkium.folfscoreboard.hooks.impl;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import me.checkium.folfscoreboard.FolfScoreboard;
import me.checkium.folfscoreboard.ScoreboardLine;
import me.checkium.folfscoreboard.ScoreboardLinePool;
import me.checkium.folfscoreboard.hooks.ScoreboardHook;
import org.bukkit.entity.Player;


/**
 * Hook for the factions plugin
 */
public class FactionsHook extends ScoreboardHook {
    private ScoreboardLinePool noFacLines;
    private ScoreboardLinePool facLines;

    @Override
    public boolean register() {
        try {
            if (FolfScoreboard.getPlugin().getConfig().isConfigurationSection("hooks.FactionsHook")) {
                noFacLines = new ScoreboardLinePool();
                facLines = new ScoreboardLinePool();
                FolfScoreboard.getPlugin().getConfig().getStringList("hooks.FactionsHook.nofaction").stream().forEach(s -> noFacLines.add(new ScoreboardLine(s)));
                FolfScoreboard.getPlugin().getConfig().getStringList("hooks.FactionsHook.hasfaction").stream().forEach(s -> facLines.add(new ScoreboardLine(s)));
            }
        } catch (Exception e) {
            new RuntimeException("Exception thrown on attempting to register hook " + getId(), e).printStackTrace();
            return false;
        }
        return super.register();
    }

    @Override
    public ScoreboardLinePool process(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        return (mplayer == null || mplayer.getFaction() == null || mplayer.getFaction().equals(FactionColl.get().getNone())) ? getNoFacLines() : getFacLines();
    }

    private ScoreboardLinePool getNoFacLines() {
        return noFacLines;
    }

    private ScoreboardLinePool getFacLines() {
        return facLines;
    }

    @Override
    public String getMatcher() {
        return "#.*#";
    }
}
