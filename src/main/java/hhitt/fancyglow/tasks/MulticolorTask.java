package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.UUID;

public class MulticolorTask extends BukkitRunnable {

    private int currentIndex = 0;
    private final GlowManager glowManager;
    private final PlayerGlowManager playerGlowManager;

    public MulticolorTask(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @Override
    public void run() {
        // Cancel task if none at this set
        if (glowManager.getMulticolorPlayerSet().isEmpty()) return;

        // Get current color iteration.
        ChatColor currentColor = GlowManager.COLORS_ARRAY[currentIndex];

        // Get or create the team corresponding to the current color
        Team currentTeam = glowManager.getOrCreateTeam(currentColor);

        Player player;
        Team lastTeam;
        for (UUID uuid : glowManager.getMulticolorPlayerSet()) {
            // If the uuid is still stored, means the player is online, so the reference shouldn't be null.
            player = Objects.requireNonNull(Bukkit.getPlayer(uuid));
            // Ignore if player is on respawn screen.
            if (player.isDead()) continue;

            // Find and define player last team
            lastTeam = playerGlowManager.findPlayerTeam(player);

            // Straight away add the player to the new team
            currentTeam.addEntry(player.getName());

            // Remove the player from last team
            if (lastTeam != null) {
                lastTeam.removeEntry(player.getName());
            }

            // Update the scoreboard if necessary
            if (currentTeam.getScoreboard() != null && player.getScoreboard() != currentTeam.getScoreboard()) {
                player.setScoreboard(currentTeam.getScoreboard());
            }
        }

        // Increment the index for the next color
        currentIndex = (currentIndex + 1) % GlowManager.COLORS_ARRAY.length;
    }
}