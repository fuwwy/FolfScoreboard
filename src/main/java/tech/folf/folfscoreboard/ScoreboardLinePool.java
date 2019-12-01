package tech.folf.folfscoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardLinePool {
    private List<ScoreboardLine> list = new ArrayList<>();

  public ScoreboardLine get(int index) {
      return list.get(index);
  }

  public void add(ScoreboardLine line) {
      list.add(line);
      updateIndexes();
  }

  public ScoreboardLine[] toArray() {
      return list.toArray(new ScoreboardLine[0]);
  }

  public void insertBefore(ScoreboardLine nextLine, ScoreboardLine line) {
      list.add(list.indexOf(nextLine), line);
      updateIndexes();
  }

    public void remove(ScoreboardLine line) {
        list.remove(line);
        updateIndexes();
  }

    public ScoreboardLinePool clone() {
        ScoreboardLinePool newLinePool = new ScoreboardLinePool();
        list.forEach(scoreboardLine -> newLinePool.add(scoreboardLine.clone()));
        return newLinePool;
    }

    private void updateIndexes() {
        AtomicInteger listSize = new AtomicInteger(list.size() - 1);
        list.forEach(scoreboardLine -> scoreboardLine.setIndex(listSize.getAndDecrement()));
    }

    public List<ScoreboardLine> getRawList() {
        return list;
    }

    public int size() {
        return list.size();
    }
}
