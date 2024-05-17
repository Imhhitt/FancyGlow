package hhitt.fancyglow.listeners;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuClickListener implements Listener {

    // Gui click listener to manage plugin actions

    private FancyGlow plugin;
    private final Map<ChatColor, Team> glowTeams;
    public MenuClickListener(FancyGlow plugin) {

        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
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
        if (!(color != null && hasGlowPermission(p, color) || color != null && p.hasPermission("fancyglow.all_colors"))) {
            p.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
            p.closeInventory();
        } else {
            toggleGlow(p, color);
        }



    }

    private void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = getOrCreateTeam(color);
        if (glowTeam != null) {
            if (glowTeam.hasEntry(player.getName())) {
                glowTeam.removeEntry(player.getName());
                player.setGlowing(false);
                player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getDisableGlow()));
                player.closeInventory();
            } else {
                glowTeam.addEntry(player.getName());
                player.setGlowing(true);
                player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getEnableGlow()));
                player.closeInventory();
            }
        }
    }

    private boolean hasGlowPermission(Player player, ChatColor color){
        return player.hasPermission("fancyglow."+ color.name().toLowerCase());
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
        } else if (armorColor.equals(Color.ORANGE)){
            return ChatColor.GOLD;
        } else {
            return null;
        }
    }

    private Team getOrCreateTeam(ChatColor color) {
        Team glowTeam = glowTeams.get(color);
        if (glowTeam == null) {
            glowTeam = createTeam(color);
            glowTeams.put(color, glowTeam);
        }
        return glowTeam;
    }

    private Team createTeam(ChatColor color) {
        Scoreboard board = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard();
        Team team = board.registerNewTeam(color.name());
        team.setColor(color);
        return team;
    }
}
