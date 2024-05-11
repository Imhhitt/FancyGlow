package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerQuitListener implements Listener {

    private FancyGlow plugin;
    public PlayerQuitListener(FancyGlow plugin) {
        this.plugin = plugin;
    }

    // Actions of persistant mode
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        if(!plugin.getConfig().getBoolean("Persistant-Mode")){

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
}
