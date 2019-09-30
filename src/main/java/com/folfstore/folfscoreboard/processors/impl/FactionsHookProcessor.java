package com.folfstore.folfscoreboard.processors.impl;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessorRegisterException;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class FactionsHookProcessor extends ScoreboardProcessor {
    private ScoreboardLinePool noFacLines;
    private ScoreboardLinePool facLines;

    @Override
    public void executeProcessor(ScoreboardLinePool scoreboardLinePool, Player p) {
        MPlayer mplayer = MPlayer.get(p);
        ScoreboardLinePool newLinePool = scoreboardLinePool.clone();
        scoreboardLinePool.getRawList().forEach(scoreboardLine -> {
            if (Pattern.compile(getMatcher()).matcher(scoreboardLine.getLine()).matches()) {
                if (mplayer == null || mplayer.hasFaction()) {
                    getFacLines().getRawList().forEach(scoreboardLine1 -> newLinePool.insertBefore(scoreboardLine, scoreboardLine1));
                } else {
                    getNoFacLines().getRawList().forEach(scoreboardLine1 -> newLinePool.insertBefore(scoreboardLine, scoreboardLine1));
                }
                newLinePool.remove(scoreboardLine);
            }
        });
        scoreboardLinePool.getRawList().clear();
        scoreboardLinePool.getRawList().addAll(newLinePool.getRawList());
    }

    @Override
    public String getId() {
        return "FactionsProcessor";
    }

    @Override
    public boolean shouldRegister() {
        if (Bukkit.getPluginManager().getPlugin("Factions") == null) return false;
        try {
            if (FolfScoreboard.getPlugin().getConfig().isConfigurationSection("hooks.FactionsHook")) {
                noFacLines = new ScoreboardLinePool();
                facLines = new ScoreboardLinePool();
                FolfScoreboard.getPlugin().getConfig().getStringList("hooks.FactionsHook.nofaction").forEach(s -> noFacLines.add(new ScoreboardLine(s, noFacLines.size())));
                FolfScoreboard.getPlugin().getConfig().getStringList("hooks.FactionsHook.hasfaction").forEach(s -> facLines.add(new ScoreboardLine(s, facLines.size())));
            }
        } catch (Exception e) {
            new ScoreboardProcessorRegisterException("[FolfScoreboard] Exception thrown on attempting to register processor " + getId(), e).printStackTrace();
            return false;
        }
        return super.shouldRegister();
    }

    @Override
    public String getPlugin() {
        return "Factions";
    }

    @Override
    public String getMatcher() {
        return "#FactionsHook#";
    }

    private ScoreboardLinePool getNoFacLines() {
        return noFacLines;
    }

    private ScoreboardLinePool getFacLines() {
        return facLines;
    }

}
