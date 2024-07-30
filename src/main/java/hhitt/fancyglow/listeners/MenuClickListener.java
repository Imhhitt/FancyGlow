package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MenuClickListener implements Listener {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public MenuClickListener(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
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

        ChatColor color = ColorUtils.getColorFromArmorColor(meta.getColor());
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
}
