package me.checkium.folfscoreboard.replacers;

import me.checkium.folfscoreboard.hooks.ScoreboardHook;

import java.util.ArrayList;
import java.util.List;

public class LineReplacerManager {
    private static LineReplacerManager instance;
    private List<LineReplacer> replacers = new ArrayList<>();

    private LineReplacerManager() {}

    public static LineReplacerManager get() {
        if (instance == null) instance = new LineReplacerManager();
        return instance;
    }

    public List<LineReplacer> getHooks() {
        return replacers;
    }
}
