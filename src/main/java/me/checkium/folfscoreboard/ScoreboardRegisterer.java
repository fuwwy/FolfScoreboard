package me.checkium.folfscoreboard;

import me.checkium.folfscoreboard.updaters.TeamUpdater;
import me.checkium.folfscoreboard.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 *
 * Old class responsible for player registration, needs to be updated.
 * Currently bridges TeamUpdater directly.
 *
 * @deprecated
 */
public class ScoreboardRegisterer implements Listener {

    long last = 0;
    int amountUpdated = 0;
    Logger logger = new Logger("Updater");
    private HashMap<Player, Scoreboard> players = new HashMap<>();
    private HashMap<Player, Integer> lastLines = new HashMap<>();


    public ScoreboardRegisterer() {
        TeamUpdater updater = new TeamUpdater();
        new BukkitRunnable() {
            @Override
            public void run() {
                updater.update();
            }
        }.runTaskTimerAsynchronously(FolfScoreboard.getPlugin(), 0L, (long) (FolfScoreboard.getPlugin().getScoreboardConfig().getUpdateSec() * 20L));
    }

    public HashMap<Player, Scoreboard> getPlayers() {
        return players;
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        lastLines.put(e.getPlayer(), 0);
        players.put(e.getPlayer(), getScoreboardFor(e.getPlayer()));
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        players.remove(e.getPlayer());
        lastLines.remove(e.getPlayer());
    }

    public Scoreboard getScoreboardFor(Player p) {
        return players.containsKey(p) ? players.get(p) : Bukkit.getScoreboardManager().getNewScoreboard();
    }

 /*   public void updateScoreboardFor(Player p) {
        if (FolfScoreboard.getPlugin().getScoreboardConfig().isDebug()) {
            amountUpdated++;
            if ((System.currentTimeMillis() - 10000) > last) {
                last = System.currentTimeMillis();
                logger.debug("Updated " + amountUpdated + " scoreboards over the last 10 seconds.");
                amountUpdated = 0;
            }
        }
        List<String> lines = FolfScoreboard.getPlugin().getScoreboardConfig().getLinesFor(p);
        Scoreboard board = getScoreboardFor(p);
        Objective obj = board.getObjective("FolfScoreboard");
        int amount = lines.size();
        if (obj == null) {
            obj = board.registerNewObjective("FolfScoreboard", "dummy");
        }
        if (amount != lastLines.get(p)) {
            obj.unregister();
            obj = board.registerNewObjective("FolfScoreboard", "dummy");
        }
        lastLines.put(p, amount);
        if (!obj.getDisplayName().equals(scoreTitle))
            obj.setDisplayName(scoreTitle);
        if (obj.getDisplaySlot() != DisplaySlot.SIDEBAR) obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        List<ChatColor> colors = new ArrayList<>(Arrays.asList(ChatColor.values()));
        for (String line : lines) {
            ChatColor color = colors.get(amount - 1);
            Team t = board.getTeam("" + color) != null ? board.getTeam("" + color) : board.registerNewTeam("" + color);
            if (line.length() > 16) {
                Iterator<String> it = Splitter.fixedLength(14).split(line).iterator();
                String prefix = it.next();
                String suffix = it.next();
                if (prefix.contains(String.valueOf(ChatColor.COLOR_CHAR))) {
                    String colorThing = prefix.substring(prefix.lastIndexOf(ChatColor.COLOR_CHAR), prefix.lastIndexOf(ChatColor.COLOR_CHAR) + 2);
                    if (colorThing.endsWith("l")) {
                        String rest = prefix.substring(0, prefix.lastIndexOf(ChatColor.COLOR_CHAR));
                        if (rest.lastIndexOf(ChatColor.COLOR_CHAR) >= 0) {
                            String secondColorThing = rest.substring(rest.lastIndexOf(ChatColor.COLOR_CHAR), rest.lastIndexOf(ChatColor.COLOR_CHAR) + 2);
                            colorThing = secondColorThing + colorThing;
                        }
                    }
                    suffix = colorThing + suffix;
                } else {
                    suffix = ChatColor.RESET + suffix;
                }
                if (!t.getPrefix().equals(prefix)) t.setPrefix(prefix);
                if (!t.getSuffix().equals(suffix)) t.setSuffix(suffix);
            } else {
                if (!t.getSuffix().equals("")) t.setSuffix("");
                if (!t.getPrefix().equals(line)) t.setPrefix(line);
            }
            if (!t.hasEntry("" + color)) t.addEntry("" + color);
            if (obj.getScore("" + color).getScore() != amount)
                obj.getScore("" + color).setScore(amount);
            amount--;
        }
        if (!p.getScoreboard().equals(board)) {
            p.setScoreboard(board);
        }
    }*/
}
