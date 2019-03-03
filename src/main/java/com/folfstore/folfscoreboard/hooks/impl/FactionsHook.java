package com.folfstore.folfscoreboard.hooks.impl;

import com.folfstore.folfscoreboard.hooks.ScoreboardHook;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
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
