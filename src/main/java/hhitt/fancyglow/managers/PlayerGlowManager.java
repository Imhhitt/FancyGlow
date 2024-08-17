package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerGlowManager {

    private final FancyGlow plugin;

    public PlayerGlowManager(FancyGlow plugin) {
        this.plugin = plugin;
    }

    // Used to manage the placeholder of status at gui
    public String getPlayerGlowingStatus(Player player) {
        String trueStatus = MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGlowStatusTrue());
        String falseStatus = MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGlowStatusFalse());

        return player.isGlowing() ? trueStatus : falseStatus;
    }

    public String getPlayerGlowColorName(Player player) {
        if (player.isGlowing()) {
            Scoreboard board = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard();
            Team team = board.getPlayerTeam(player);
            if (team != null) {
                ChatColor glowColor = team.getColor();
                return glowColor.name();
            }
        }
        return "NONE";
    }

    public String getPlayerGlowColor(Player player) {
        if (player.isGlowing()) {
            Scoreboard board = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard();
            Team team = board.getPlayerTeam(player);
            if (team != null) {
                ChatColor glowColor = team.getColor();
                return glowColor.toString();
            }
        }
        return "";
    }

    public void updateItemLore(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            List<String> lore = new ArrayList<>();

            for (String line : meta.getLore()) {
                lore.add(line.replace("%fancyglow_status%", getPlayerGlowingStatus(player)));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }
}
