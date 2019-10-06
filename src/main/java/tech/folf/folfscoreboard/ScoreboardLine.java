package tech.folf.folfscoreboard;

import com.google.common.base.Splitter;
import org.bukkit.ChatColor;
import tech.folf.folfscoreboard.services.ChatColorService;

import java.util.Iterator;

public class ScoreboardLine {
    private String line;
    private String prefix;
    private String suffix;
    private int index;

    public ScoreboardLine(String line, int index) {
        setIndex(index);
        setLine(line);
    }

    public String getLine() {
        return line;
    }

    public ScoreboardLine setLine(String line) {
        if (0 == line.length() || line.equals(" ")) {
            line = "" + ChatColorService.get(index);
        }
        this.line = line;
        Iterator<String> it = Splitter.fixedLength(16).split(line).iterator();
        prefix = it.next();
        suffix = null;
        if (it.hasNext()) {
            suffix = makeSuffix(it.next());
            if (suffix.length() > 16) suffix = suffix.substring(0, 16);
        }
        return this;
    }

    private String makeSuffix(String suffix) {
        StringBuilder colors = new StringBuilder();
        if (prefix.contains(ChatColor.COLOR_CHAR + "")) {
            char[] charArray = prefix.toCharArray().clone();
            reverseArray(charArray);
            for (int i = 0; i < charArray.length; i++) {
                if (colors.length() >= 4) break;
                char c = charArray[i];
                if (c == ChatColor.COLOR_CHAR) {
                    char cChar = charArray[i - 1];
                    if (colors.length() != 0 && (cChar != ChatColor.BOLD.getChar() && cChar != ChatColor.ITALIC.getChar() && cChar != ChatColor.STRIKETHROUGH.getChar()))
                        continue;
                    colors.append(c).append(cChar);
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
        return new ScoreboardLine(line, index);
    }

    private void reverseArray(char[] array) {
        int length = array.length;
        char element;
        for (int i = 0; i < length / 2; i++) {
            element = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = element;
        }
    }

}
