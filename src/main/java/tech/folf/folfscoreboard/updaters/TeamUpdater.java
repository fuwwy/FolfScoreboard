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


public class TeamUpdater implements ScoreboardUpdater {
    private HashMap<Player, Integer> highestIndexCache = new HashMap<>();

    public void update(Player player) {
        FolfScoreboard pluginInstance = FolfScoreboard.getPlugin();

        ScoreboardLinePool scoreboardLines = pluginInstance.getScoreboardProvider().getScoreboardFor(player);

        Scoreboard playerScoreboard = player.getScoreboard();
        Objective folfScoreboardObject = playerScoreboard.getObjective("FolfScoreboard");
        String title = pluginInstance.getScoreboardProvider().getScoreboardTitleFor(player);
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
        for (int i = maxIndex + 1; i <= highestIndexCache.getOrDefault(player, 0); i++) {
            playerScoreboard.resetScores(service.get(i) + "" + ChatColor.RESET);
        }
        highestIndexCache.put(player, maxIndex);

        for (ScoreboardLine scoreboardLine : scoreboardLines.toArray()) {
            int index = scoreboardLine.getIndex();

            String teamName = service.get(index) + "" + ChatColor.RESET;

            Team t = playerScoreboard.getTeam(teamName);
            if (t == null) t = playerScoreboard.registerNewTeam(teamName);
            if (!t.hasEntry(teamName)) t.addEntry(teamName);


            if (!t.getPrefix().equals(scoreboardLine.getPrefix())) t.setPrefix(scoreboardLine.getPrefix());
            if (!t.getSuffix().equals(scoreboardLine.getSuffix())) t.setSuffix(scoreboardLine.getSuffix());

            if (!folfScoreboardObject.getScore(teamName).isScoreSet() || folfScoreboardObject.getScore(teamName).getScore() != index)
                folfScoreboardObject.getScore(teamName).setScore(index);
        }

        if (player.getScoreboard() != playerScoreboard) player.setScoreboard(playerScoreboard);
    }
}
