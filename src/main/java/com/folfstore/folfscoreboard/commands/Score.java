package com.folfstore.folfscoreboard.commands;

import com.folfstore.folfscoreboard.FolfScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Score implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length > 0) {
            if (strings[0].equalsIgnoreCase("toggle")) {
                if (FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().containsKey(commandSender)) {
                    FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().remove(commandSender);
                    ((Player) commandSender).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                } else {
                    FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().put((Player) commandSender, FolfScoreboard.getPlugin().getScoreboardListener().getScoreboardFor((Player) commandSender));
                }
            } else if (strings[0].equalsIgnoreCase("off")) {
                FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().remove(commandSender);
                ((Player) commandSender).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            } else if (strings[0].equalsIgnoreCase("on")) {
                if (!FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().containsKey(commandSender))
                    FolfScoreboard.getPlugin().getScoreboardListener().getOnlinePlayersScoreboard().put((Player) commandSender, FolfScoreboard.getPlugin().getScoreboardListener().getScoreboardFor((Player) commandSender));
            }
        }
        return true;
    }

}
