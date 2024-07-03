package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.utils.GlowManager;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class HeadClickListener implements Listener {
    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public HeadClickListener(FancyGlow plugin, GlowManager glowManager) {
        this.plugin = plugin;
        this.glowManager = glowManager;
    }

    @EventHandler
    public void onPlayerClickHead(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !(clickedInventory.getHolder() instanceof CreatingInventory)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null) return;

        Material itemType = currentItem.getType();
        int slot = event.getSlot();

        // Disable color head
        if (itemType == Material.PLAYER_HEAD && slot == 41) {
            glowManager.removeGlow(player);
            player.closeInventory();
        }

        // Multicolor head
        if (itemType == Material.PLAYER_HEAD && slot == 39) {

            if (!player.hasPermission("fancyglow.rainbow") || !player.hasPermission("fancyglow.all_colors")) {
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getNoPermissionMessage());
                player.closeInventory();
                return;
            }

            // Toggle multicolor mode
            glowManager.toggleMulticolorGlow(player);
            player.closeInventory();
        }
    }
}