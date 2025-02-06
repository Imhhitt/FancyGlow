package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerGlowManager {

    private final GlowManager glowManager;
    private final MessageHandler messageHandler;

    public PlayerGlowManager(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    /**
     * @param player Player to search to.
     *
     * @return Returns player formatted glowing status.
     */
    public String getPlayerGlowingStatus(Player player) {
        return messageHandler.getMessage(!getPlayerGlowingMode(player).equalsIgnoreCase("NONE") ? Messages.GLOW_STATUS_TRUE : Messages.GLOW_STATUS_FALSE);
    }

    /**
     * @param player Player to search to.
     *
     * @return Returns the mode the player is on.
     */
    public String getPlayerGlowingMode(Player player) {

        if (!player.isGlowing() && !glowManager.isFlashingTaskActive(player)) {
            return "NONE";
        }
        if (glowManager.isMulticolorTaskActive(player) && glowManager.isFlashingTaskActive(player)) {
            return "PARTY";
        }
        if (glowManager.isFlashingTaskActive(player)) {
            return "FLASHING";
        }
        if (glowManager.isMulticolorTaskActive(player)) {
            return "RAINBOW";
        }

        return getPlayerGlowColorName(player.getPlayer());
    }

    /**
     * @param player Player to search to.
     *
     * @return Player glow color name, if not glowing returns none status.
     */
    public String getPlayerGlowColorName(Player player) {
        Team team = findPlayerTeam(player);
        return (player.isGlowing() && team != null) ? team.getColor().name() : messageHandler.getMessage(Messages.GLOW_STATUS_NONE);
    }

    /**
     * @param player Player to search to.
     *
     * @return Player glow color format if not glowing returns an empty string.
     */
    public String getPlayerGlowColor(Player player) {
        Team team = findPlayerTeam(player);
        return (team != null) ? team.getColor().toString() : "";
    }

    /**
     * @param player Player to search to.
     *
     * @return Team where player has a registry on, if none returns null
     */
    public Team findPlayerTeam(Player player) {
        for (Team team : glowManager.getGlowTeams()) {
            if (team.hasEntry(player.getName())) {
                return team;
            }
        }
        return null;
    }

    /**
     * Updates head lore placeholders.
     *
     * @param item   Item to update to.
     * @param player Player whose inventory if from.
     */
    public void updateItemLore(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            List<String> lore = new ArrayList<>();

            for (String line : meta.getLore()) {
                lore.add(PlaceholderAPI.setPlaceholders(player, line));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }
}
