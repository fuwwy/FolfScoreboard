package com.folfstore.folfscoreboard.hooks;

import java.util.ArrayList;
import java.util.List;

public class HookManager {
    private static HookManager instance;
    private List<ScoreboardHook> hooks = new ArrayList<>();

    private HookManager() {}

    public static HookManager get() {
        if (instance == null) instance = new HookManager();
        return instance;
    }

    public List<ScoreboardHook> getHooks() {
        return hooks;
    }
}
