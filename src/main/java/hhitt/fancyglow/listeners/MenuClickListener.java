package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.utils.GlowManager;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Map;

public class MenuClickListener implements Listener {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

    // Implementación para la lógica para mapear los colores de la armadura de cuero a los colores.
    private static final Map<Color, ChatColor> colorMap = new HashMap<>();

    static {
        colorMap.put(Color.BLACK, ChatColor.BLACK);
        colorMap.put(Color.BLUE, ChatColor.BLUE);
        colorMap.put(Color.LIME, ChatColor.GREEN);
        colorMap.put(Color.AQUA, ChatColor.AQUA);
        colorMap.put(Color.RED, ChatColor.RED);
        colorMap.put(Color.FUCHSIA, ChatColor.LIGHT_PURPLE);
        colorMap.put(Color.YELLOW, ChatColor.YELLOW);
        colorMap.put(Color.WHITE, ChatColor.WHITE);
        colorMap.put(Color.SILVER, ChatColor.GRAY);
        colorMap.put(Color.GRAY, ChatColor.DARK_GRAY);
        colorMap.put(Color.NAVY, ChatColor.DARK_BLUE);
        colorMap.put(Color.GREEN, ChatColor.DARK_GREEN);
        colorMap.put(Color.TEAL, ChatColor.DARK_AQUA);
        colorMap.put(Color.MAROON, ChatColor.DARK_RED);
        colorMap.put(Color.PURPLE, ChatColor.DARK_PURPLE);
        colorMap.put(Color.ORANGE, ChatColor.GOLD);
    }

    public MenuClickListener(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = new GlowManager(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        Inventory inventoryClicked = e.getClickedInventory();
        if (inventoryClicked == null || !(inventoryClicked.getHolder() instanceof CreatingInventory)) {
            return;
        } else {
            e.setCancelled(true);
        }

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() != Material.LEATHER_CHESTPLATE) {
            return;
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) clickedItem.getItemMeta();
        if (meta == null) {
            return;
        }

        ChatColor color = getColorFromArmorColor(meta.getColor());
        if (!(color != null && glowManager.hasGlowPermission(p, color) ||
                color != null && p.hasPermission("fancyglow.all_colors") ||
                color != null && p.hasPermission("fancyglow.admin"))) {
            MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
            p.closeInventory();
            return;
        }

        glowManager.toggleGlow(p, color);
        p.closeInventory();
    }

    private ChatColor getColorFromArmorColor(org.bukkit.Color armorColor) {
        return colorMap.getOrDefault(armorColor, ChatColor.WHITE);
    }
}
