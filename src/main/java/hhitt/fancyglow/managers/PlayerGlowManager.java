package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class PlayerGlowManager {

    private final GlowManager glowManager;
    private final MessageHandler messageHandler;

    public PlayerGlowManager(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    /**
     * Retrieves the player's glow status in a formatted message.
     *
     * @param player The player to check.
     * @return Formatted message indicating if the player is glowing.
     */
    public String getPlayerGlowingStatus(Player player) {
        boolean isGlowing = player.isGlowing() || glowManager.isFlashingTaskActive(player);
        return messageHandler.getMessage(isGlowing ? Messages.GLOW_STATUS_TRUE : Messages.GLOW_STATUS_FALSE);
    }

    /**
     * Gets the player's glow color name.
     *
     * @param player The player to check.
     * @return The player's glow color name, or a "none" status if not glowing.
     */
    public String getPlayerGlowColorName(Player player) {
        Team team = findPlayerTeam(player);
        return (team != null && player.isGlowing()) ? team.getColor().name() : messageHandler.getMessage(Messages.GLOW_STATUS_NONE);
    }

    /**
     * Gets the player's glow color code.
     *
     * @param player The player to check.
     * @return The player's glow color code, or an empty string if not glowing.
     */
    public String getPlayerGlowColor(Player player) {
        Team team = findPlayerTeam(player);
        return (team != null) ? team.getColor().toString() : "";
    }

    /**
     * Finds the team a player belongs to.
     *
     * @param player The player to check.
     * @return The team the player is registered in, or null if none.
     */
    public Team findPlayerTeam(Player player) {
        return glowManager.getGlowTeams().stream()
                .filter(team -> team.hasEntry(player.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the item's lore with placeholders.
     *
     * @param item   The item to update.
     * @param player The player whose placeholders will be used.
     */
    public void updateItemLore(ItemStack item, Player player) {
        if (item == null || player == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getLore() == null) return;

        List<String> updatedLore = meta.getLore().stream()
                .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                .toList();

        meta.setLore(updatedLore);
        item.setItemMeta(meta);
    }
}
