package hhitt.org.example.fancyglow.Listeners;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Inventory.CreatingInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.net.http.WebSocket;

public class HeadClickListener implements Listener {

    public HeadClickListener(FancyGlow plugin) {
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
                player.closeInventory();
            }
        }
    }
}
