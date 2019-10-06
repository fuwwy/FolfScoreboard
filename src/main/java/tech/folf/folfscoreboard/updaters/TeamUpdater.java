package tech.folf.folfscoreboard.updaters;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tech.folf.folfscoreboard.FolfScoreboard;
import tech.folf.folfscoreboard.ScoreboardLine;
import tech.folf.folfscoreboard.ScoreboardLinePool;
import tech.folf.folfscoreboard.services.ChatColorService;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TeamUpdater {
    private HashMap<Player, Integer> highestIndexCache = new HashMap<>();
    public void update(Player p) {
        FolfScoreboard pluginInstance = FolfScoreboard.getPlugin();

        ScoreboardLinePool scoreboardLines = pluginInstance.getScoreboardProvider().getScoreboardFor(p);

        Scoreboard playerScoreboard = p.getScoreboard();
        Objective folfScoreboardObject = playerScoreboard.getObjective("FolfScoreboard");
        String title = pluginInstance.getScoreboardProvider().getScoreboardTitleFor(p);
        if (folfScoreboardObject == null) {
            Future<Scoreboard> scoreboardFuture = Bukkit.getScheduler().callSyncMethod(FolfScoreboard.getPlugin(), () -> Bukkit.getScoreboardManager().getNewScoreboard());
            try {
                playerScoreboard = scoreboardFuture.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                throw new RuntimeException("Failed to update scoreboard", ex);
            }
            folfScoreboardObject = playerScoreboard.registerNewObjective("FolfScoreboard", "dummy");
            folfScoreboardObject.setDisplaySlot(DisplaySlot.SIDEBAR);
            folfScoreboardObject.setDisplayName(title);
        }

        if (folfScoreboardObject.getDisplayName() != title) folfScoreboardObject.setDisplayName(title);

        ChatColorService service = new ChatColorService();

        int maxIndex = scoreboardLines.get(0).getIndex();
        for (int i = maxIndex + 1; i <= highestIndexCache.getOrDefault(p, 0); i++) {
            playerScoreboard.resetScores(service.get(i) + "" + ChatColor.RESET);
        }
        highestIndexCache.put(p, maxIndex);

        for (ScoreboardLine scoreboardLine : scoreboardLines.toArray()) {
            int index = scoreboardLine.getIndex();

            String teamName = service.get(index) + "" + ChatColor.RESET;

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
