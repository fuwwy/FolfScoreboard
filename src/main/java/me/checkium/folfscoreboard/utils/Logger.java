package me.checkium.folfscoreboard.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * Do I really need to document this out?
 */
public class Logger {
    private String id;
    public Logger(String id) { this.id = id; }
    public Logger() {}

    public void info(String message) {
       send(message, Level.INFO);
    }

    private String getPrefix() {
        return "[FolfScoreboard" + (id == null ? "" : "-" + id) + "] ";
    }

    private void send(String message, Level level) {
        Bukkit.getConsoleSender().sendMessage(level.getColor() + getPrefix() + message);
    }

    public void debug(String message) {
        send(message, Level.DEBUG);
    }

    public enum Level {
        INFO(ChatColor.GREEN), WARN(ChatColor.GOLD), ERROR(ChatColor.RED), DEBUG(ChatColor.LIGHT_PURPLE);

        ChatColor color;
        Level(ChatColor color){
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }
    }
}
