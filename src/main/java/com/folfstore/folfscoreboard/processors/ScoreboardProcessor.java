package com.folfstore.folfscoreboard.processors;

import org.bukkit.Bukkit;

public abstract class ScoreboardProcessor implements IScoreboardProcessor {

    public ScoreboardProcessor() {
        if (getPlugin() == null || Bukkit.getPluginManager().isPluginEnabled(getPlugin())) {
            if (shouldRegister()) {
                ScoreboardProcessorManager.get().getLogger().debug("Registering processor " + getId());
                ScoreboardProcessorManager.get().getProcessors().add(this);
            }
        }
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean isRegistered() {
        return ScoreboardProcessorManager.get().getProcessors().contains(this);
    }

    @Override
    public String getPlugin() {
        return null;
    }
}