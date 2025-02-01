package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

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
        // Cancel task if there are no players in the multicolor set
        if (glowManager.getMulticolorPlayerSet().isEmpty()) return;

        // Get the current color for this iteration
        ChatColor currentColor = GlowManager.COLORS_ARRAY[currentIndex];

        // Get or create the team corresponding to the current color
        Team currentTeam = glowManager.getOrCreateTeam(currentColor);
        if (currentTeam == null) return;

        for (UUID uuid : glowManager.getMulticolorPlayerSet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || player.isDead()) continue;

            // Get the player's last team, if any
            Team lastTeam = playerGlowManager.findPlayerTeam(player);

            // Assign the player to the new team
            currentTeam.addEntry(player.getName());

            // Remove from the last team, if applicable
            if (lastTeam != null && lastTeam != currentTeam) {
                lastTeam.removeEntry(player.getName());
            }

            // Update the scoreboard only if necessary
            if (player.getScoreboard() != currentTeam.getScoreboard()) {
                player.setScoreboard(currentTeam.getScoreboard());
            }
        }

        // Move to the next color in the sequence
        currentIndex = (currentIndex + 1) % GlowManager.COLORS_ARRAY.length;
    }
}
