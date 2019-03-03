package me.checkium.folfscoreboard.commands;

import me.checkium.folfscoreboard.FolfScoreboard;
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
                if (FolfScoreboard.getPlugin().getR().getPlayers().containsKey(commandSender)) {
                    FolfScoreboard.getPlugin().getR().getPlayers().remove(commandSender);
                    ((Player) commandSender).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                } else {
                    FolfScoreboard.getPlugin().getR().getPlayers().put((Player) commandSender, FolfScoreboard.getPlugin().getR().getScoreboardFor((Player) commandSender));
                }
            } else if (strings[0].equalsIgnoreCase("off")) {
                FolfScoreboard.getPlugin().getR().getPlayers().remove(commandSender);
                ((Player) commandSender).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            } else if (strings[0].equalsIgnoreCase("on")) {
                if (!FolfScoreboard.getPlugin().getR().getPlayers().containsKey(commandSender)) FolfScoreboard.getPlugin().getR().getPlayers().put((Player) commandSender, FolfScoreboard.getPlugin().getR().getScoreboardFor((Player) commandSender));
            }
        }
        return true;
    }
}
