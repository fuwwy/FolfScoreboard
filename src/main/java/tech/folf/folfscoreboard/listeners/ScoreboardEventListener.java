package tech.folf.folfscoreboard.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import tech.folf.folfscoreboard.FolfScoreboard;
import tech.folf.folfscoreboard.updaters.ScoreboardUpdater;

import java.util.HashMap;

public class ScoreboardEventListener implements Listener {

    private HashMap<Player, Scoreboard> onlinePlayersScoreboard = new HashMap<>();

    public ScoreboardEventListener(ScoreboardUpdater updater) {
        long updateIntervalInTicks = (long) (FolfScoreboard.getPlugin().getScoreboardConfig().getUpdateInterval() * 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                new HashMap<>(onlinePlayersScoreboard).forEach((player, scoreboard) -> updater.update(player));
            }
        }.runTaskTimerAsynchronously(FolfScoreboard.getPlugin(), 0L, updateIntervalInTicks);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        onlinePlayersScoreboard.remove(event.getPlayer());
    }

    public void registerPlayer(Player player) {
        onlinePlayersScoreboard.put(player, getScoreboardFor(player));
    }

    public Scoreboard getScoreboardFor(Player player) {
        return onlinePlayersScoreboard.getOrDefault(player, Bukkit.getScoreboardManager().getNewScoreboard());
    }

}
