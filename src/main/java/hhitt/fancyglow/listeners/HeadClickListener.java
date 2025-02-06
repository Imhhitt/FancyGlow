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
            // Multicolor head
            case 39 -> {
                if (!player.hasPermission("fancyglow.rainbow")) {
                    messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                    player.closeInventory();
                    return;
                }

                // Disables combined modes if set on config.
                if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isFlashingTaskActive(player)) {
                    messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
                    return;
                }

                // Toggle rainbow mode
                player.closeInventory();
                boolean toggled = glowManager.toggleMulticolorGlow(player);
                messageHandler.sendMessage(player, toggled ? Messages.ENABLE_RAINBOW : Messages.DISABLE_RAINBOW);
            }
            // Flashing head
            case 40 -> {
                if (!player.hasPermission("fancyglow.flashing")) {
                    messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                    player.closeInventory();
                    return;
                }

                // Disables combined modes if set on config.
                if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isMulticolorTaskActive(player)) {
                    messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
                    return;
                }

                // Toggle flashing mode
                boolean toggled = glowManager.toggleFlashingGlow(player);
                messageHandler.sendMessage(player, toggled ? Messages.ENABLE_FLASHING : Messages.DISABLE_GLOW);
                player.closeInventory();
            }
            // Disable color head
            case 41 -> {
                player.closeInventory();
                glowManager.removeGlow(player);
                messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
            }
        }
    }
}