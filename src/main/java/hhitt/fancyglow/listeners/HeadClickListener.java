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
import org.bukkit.scoreboard.Scoreboard;


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
        if (clickedInventory != null && clickedInventory.getHolder() instanceof CreatingInventory) {
            Player player = (Player) event.getWhoClicked();

            // Disable color head
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD
                    && event.getSlot() == 41) {

                if (glowManager.isMulticolorTaskActive(player)) {
                    glowManager.toggleMulticolorGlow(player);
                    player.setGlowing(false);
                    glowManager.removePlayerFromAllTeams(player);
                    MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
                    player.closeInventory();
                } else {
                    Scoreboard scoreboard = player.getScoreboard();
                    scoreboard.getTeams().stream()
                            .filter(team -> team.hasEntry(player.getName()))
                            .forEach(team -> team.removeEntry(player.getName()));

                    String messageKey = player.isGlowing() ? plugin.getMainConfigManager().getDisableGlow()
                            : plugin.getConfig().getString("Messages.Not_Glowing");

                    MessageUtils.miniMessageSender(player, messageKey);
                    player.setGlowing(false);
                    player.closeInventory();
                }
            }

            // Multicolor head
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD
                    && event.getSlot() == 39) {

                if(!player.hasPermission("fancyglow.rainbow") || !player.hasPermission("fancyglow.all_colors")){
                    MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getNoPermissionMessage());
                    player.closeInventory();
                    return;
                }

                // Toggle multicolor mode
                glowManager.toggleMulticolorGlow(player);
            }
        }
    }
}