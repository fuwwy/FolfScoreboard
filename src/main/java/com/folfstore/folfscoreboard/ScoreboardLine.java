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
        suffix = it.hasNext() ? it.next() : null;
        return this;
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

    public ScoreboardLine clone() {
        return new ScoreboardLine(line);
    }
}
