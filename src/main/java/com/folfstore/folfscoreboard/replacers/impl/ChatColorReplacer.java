package com.folfstore.folfscoreboard.replacers.impl;

import com.folfstore.folfscoreboard.replacers.LineReplacer;
import com.folfstore.folfscoreboard.ScoreboardLine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatColorReplacer extends LineReplacer {

  @Override
  public ScoreboardLine replace(ScoreboardLine l, Player p) {
    return l.setLine(ChatColor.translateAlternateColorCodes('&', l.getLine()));
  }

  @Override
  public String getId() {
    return "ChatColor";
  }

  @Override
  public String getMatcher() {
    return "&(.*)";
  }

}
