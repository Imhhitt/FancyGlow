package hhitt.fancyglow.utils;

import hhitt.fancyglow.FancyGlow;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerGlowingColor {

    private final FancyGlow plugin;

    public PlayerGlowingColor(FancyGlow plugin) {
        this.plugin = plugin;
    }

    // Get the color of the glowing
    public String getPlayerGlowColor(Player player) {
        if (player.isGlowing()) {
            Scoreboard board = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard();
            Team team = board.getPlayerTeam(player);
            if (team != null) {
                ChatColor glowColor = team.getColor();
                return glowColor.toString();
            }
        }
        return "";
    }
}
