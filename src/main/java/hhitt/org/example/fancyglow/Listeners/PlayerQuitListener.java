package hhitt.org.example.fancyglow.Listeners;

import hhitt.org.example.fancyglow.FancyGlow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerQuitListener implements Listener {
    public PlayerQuitListener(FancyGlow plugin) {
    }

    // Eliminar al jugador del equipo al salir del server para evitar problemas y/o bugs.

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Scoreboard scoreboard = player.getScoreboard();

        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
            }
        }
        player.setGlowing(false);
    }
}
