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

    // Used to manage the placeholder of status at gui
    public String getPlayerGlowingStatus(Player player) {
        return messageHandler.getMessage(player.isGlowing() ? Messages.GLOW_STATUS_TRUE : Messages.GLOW_STATUS_FALSE);
    }

    public String getPlayerGlowColorName(Player player) {
        Team team = findPlayerTeam(player);
        return (player.isGlowing() && team != null) ? team.getColor().name() : messageHandler.getMessage(Messages.GLOW_STATUS_NONE);
    }

    public String getPlayerGlowColor(Player player) {
        Team team = findPlayerTeam(player);
        return (player.isGlowing() && team != null) ? team.getColor().toString() : "";
    }

    private Team findPlayerTeam(Player player) {
        for (Team team : glowManager.getGlowTeams().values()) {
            if (team.hasEntry(player.getName())) {
                return team;
            }
        }
        return null;
    }

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
