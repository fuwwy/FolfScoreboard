package com.folfstore.folfscoreboard.listeners;

import com.folfstore.folfscoreboard.FolfScoreboard;
import com.folfstore.folfscoreboard.updaters.TeamUpdater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class ScoreboardEventListener implements Listener {

    private HashMap<Player, Scoreboard> onlinePlayersScoreboard = new HashMap<>();

    public ScoreboardEventListener() {
        TeamUpdater updater = new TeamUpdater();
        new BukkitRunnable() {
            @Override
            public void run() {
                new HashMap<>(onlinePlayersScoreboard).forEach((player, scoreboard) -> updater.update(player));
            }
        }.runTaskTimerAsynchronously(FolfScoreboard.getPlugin(), 0L, (long) (FolfScoreboard.getPlugin().getScoreboardConfig().getUpdateSec() * 20L));
    }

    public HashMap<Player, Scoreboard> getOnlinePlayersScoreboard() {
        return onlinePlayersScoreboard;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        initPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        onlinePlayersScoreboard.remove(e.getPlayer());
    }

    public void initPlayer(Player player) {
        onlinePlayersScoreboard.put(player, getScoreboardFor(player));
    }

    public Scoreboard getScoreboardFor(Player p) {
        return onlinePlayersScoreboard.containsKey(p) ? onlinePlayersScoreboard.get(p) : Bukkit.getScoreboardManager().getNewScoreboard();
    }

}
