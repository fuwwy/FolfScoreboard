package com.folfstore.folfscoreboard;

import com.google.common.base.Splitter;
import org.bukkit.ChatColor;

import java.util.Iterator;

public class ScoreboardLine {
    private String line;
    private String prefix;
    private String suffix;
    private int index;

    public ScoreboardLine(String line) {
        setLine(line);
    }

    public String getLine() {
        return line;
    }

    public ScoreboardLine setLine(String line) {
        if (0 == line.length() || line.equals(" ")) {
            line = "" + ChatColor.values()[(int) (Math.random() * ChatColor.values().length)];
        }
        this.line = line;
        Iterator<String> it = Splitter.fixedLength(14).split(line).iterator();
        prefix = it.next();
        suffix = it.hasNext() ? makeSuffix(it.next()) : null;
        return this;
    }

    private String makeSuffix(String suffix) {
        StringBuilder colors = new StringBuilder();
        if (prefix.contains(ChatColor.COLOR_CHAR + "")) {
            char[] charArray = prefix.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c == ChatColor.COLOR_CHAR) {
                    colors.append(c).append(prefix.charAt(i + 1));
                }
            }
        }
        return colors + suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix == null ? "" : suffix;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ScoreboardLine clone() {
        return new ScoreboardLine(line);
    }
}
