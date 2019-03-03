package me.checkium.folfscoreboard.hooks;

import org.bukkit.Bukkit;

public abstract class ScoreboardHook implements IScoreboardHook {

    public ScoreboardHook() {
        if (Bukkit.getPluginManager().isPluginEnabled(getPlugin())) {
           if (register()) {
               HookManager.get().getHooks().add(this);
           }
        }
    }

    @Override
    public boolean register() {
        return true;
    }

    @Override
    public boolean isRegistered() {
        return HookManager.get().getHooks().contains(this);
    }

    @Override
    public String getPlugin() {
        return "FolfScoreboard";
    }

    @Override
    public String getId() {
        return "FolfScoreboard";
    }
}
