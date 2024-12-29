package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.tasks.MulticolorTask;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class GlowManager {

    private final FancyGlow plugin;
    private final Map<ChatColor, Team> glowTeams;
    private final Map<UUID, MulticolorTask> multicolorTasks;

    public GlowManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
        this.multicolorTasks = new HashMap<>();
    }

    public void toggleMulticolorGlow(Player player) {
        if (isMulticolorTaskActive(player)) {
            removeGlow(player);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            return;
        }

        int ticks = plugin.getConfig().getInt("Rainbow_Update_Interval");
        MulticolorTask task = new MulticolorTask(plugin, player);
        task.runTaskTimer(plugin, 0L, ticks);
        multicolorTasks.put(player.getUniqueId(), task);
        player.setGlowing(true);
        MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
    }

    public boolean isMulticolorTaskActive(Player player) {
        return multicolorTasks.containsKey(player.getUniqueId());
    }

    public void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = getOrCreateTeam(color);
        String cleanName = ChatColor.stripColor(player.getName());

        // Remove any existing glow
        removeGlow(player);

        // Add the player to the team and enable glowing
        glowTeam.addEntry(cleanName);
        player.setGlowing(true);
        MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
    }

    public void removeGlow(Player player) {
        // Remove glow from player
        player.setGlowing(false);
        // Remove from any existing color team
        removePlayerFromAllTeams(player);
    }

    public void removePlayerFromAllTeams(Player player) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        String cleanName = ChatColor.stripColor(player.getName());

        // Handle remove if rainbow selected
        if (isMulticolorTaskActive(player)) {
            // Cancel task
            multicolorTasks.get(player.getUniqueId()).cancel();
            // Remove from map
            multicolorTasks.remove(player.getUniqueId());
        }

        // Attempt to remove player from any color team
        Arrays.stream(ChatColor.values())
                // Allowed team names. (Color names from ChatColor...)
                .map(color -> board.getTeam(color.name()))
                // Make sure team exists
                .filter(Objects::nonNull)
                // Filter if player is in the team
                .filter(team -> team.hasEntry(cleanName))
                // Remove from the team
                .forEach(team -> team.removeEntry(cleanName));

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
        return hasGlowPermission(player, color.name());
    }

    public boolean hasGlowPermission(Player player, String colorName) {
        return player.hasPermission("fancyglow." + colorName.toLowerCase());
    }

    public void cancelMulticolorTasks() {
        for (MulticolorTask task : multicolorTasks.values()) {
            task.cancel();
        }
        multicolorTasks.clear();
    }
}
