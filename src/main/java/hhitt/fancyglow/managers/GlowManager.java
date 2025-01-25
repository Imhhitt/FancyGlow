package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.tasks.FlashingTask;
import hhitt.fancyglow.tasks.MulticolorTask;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class GlowManager {
    public static final ChatColor[] COLORS_ARRAY = new ChatColor[]{
            ChatColor.BLACK,
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.GRAY,
            ChatColor.DARK_GRAY,
            ChatColor.BLUE,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW,
            ChatColor.WHITE
    };
    private final FancyGlow plugin;
    private final MessageHandler messageHandler;

    private final Set<UUID> flashingPlayerSet;
    private final Set<UUID> multicolorPlayerSet;

    private BukkitTask flashingTask;
    private BukkitTask multicolorTask;

    public GlowManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.messageHandler = plugin.getMessageHandler();

        this.flashingPlayerSet = new HashSet<>();
        this.multicolorPlayerSet = new HashSet<>();
    }

    public void toggleMulticolorGlow(Player player) {
        final UUID playerId = player.getUniqueId();
        if (isMulticolorTaskActive(player)) {
            multicolorPlayerSet.remove(playerId);
            removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
            return;
        }

        multicolorPlayerSet.add(playerId);
        player.setGlowing(true);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }

    public void toggleFlashingGlow(Player player) {
        final UUID playerId = player.getUniqueId();
        if (isFlashingTaskActive(player)) {
            player.setGlowing(true);
            flashingPlayerSet.remove(playerId);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
            return;
        }

        flashingPlayerSet.add(playerId);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }

    public void toggleGlow(Player player, ChatColor color) {
        // Remove any existing glow
        removeGlow(player);
        // Add the player to the team and enable glowing
        // Avoid object-alloc by use directly reference returned and provide entry-value directly.
        final Team team = getOrCreateTeam(color);
        if (team == null) {
            return;
        }
        team.addEntry(ChatColor.stripColor(player.getName()));
        player.setGlowing(true);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }

    public void removeGlow(Player player) {
        // Remove glow from player
        player.setGlowing(false);
        // Remove from any existing color team
        removePlayerFromAllTeams(player);
    }

    public void removePlayerFromAllTeams(Player player) {
        if (plugin.getServer().getScoreboardManager() == null) {
            return;
        }
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        String cleanName = ChatColor.stripColor(player.getName());

        // Handle remove if rainbow selected
        if (isMulticolorTaskActive(player)) {
            // Remove from multicolor set.
            multicolorPlayerSet.remove(player.getUniqueId());
        }

        // Same as above but for flashing task 😎
        if (isFlashingTaskActive(player)) {
            // Remove from flashing set.
            flashingPlayerSet.remove(player.getUniqueId());
        }

        // Attempt to remove player from any color team
        Team team;
        for (final ChatColor color : ChatColor.values()) {
            team = board.getTeam(color.name());
            if (team == null) {
                continue;
            }
            team.removeEntry(cleanName);
        }
    }

    public Team getOrCreateTeam(ChatColor color) {
        for (Team team : getGlowTeams()) {
            if (!team.getName().equalsIgnoreCase(color.name())) continue;
            return team;
        }
        return createTeam(color);
    }

    public Team createTeam(ChatColor color) {
        if (plugin.getServer().getScoreboardManager() == null) {
            return null;
        }
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

    public void scheduleMulticolorTask() {
        if (multicolorTask == null || multicolorTask.isCancelled()) {
            int ticks = plugin.getConfiguration().getInt("Rainbow_Update_Interval");
            this.multicolorTask = new MulticolorTask(plugin)
                    .runTaskTimer(plugin, 5L, ticks);
        }
    }

    public void stopMulticolorTask() {
        if (multicolorTask != null && !multicolorTask.isCancelled()) {
            multicolorTask.cancel();
        }
    }

    public void scheduleFlashingTask() {
        if (flashingTask == null || flashingTask.isCancelled()) {
            int ticks = plugin.getConfiguration().getInt("Flashing_Update_Interval");
            this.flashingTask = new FlashingTask(plugin)
                    .runTaskTimerAsynchronously(plugin, 5L, ticks);
        }
    }

    public void stopFlashingTask() {
        if (flashingTask != null && !flashingTask.isCancelled()) {
            flashingTask.cancel();
        }
    }

    public boolean isFlashingTaskActive(Player player) {
        return flashingPlayerSet.contains(player.getUniqueId());
    }

    public boolean isMulticolorTaskActive(Player player) {
        return multicolorPlayerSet.contains(player.getUniqueId());
    }

    public Set<UUID> getFlashingPlayerSet() {
        return flashingPlayerSet;
    }

    public Set<UUID> getMulticolorPlayerSet() {
        return multicolorPlayerSet;
    }

    public List<Team> getGlowTeams() {
        if (plugin.getServer().getScoreboardManager() == null) {
            return List.of();
        }
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        final List<Team> teamList = new ArrayList<>(board.getTeams().size());
        final List<String> colorsNames = getAvailableColorsNames();
        for (final Team team : board.getTeams()) {
            if (!colorsNames.contains(team.getName())) continue;
            teamList.add(team);
        }
        return teamList;
    }

    public List<String> getAvailableColorsNames() {
        final List<String> colorNames = new ArrayList<>(getAvailableColors().length);
        for (final ChatColor color : COLORS_ARRAY) {
            if (colorNames.contains(color.name())) continue;
            colorNames.add(color.name());
        }
        return colorNames;
    }

    public ChatColor[] getAvailableColors() {
        return COLORS_ARRAY;
    }
}
