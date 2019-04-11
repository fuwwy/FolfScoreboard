package com.folfstore.folfscoreboard.updaters;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLine;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessor;
import com.folfstore.folfscoreboard.processors.ScoreboardProcessorManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Updater that uses bukkit default teams.
 * <p>
 * The update method is supposed to take Player as an argument and do only one at a time, needs to be changed.
 * The line replacing part should be split into a executeProcessor() method, update should only create the teams and send information.
 */
public class TeamUpdater {

    public void update(Player p) {
        FolfScoreboard pluginInstance = FolfScoreboard.getPlugin();
        ScoreboardLinePool scoreboardConfiguredLines = pluginInstance.getScoreboardConfig().getLines();
        ScoreboardLinePool scoreboardConfiguredLinesClone = scoreboardConfiguredLines.clone();
        for (ScoreboardProcessor scoreboardProcessor : ScoreboardProcessorManager.get().getProcessors()) {
            scoreboardProcessor.executeProcessor(scoreboardConfiguredLinesClone, p);
        }
        Scoreboard playerScoreboard = p.getScoreboard();
        Objective folfScoreboardObject = playerScoreboard.getObjective("FolfScoreboard");
        if (folfScoreboardObject == null) {
            Future<Scoreboard> scoreboardFuture = Bukkit.getScheduler().callSyncMethod(FolfScoreboard.getPlugin(), () -> Bukkit.getScoreboardManager().getNewScoreboard());
            try {
                playerScoreboard = scoreboardFuture.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                throw new RuntimeException("Failed to update scoreboard", ex);
            }
            folfScoreboardObject = playerScoreboard.registerNewObjective("FolfScoreboard", "dummy");
            folfScoreboardObject.setDisplaySlot(DisplaySlot.SIDEBAR);
            folfScoreboardObject.setDisplayName(pluginInstance.getScoreboardConfig().getTitle());
        }

        List<ChatColor> colors = Arrays.asList(ChatColor.values());
        for (ScoreboardLine scoreboardLine : scoreboardConfiguredLinesClone.toArray()) {
            int index = scoreboardLine.getIndex();
            String teamName = colors.get(index) + "";
            Team t = playerScoreboard.getTeam(teamName);
            if (t == null) t = playerScoreboard.registerNewTeam(teamName);
            t.addEntry(teamName);
            t.setPrefix(scoreboardLine.getPrefix());
            t.setSuffix(scoreboardLine.getSuffix());
            folfScoreboardObject.getScore(teamName).setScore(index);
        }
        p.setScoreboard(playerScoreboard);

    }
}
