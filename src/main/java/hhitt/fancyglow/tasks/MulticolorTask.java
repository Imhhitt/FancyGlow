package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class MulticolorTask extends BukkitRunnable {

    private final ChatColor[] colorArray;
    private int currentIndex;
    private final GlowManager glowManager;

    public MulticolorTask(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.colorArray = glowManager.getAvailableColors();
        this.currentIndex = 0;
    }

    @Override
    public void run() {
        // Cancel task if none at this set
        if (glowManager.getMulticolorPlayerSet().isEmpty()) cancel();

        // Get current color iteration.
        ChatColor currentColor = colorArray[currentIndex];

        // Get or create the team corresponding to the current color
        Team glowTeam = glowManager.getOrCreateTeam(currentColor);
        // Will check if the scoreboard is available.
        if (glowTeam == null) {
            return;
        }

        Player player;
        Team team;
        for (UUID uuid : glowManager.getMulticolorPlayerSet()) {
            // If the uuid is still stored, means the player is online, so the reference shouldn't be null.
            player = Bukkit.getPlayer(uuid);
            if (player.isDead()) continue;

            // Remove the player from all teams except the current one
            for (ChatColor color : colorArray) {
                if (color != currentColor) {
                    team = glowManager.getOrCreateTeam(color);
                    if (team.hasEntry(player.getName())) {
                        team.removeEntry(player.getName());
                    }
                }
            }

            // Add the player to the new team
            if (!glowTeam.hasEntry(player.getName())) {
                glowTeam.addEntry(player.getName());
            }

            // Update the scoreboard if necessary
            if (glowTeam.getScoreboard() != null) {
                player.setScoreboard(glowTeam.getScoreboard());
            }
        }

        // Increment the index for the next color
        currentIndex++;
        if (currentIndex >= colorArray.length) {
            currentIndex = 0;
        }
    }
}