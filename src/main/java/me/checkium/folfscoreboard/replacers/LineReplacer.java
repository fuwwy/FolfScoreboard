package me.checkium.folfscoreboard.replacers;

import org.bukkit.Bukkit;

public abstract class LineReplacer implements ILineReplacer {

    public LineReplacer() {
        if (getPlugin() == null || Bukkit.getPluginManager().isPluginEnabled(getPlugin())) {
            if (register()) {
                LineReplacerManager.get().getHooks().add(this);
            }
        }
    }

    @Override
    public boolean register() {
        return true;
    }

    @Override
    public boolean isRegistered() {
        return LineReplacerManager.get().getHooks().contains(this);
    }

    @Override
    public String getPlugin() {
        return null;
    }
}
