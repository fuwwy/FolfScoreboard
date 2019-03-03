package com.folfstore.folfscoreboard;

/**
 *  Sorcery for the line pool
 */
public class ScoreboardLinePool {
  ScoreboardLine[] cache;
  private int size;
  private ScoreboardLine firstLine;
  private ScoreboardLine lastLine;

  public ScoreboardLine get(int index) {
    if (index >= 0 && index < this.size) {
      if (this.cache == null) {
        this.cache = this.toArray();
      }

      return this.cache[index];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  public void add(ScoreboardLine line) {
    ++this.size;
    if (this.lastLine == null) {
      this.firstLine = line;
      this.lastLine = line;
    } else {
      this.lastLine.nextLine = line;
      line.previousLine = this.lastLine;
    }

    this.lastLine = line;
    this.cache = null;
    line.index = 0;
  }

  public ScoreboardLine[] toArray() {
    int currentInsnIndex = 0;
    ScoreboardLine currentLine = this.firstLine;

    ScoreboardLine[] insnNodeArray;
    for (insnNodeArray = new ScoreboardLine[this.size];
        currentLine != null;
        currentLine = currentLine.nextLine) {
      insnNodeArray[currentInsnIndex] = currentLine;
      currentLine.index = currentInsnIndex++;
    }

    return insnNodeArray;
  }

  public void insertBefore(ScoreboardLine nextLine, ScoreboardLine line) {
    ++this.size;
    ScoreboardLine previousInsn = nextLine.previousLine;
    if (previousInsn == null) {
      this.firstLine = line;
    } else {
      previousInsn.nextLine = line;
    }

    nextLine.previousLine = line;
    line.nextLine = nextLine;
    line.previousLine = previousInsn;
    this.cache = null;
    line.index = 0;
  }

  public void remove(final ScoreboardLine line) {
    --size;
    ScoreboardLine nextLine = line.nextLine;
    ScoreboardLine previousLine = line.previousLine;
    if (nextLine == null) {
      if (previousLine == null) {
        firstLine = null;
        lastLine = null;
      } else {
        previousLine.nextLine = null;
        lastLine = previousLine;
      }
    } else {
      if (previousLine == null) {
        firstLine = nextLine;
        nextLine.previousLine = null;
      } else {
        previousLine.nextLine = nextLine;
        nextLine.previousLine = previousLine;
      }
    }
    cache = null;
    line.index = -1;
    line.previousLine = null;
    line.nextLine = null;
  }

  public ScoreboardLinePool clone() {
    ScoreboardLinePool pool = new ScoreboardLinePool();
    for (ScoreboardLine scoreboardLine : toArray()) {
      pool.add(scoreboardLine.clone());
    }
    return pool;
  }
}
