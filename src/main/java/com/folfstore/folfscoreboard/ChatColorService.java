package com.folfstore.folfscoreboard;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class ChatColorService {
    private List<ChatColor> colors = Arrays.asList(ChatColor.values());

    public String get(int index) {
        String color = "";
        if (index > 15) {
            if (index < 32) {
                color += ChatColor.BOLD;
            } else if (index < 47) {
                color += ChatColor.ITALIC;
            } else if (index < 63) {
                color += ChatColor.STRIKETHROUGH;
            } else if (index < 79) {
                color += ChatColor.UNDERLINE;
            } else if (index < 95) {
                color += ChatColor.MAGIC;
            }
        }
        color += colors.get(index);
        return color;
    }
}
