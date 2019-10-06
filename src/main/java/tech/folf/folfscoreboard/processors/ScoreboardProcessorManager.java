package tech.folf.folfscoreboard.processors;

import tech.folf.folfscoreboard.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProcessorManager {
    private static ScoreboardProcessorManager singletonInstance;
    private List<ScoreboardProcessor> scoreboardProcessorList = new ArrayList<>();
    private Logger logger = new Logger("ScoreboardProcessor");

    private ScoreboardProcessorManager() {
    }

    public static ScoreboardProcessorManager get() {
        if (singletonInstance == null) singletonInstance = new ScoreboardProcessorManager();
        return singletonInstance;
    }

    public List<ScoreboardProcessor> getProcessors() {
        return scoreboardProcessorList;
    }

    public Logger getLogger() {
        return logger;
    }
}
