package hhitt.fancyglow.utils;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.tasks.MulticolorTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class GlowManager {
    private final FancyGlow plugin;
    private final Map<ChatColor, Team> glowTeams;
    private final Map<Player, MulticolorTask> multicolorTasks;

    public GlowManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
        this.multicolorTasks = new HashMap<>();
    }

    public void toggleMulticolorGlow(Player player) {
        if (multicolorTasks.containsKey(player)) {
            MulticolorTask task = multicolorTasks.get(player);
            task.cancel();
            multicolorTasks.remove(player);
            removePlayerFromAllTeams(player);
            player.setGlowing(false);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            player.closeInventory();
        } else {
            int ticks = plugin.getConfig().getInt("Rainbow_Update_Interval");
            MulticolorTask task = new MulticolorTask(plugin, player);
            task.runTaskTimer(plugin, 0L, ticks);
            multicolorTasks.put(player, task);
            player.setGlowing(true);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
            player.closeInventory();
        }
    }

    public boolean isMulticolorTaskActive(Player player) {
        return multicolorTasks.containsKey(player);
    }

    public void removePlayerFromAllTeams(Player player) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        String cleanName = ChatColor.stripColor(player.getName());
        for (Team team : board.getTeams()) {
            if (team.hasEntry(cleanName)) {
                team.removeEntry(cleanName);
            }
        }
    }

    public Team getOrCreateTeam(ChatColor color) {
        Team glowTeam = glowTeams.get(color);
        try { glowTeam.getName(); } catch (IllegalStateException | NullPointerException e) {
            glowTeam = createTeam(color);
            glowTeams.put(color, glowTeam);
        }
        return glowTeam;
    }

    public Team createTeam(ChatColor color) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        if (board.getTeam(color.name()) == null) {
            Team team = board.registerNewTeam(color.name());
            team.setColor(color);
            return team;
        }
        return board.getTeam(color.name());
    }

    public boolean hasGlowPermission(Player player, ChatColor color) {
        return player.hasPermission("fancyglow." + color.name().toLowerCase());
    }
}
