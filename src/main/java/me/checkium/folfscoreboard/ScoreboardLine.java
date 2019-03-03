package me.checkium.folfscoreboard;

import com.google.common.base.Splitter;
import org.bukkit.ChatColor;

import java.util.Iterator;


/**
 *  Scoreboard line class, splits text between prefix and suffix
 */
public class ScoreboardLine {
    private String line;
    private String prefix;
    private String suffix;
    ScoreboardLine nextLine;
    ScoreboardLine previousLine;
    int index;

    public ScoreboardLine(String line) {
        setLine(line);
    }

    public String getLine() {
        return line;
    }

    public ScoreboardLine setLine(String line) {
        if (line.length() == 0 || line.equals(" ")) {
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

    public ScoreboardLine clone() {
        return new ScoreboardLine(line);
    }
}
