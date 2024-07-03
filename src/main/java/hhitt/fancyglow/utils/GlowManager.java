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
            removeGlow(player);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            return;
        }

        int ticks = plugin.getConfig().getInt("Rainbow_Update_Interval");
        MulticolorTask task = new MulticolorTask(plugin, player);
        task.runTaskTimer(plugin, 0L, ticks);
        multicolorTasks.put(player, task);
        player.setGlowing(true);
        MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
    }

    public boolean isMulticolorTaskActive(Player player) {
        return multicolorTasks.containsKey(player);
    }

    public void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = getOrCreateTeam(color);
        String cleanName = ChatColor.stripColor(player.getName());

        removeGlow(player);

        if (glowTeam.hasEntry(cleanName)) {
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            return;
        }

        glowTeam.addEntry(cleanName);
        player.setGlowing(true);
        MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
    }

    public void removeGlow(Player player) {
        player.setGlowing(false);
        removePlayerFromAllTeams(player);

        if (isMulticolorTaskActive(player)) {
            multicolorTasks.get(player).cancel();
            multicolorTasks.remove(player);
        }
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
        return glowTeams.computeIfAbsent(color, k -> createTeam(color));
    }

    public Team createTeam(ChatColor color) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        Team team = board.getTeam(color.name());
        if (team == null) {
            team = board.registerNewTeam(color.name());
            team.setColor(color);
        }
        return team;
    }

    public boolean hasGlowPermission(Player player, ChatColor color) {
        return player.hasPermission("fancyglow." + color.name().toLowerCase());
    }
}
