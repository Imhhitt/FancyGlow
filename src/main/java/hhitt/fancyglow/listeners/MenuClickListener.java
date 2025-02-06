package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
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
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    public MenuClickListener(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inventoryClicked = e.getClickedInventory();
        if (inventoryClicked == null || !(inventoryClicked.getHolder() instanceof CreatingInventory)) {
            return;
        } else {
            e.setCancelled(true);
        }

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() != Material.LEATHER_CHESTPLATE) return;

        LeatherArmorMeta meta = (LeatherArmorMeta) clickedItem.getItemMeta();
        if (meta == null) return;

        Player player = (Player) e.getWhoClicked();
        ChatColor color = ColorUtils.getColorFromArmorColor(meta.getColor());
        if (!(color != null && glowManager.hasGlowPermission(player, color) ||
                color != null && player.hasPermission("fancyglow.all_colors") ||
                color != null && player.hasPermission("fancyglow.admin"))) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            player.closeInventory();
            return;
        }

        if (!glowManager.isMulticolorTaskActive(player) && playerGlowManager.getPlayerGlowColorName(player).equalsIgnoreCase(color.name())) {
            messageHandler.sendMessage(player, Messages.COLOR_ALREADY_SELECTED);
            player.closeInventory();
            return;
        }

        player.closeInventory();
        glowManager.setGlow(player, color);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }
}
