package com.folfstore.folfscoreboard.updaters;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.ScoreboardLinePool;
import com.folfstore.folfscoreboard.hooks.HookManager;
import com.folfstore.folfscoreboard.hooks.ScoreboardHook;
import com.folfstore.folfscoreboard.replacers.LineReplacer;
import com.folfstore.folfscoreboard.replacers.LineReplacerManager;
import com.folfstore.folfscoreboard.ScoreboardLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *  Updater that uses bukkit default teams.
 *
 *  The update method is supposed to take Player as an argument and do only one at a time, needs to be changed.
 *  The line replacing part should be split into a process() method, update should only create the teams and send information.
 */
public class TeamUpdater {

    public void update() {
        FolfScoreboard instance = FolfScoreboard.getPlugin();
        ScoreboardLinePool lines = instance.getScoreboardConfig().getLines();
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreboardLinePool pline = lines.clone();
            for (ScoreboardLine scoreboardLine : pline.toArray()) {
                String line = scoreboardLine.getLine();
                for (ScoreboardHook hook : HookManager.get().getHooks()) {
                    if (Pattern.compile(hook.getMatcher()).matcher(line).matches()) {
                        ScoreboardLinePool pool = hook.process(p);
                        for (ScoreboardLine scoreboardLine1 : pool.toArray()) {
                            pline.insertBefore(scoreboardLine, scoreboardLine1.clone());
                        }
                        pline.remove(scoreboardLine);
                    }
                }
            }
            for (ScoreboardLine scoreboardLine : pline.toArray()) {
                String line = scoreboardLine.getLine();
                for (LineReplacer replacer : LineReplacerManager.get().getHooks()) {
                    if (Pattern.compile(replacer.getMatcher()).matcher(line).matches()) {
                        replacer.replace(scoreboardLine, p);
                    }
                }
            }



            Scoreboard board = p.getScoreboard();
            Objective obj = board.getObjective("FolfScoreboard");
            if (obj == null) {
                final Scoreboard[] boardArray = new Scoreboard[]{p.getScoreboard()};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        boardArray[0] = Bukkit.getScoreboardManager().getNewScoreboard();
                    }
                }.runTask(FolfScoreboard.getPlugin());
                while (boardArray[0] == p.getScoreboard()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj = board.registerNewObjective("FolfScoreboard", "dummy");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.setDisplayName(instance.getScoreboardConfig().getTitle());
            }
            List<ChatColor> colors = Arrays.asList(ChatColor.values());
            for (ScoreboardLine scoreboardLine : pline.toArray()) {
                int index = scoreboardLine.getIndex();
                String teamName = colors.get(index) + "";
                Team t = board.getTeam(teamName);
                if (t == null) t = board.registerNewTeam(teamName);
                t.addEntry(teamName);
                t.setPrefix(scoreboardLine.getPrefix());
                t.setSuffix(scoreboardLine.getSuffix());
                obj.getScore(teamName).setScore(index);
            }
            p.setScoreboard(board);
        }
    }
}
