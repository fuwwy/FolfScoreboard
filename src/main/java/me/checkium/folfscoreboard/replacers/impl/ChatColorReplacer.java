package me.checkium.folfscoreboard.replacers.impl;

import me.checkium.folfscoreboard.ScoreboardLine;
import me.checkium.folfscoreboard.replacers.LineReplacer;
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
