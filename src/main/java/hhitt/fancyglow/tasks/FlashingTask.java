package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class FlashingTask extends BukkitRunnable {
    private final GlowManager glowManager;
    private final PlayerGlowManager playerGlowManager;

    public FlashingTask(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @Override
    public void run() {
        // Cancel task if none at this set
        if (glowManager.getFlashingPlayerSet().isEmpty()) cancel();

        Player player;
        Team glowTeam;
        for (UUID uuid : glowManager.getFlashingPlayerSet()) {
            // If the uuid is still stored, means the player is online, so the reference shouldn't be null.
            player = Bukkit.getPlayer(uuid);

            // Get player current glowing team.
            glowTeam = playerGlowManager.findPlayerTeam(player);

            // If team null or player is in respawn screen return.
            if (glowTeam == null || player.isDead()) {
                continue;
            }

            // Toggle glowing state.
            player.setGlowing(!player.isGlowing());

            // Update the scoreboard if necessary
            if (glowTeam.getScoreboard() != null) {
                player.setScoreboard(glowTeam.getScoreboard());
            }
        }
    }
}