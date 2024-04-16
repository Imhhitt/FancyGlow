package hhitt.org.example.fancyglow.Listeners;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Inventory.CreatingInventory;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;


public class MenuClickListener implements Listener {

    private final FancyGlow plugin;
    private Team glowTeamAqua;
    private Team glowTeamBlue;
    private Team glowTeamRed;
    private Team glowTeamLime;
    private Team glowTeamPink;
    private Team glowTeamWhite;
    private Team glowTeamBlack;

    public MenuClickListener(FancyGlow plugin) {
        this.plugin = plugin;
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
        if (clickedItem == null || !clickedItem.hasItemMeta() || !Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) {
            return;
        }

        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        toggleGlow(p, itemName);
    }



    private void toggleGlow(Player player, String colorName) {
        ChatColor color;
        switch (colorName.toLowerCase()) {
            case "aqua" -> color = ChatColor.AQUA;
            case "blue" -> color = ChatColor.BLUE;
            case "red" -> color = ChatColor.RED;
            case "lime" -> color = ChatColor.GREEN;
            case "pink" -> color = ChatColor.LIGHT_PURPLE;
            case "black" -> color = ChatColor.BLACK;
            case "white" -> color = ChatColor.WHITE;
            default -> {
                return;
            }
        }

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

    private Team getOrCreateTeam(ChatColor color) {
        switch (color) {
            case AQUA -> {
                if (glowTeamAqua == null) {
                    glowTeamAqua = createTeam("aquaTeam", ChatColor.AQUA);
                }
                return glowTeamAqua;
            }
            case BLUE -> {
                if (glowTeamBlue == null) {
                    glowTeamBlue = createTeam("blueTeam", ChatColor.BLUE);
                }
                return glowTeamBlue;
            }
            case RED -> {
                if (glowTeamRed == null) {
                    glowTeamRed = createTeam("redTeam", ChatColor.RED);
                }
                return glowTeamRed;
            }
            case GREEN -> {
                if (glowTeamLime == null) {
                    glowTeamLime = createTeam("limeTeam", ChatColor.GREEN);
                }
                return glowTeamLime;
            }
            case LIGHT_PURPLE -> {
                if (glowTeamPink == null) {
                    glowTeamPink = createTeam("pinkTeam", ChatColor.LIGHT_PURPLE);
                }
                return glowTeamPink;
            }
            case BLACK -> {
                if (glowTeamBlack == null) {
                    glowTeamBlack = createTeam("blackTeam", ChatColor.BLACK);
                }
                return glowTeamBlack;
            }
            case WHITE -> {
                if (glowTeamWhite == null) {
                    glowTeamWhite = createTeam("whiteTeam", ChatColor.WHITE);
                }
                return glowTeamWhite;
            }
            default -> {
                return null;
            }
        }
    }

    private Team createTeam(String teamName, ChatColor color) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = board.registerNewTeam(teamName);
        team.setColor(color);
        return team;
    }
}
