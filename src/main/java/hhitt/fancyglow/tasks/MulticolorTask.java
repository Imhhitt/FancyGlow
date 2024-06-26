package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

public class MulticolorTask extends BukkitRunnable {
    private final FancyGlow plugin;
    private final Player player;
    private final Map<ChatColor, Team> glowTeams;
    private Iterator<ChatColor> colorIterator;

    public MulticolorTask(FancyGlow plugin, Player player, Map<ChatColor, Team> glowTeams) {
        this.plugin = plugin;
        this.player = player;
        this.glowTeams = glowTeams;
        this.colorIterator = EnumSet.allOf(ChatColor.class).iterator();
    }

    @Override
    public void run() {
        if (!colorIterator.hasNext()) {
            colorIterator = EnumSet.allOf(ChatColor.class).iterator();
        }
        ChatColor color = colorIterator.next();

        // Remover al jugador de todos los equipos
        for (Team team : glowTeams.values()) {
            if (team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
            }
        }

        // Agregar al jugador al nuevo equipo
        Team glowTeam = glowTeams.get(color);
        if (glowTeam != null) {
            glowTeam.addEntry(player.getName());
            player.setGlowing(true);
        }
    }
}
