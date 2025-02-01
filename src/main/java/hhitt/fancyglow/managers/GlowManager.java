package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.tasks.FlashingTask;
import hhitt.fancyglow.tasks.MulticolorTask;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class GlowManager {

    private static final ChatColor[] COLORS_ARRAY = ChatColor.values();

    private final FancyGlow plugin;
    private final MessageHandler messageHandler;
    private final Set<UUID> flashingPlayerSet = new HashSet<>();
    private final Set<UUID> multicolorPlayerSet = new HashSet<>();
    private BukkitTask flashingTask, multicolorTask;

    public GlowManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.messageHandler = plugin.getMessageHandler();
    }

    public void toggleMulticolorGlow(Player player) {
        disableOtherGlows(player);
        if (multicolorPlayerSet.remove(player.getUniqueId())) {
            removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        } else {
            multicolorPlayerSet.add(player.getUniqueId());
            player.setGlowing(true);
            messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
        }
    }

    public void toggleFlashingGlow(Player player) {
        disableOtherGlows(player);
        if (flashingPlayerSet.remove(player.getUniqueId())) {
            removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        } else {
            flashingPlayerSet.add(player.getUniqueId());
            player.setGlowing(true);
            messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
        }
    }

    private void disableOtherGlows(Player player) {
        multicolorPlayerSet.remove(player.getUniqueId());
        flashingPlayerSet.remove(player.getUniqueId());
    }

    public void toggleGlow(Player player, ChatColor color) {
        removeGlow(player);
        getOrCreateTeam(color).addEntry(player.getName());
        player.setGlowing(true);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }

    public void removeGlow(Player player) {
        player.setGlowing(false);
        removePlayerFromAllTeams(player);
    }

    public void removePlayerFromAllTeams(Player player) {
        Optional.ofNullable(plugin.getServer().getScoreboardManager())
                .map(manager -> manager.getMainScoreboard())
                .ifPresent(board -> Arrays.stream(COLORS_ARRAY)
                        .map(color -> board.getTeam(color.name()))
                        .filter(Objects::nonNull)
                        .forEach(team -> team.removeEntry(player.getName())));

        multicolorPlayerSet.remove(player.getUniqueId());
        flashingPlayerSet.remove(player.getUniqueId());
    }

    public Team getOrCreateTeam(ChatColor color) {
        return Optional.ofNullable(plugin.getServer().getScoreboardManager())
                .map(manager -> manager.getMainScoreboard().getTeam(color.name()))
                .orElseGet(() -> createTeam(color));
    }

    public Team createTeam(ChatColor color) {
        return Optional.ofNullable(plugin.getServer().getScoreboardManager())
                .map(manager -> manager.getMainScoreboard())
                .map(board -> board.getTeam(color.name()) != null ? board.getTeam(color.name()) : board.registerNewTeam(color.name()))
                .map(team -> {
                    team.setColor(color);
                    return team;
                }).orElse(null);
    }

    public void scheduleMulticolorTask() {
        if (multicolorTask == null || multicolorTask.isCancelled()) {
            multicolorTask = new MulticolorTask(plugin)
                    .runTaskTimer(plugin, 5L, plugin.getConfiguration().getInt("Rainbow_Update_Interval"));
        }
    }

    public void stopMulticolorTask() {
        Optional.ofNullable(multicolorTask).ifPresent(BukkitTask::cancel);
    }

    public void scheduleFlashingTask() {
        if (flashingTask == null || flashingTask.isCancelled()) {
            flashingTask = new FlashingTask(plugin)
                    .runTaskTimerAsynchronously(plugin, 5L, plugin.getConfiguration().getInt("Flashing_Update_Interval"));
        }
    }

    public void stopFlashingTask() {
        Optional.ofNullable(flashingTask).ifPresent(BukkitTask::cancel);
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

    public Set<Team> getGlowTeams() {
        return Optional.ofNullable(plugin.getServer().getScoreboardManager())
                .map(manager -> manager.getMainScoreboard().getTeams())
                .orElse(Collections.emptySet());
    }
}
