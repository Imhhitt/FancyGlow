package hhitt.org.example.fancyglow.Utils;

import hhitt.org.example.fancyglow.FancyGlow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IsGlowingVariable implements Listener {
    private static PlayerGlowManager glowManager;

    public IsGlowingVariable(FancyGlow plugin) {
        glowManager = new PlayerGlowManager(plugin);
    }

    public String replaceVariables(String message, Player player) {
        // Reemplazar %glow_status% con el estado de brillo real
        return message.replace("%glow_status%", glowManager.getPlayerGlowingStatus(player));
    }

    public static void updateItemLore(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    String line = lore.get(i);
                    line = line.replace("%glow_status%", glowManager.getPlayerGlowingStatus(player));
                    lore.set(i, line);
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }
}