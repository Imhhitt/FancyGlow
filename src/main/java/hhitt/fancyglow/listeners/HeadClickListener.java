package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
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
    private final MessageHandler messageHandler;

    public HeadClickListener(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    @EventHandler
    public void onPlayerClickHead(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !(clickedInventory.getHolder() instanceof CreatingInventory)) return;

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getType() != Material.PLAYER_HEAD) return;

        Player player = (Player) event.getWhoClicked();
        switch (event.getSlot()) {
            case 39 -> {
                // Multicolor head
                if (!player.hasPermission("fancyglow.rainbow") || !player.hasPermission("fancyglow.all_colors")) {
                    messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                    player.closeInventory();
                    return;
                }

                // Toggle multicolor mode
                glowManager.toggleMulticolorGlow(player);
                player.closeInventory();
            }
            case 40 -> {
                // Flashing head
                if (!player.hasPermission("fancyglow.flashing")) {
                    messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                    player.closeInventory();
                    return;
                }

                if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isMulticolorTaskActive(player)) {
                    messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
                    return;
                }

                // Toggle flashing mode
                glowManager.toggleFlashingGlow(player);
                player.closeInventory();
            }
            case 41 -> {
                // Disable color head
                glowManager.removeGlow(player);
                player.closeInventory();

                messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
            }
        }
    }
}