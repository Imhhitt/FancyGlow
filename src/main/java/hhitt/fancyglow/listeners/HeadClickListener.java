package hhitt.fancyglow.listeners;

import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


public class HeadClickListener implements Listener {

    // Head on gui logic


    private final FancyGlow plugin;
    public HeadClickListener(FancyGlow plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClickHead(InventoryClickEvent event){
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof CreatingInventory) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Player player = (Player) event.getWhoClicked();

                Scoreboard scoreboard = player.getScoreboard();

                scoreboard.getTeams().stream()
                        .filter(team -> team.hasEntry(player.getName()))
                        .forEach(team -> team.removeEntry(player.getName()));

                String messageKey = player.isGlowing() ? plugin.getMainConfigManager().getDisableGlow()
                        : plugin.getConfig().getString("Messages.Not_Glowing");

                player.sendMessage(MessageUtils.miniMessageParse(messageKey));

                player.setGlowing(false);

                player.closeInventory();
            }
        }
    }
}
