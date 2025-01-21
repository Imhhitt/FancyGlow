package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.UUID;

public class FlashingTask extends BukkitRunnable {

    private final FancyGlow plugin;
    private final GlowManager glowManager;
    private final PlayerGlowManager playerGlowManager;

    public FlashingTask(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @Override
    public void run() {
        // Cancel task if none at this set
        if (glowManager.getFlashingPlayerSet().isEmpty()) cancel();

        for (UUID uuid : glowManager.getFlashingPlayerSet()) {
            Player player = Objects.requireNonNull(Bukkit.getPlayer(uuid));

            // Get player current glowing team.
            Team glowTeam = playerGlowManager.findPlayerTeam(player);

            // If team null or player is in respawn screen return.
            if (glowTeam == null || player.isDead()) {
                continue;
            }

            // Toggle glowing state.
            player.setGlowing(!player.isGlowing());

            // Update the scoreboard if necessary
            player.setScoreboard(Objects.requireNonNull(glowTeam.getScoreboard()));
        }
    }
}