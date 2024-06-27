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
import org.bukkit.scoreboard.Team;

public class MenuClickListener implements Listener {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

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
        } else {
            toggleGlow(p, color);
        }
    }

    private void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = glowManager.getOrCreateTeam(color);
        if (glowTeam != null) {
            String cleanName = ChatColor.stripColor(player.getName());
            if (glowTeam.hasEntry(cleanName)) {
                glowTeam.removeEntry(cleanName);
                player.setGlowing(false);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
                player.closeInventory();
            } else {
                glowManager.removePlayerFromAllTeams(player);
                glowTeam.addEntry(cleanName);
                player.setGlowing(true);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
                player.closeInventory();
            }
        }
    }

    private ChatColor getColorFromArmorColor(org.bukkit.Color armorColor) {
        // Implementación para la lógica para mapear los colores de la armadura de cuero a los colores
        if (armorColor.equals(Color.BLACK)) {
            return ChatColor.BLACK;
        } else if (armorColor.equals(Color.BLUE)) {
            return ChatColor.BLUE;
        } else if (armorColor.equals(Color.LIME)) {
            return ChatColor.GREEN;
        } else if (armorColor.equals(Color.AQUA)) {
            return ChatColor.AQUA;
        } else if (armorColor.equals(Color.RED)) {
            return ChatColor.RED;
        } else if (armorColor.equals(Color.FUCHSIA)) {
            return ChatColor.LIGHT_PURPLE;
        } else if (armorColor.equals(Color.YELLOW)) {
            return ChatColor.YELLOW;
        } else if (armorColor.equals(Color.WHITE)) {
            return ChatColor.WHITE;
        } else if (armorColor.equals(Color.SILVER)) {
            return ChatColor.GRAY;
        } else if (armorColor.equals(Color.GRAY)) {
            return ChatColor.DARK_GRAY;
        } else if (armorColor.equals(Color.NAVY)) {
            return ChatColor.DARK_BLUE;
        } else if (armorColor.equals(Color.GREEN)) {
            return ChatColor.DARK_GREEN;
        } else if (armorColor.equals(Color.TEAL)) {
            return ChatColor.DARK_AQUA;
        } else if (armorColor.equals(Color.MAROON)) {
            return ChatColor.DARK_RED;
        } else if (armorColor.equals(Color.PURPLE)) {
            return ChatColor.DARK_PURPLE;
        } else if (armorColor.equals(Color.ORANGE)) {
            return ChatColor.GOLD;
        } else {
            return null;
        }
    }
}
