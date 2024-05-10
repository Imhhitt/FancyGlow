package hhitt.org.example.fancyglow.Listeners;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Inventory.CreatingInventory;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
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


    private FancyGlow plugin;
    public HeadClickListener(FancyGlow plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClickHead(InventoryClickEvent event){
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof CreatingInventory) {
            // Verifica si el ítem clicado es una cabeza
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Player player = (Player) event.getWhoClicked();
                Scoreboard scoreboard = player.getScoreboard();

                for (Team team : scoreboard.getTeams()) {
                    if (team.hasEntry(player.getName())) {
                        team.removeEntry(player.getName());
                    }
                }
                player.setGlowing(false);
                player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getDisableGlow()));
                scoreboard.getTeam(player.getName());
                Team team = player.getScoreboard().getTeam(player.getName());
                player.getScoreboard().getTeam(player.getName());
                player.closeInventory();
            }
        }
    }
}
