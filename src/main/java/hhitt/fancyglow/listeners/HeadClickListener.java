package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.tasks.MulticolorTask;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;


public class HeadClickListener implements Listener {

    // Head on gui logic
    private final FancyGlow plugin;
    private Map<Player, MulticolorTask> multicolorTasks;
    private final Map<ChatColor, Team> glowTeams;

    public HeadClickListener(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
        this.multicolorTasks = new HashMap<>();

        // Initialize glowTeams with all ChatColor values
        ChatColor[] allColors = {
                ChatColor.BLACK, ChatColor.DARK_BLUE, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA,
                ChatColor.DARK_RED, ChatColor.DARK_PURPLE, ChatColor.GOLD, ChatColor.GRAY,
                ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.GREEN, ChatColor.AQUA,
                ChatColor.RED, ChatColor.LIGHT_PURPLE, ChatColor.YELLOW, ChatColor.WHITE};

        for (ChatColor color : allColors) {
            Team team = plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("glow_" + color.name());
            team.setColor(color);
            glowTeams.put(color, team);
        }
    }

    @EventHandler
    public void onPlayerClickHead(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof CreatingInventory) {

            Player player = (Player) event.getWhoClicked();

            // Disable color head
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD
                    && event.getSlot() == 41) {


                if (multicolorTasks.containsKey(player)) {
                    // If player is glowing with multicolor mode
                    MulticolorTask task = multicolorTasks.get(player);
                    task.cancel();
                    multicolorTasks.remove(player);

                    // Remove player from all glow teams
                    for (Team team : glowTeams.values()) {
                        if (team.hasEntry(player.getName())) {
                            team.removeEntry(player.getName());
                        }
                    }

                    player.setGlowing(false);
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

                if(!player.hasPermission("fancyglow.color.rainbow")){
                    MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getNoPermissionMessage());
                    player.closeInventory();
                    return;
                }

                // Toggle multicolor mode
                toggleMulticolorGlow(player);
            }
        }
    }

    public void toggleMulticolorGlow(Player player) {
        if (multicolorTasks.containsKey(player)) {
            // If player is glowing with multicolor mode
            MulticolorTask task = multicolorTasks.get(player);
            task.cancel();
            multicolorTasks.remove(player);

            // Remove player from all glow teams
            for (Team team : glowTeams.values()) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                }
            }

            player.setGlowing(false);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            player.closeInventory();
        } else {
            // If player is not glowing with multicolor mode
            int ticks = plugin.getConfig().getInt("Rainbow_Update_Interval");

            MulticolorTask task = new MulticolorTask(plugin, player, glowTeams);
            task.runTaskTimer(plugin, 0L, ticks);
            multicolorTasks.put(player, task);
            player.setGlowing(true);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
            player.closeInventory();
        }
    }

}