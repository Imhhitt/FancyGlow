package hhitt.fancyglow.tasks;

import hhitt.fancyglow.FancyGlow;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.Map;

public class MulticolorTask extends BukkitRunnable {

    private final FancyGlow plugin;
    private final Player player;
    private final Map<ChatColor, Team> glowTeams;
    private final ChatColor[] colorArray;
    private int currentIndex;

    public MulticolorTask(FancyGlow plugin, Player player, Map<ChatColor, Team> glowTeams) {
        this.plugin = plugin;
        this.player = player;
        this.glowTeams = glowTeams;
        this.colorArray = getAllColors();
        this.currentIndex = 0;
    }

    @Override
    public void run() {
        ChatColor color = colorArray[currentIndex];

        // Remover al jugador de todos los equipos excepto el actual
        Team glowTeam = glowTeams.get(color);
        for (Team team : glowTeams.values()) {
            if (team != glowTeam && team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
            }
        }

        // Agregar al jugador al nuevo equipo
        if (!glowTeam.hasEntry(player.getName())) {
            glowTeam.addEntry(player.getName());
        }

        // Actualizar el scoreboard si es necesario
        player.setScoreboard(plugin.getServer().getScoreboardManager().getMainScoreboard());

        // Incrementar el índice para el siguiente color
        currentIndex++;
        if (currentIndex >= colorArray.length) {
            currentIndex = 0;
        }
    }

    private ChatColor[] getAllColors() {
        return new ChatColor[] {
                ChatColor.BLACK, ChatColor.DARK_BLUE, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA,
                ChatColor.DARK_RED, ChatColor.DARK_PURPLE, ChatColor.GOLD, ChatColor.GRAY,
                ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.GREEN, ChatColor.AQUA,
                ChatColor.RED, ChatColor.LIGHT_PURPLE, ChatColor.YELLOW, ChatColor.WHITE
        };
    }
}