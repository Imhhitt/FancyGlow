package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateJoinLitener implements Listener {

    private final FancyGlow plugin;

    public UpdateJoinLitener(FancyGlow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void updateJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Return if option is disabled.
        if (!plugin.getConfiguration().getBoolean("Notify_Updates")) return;
        // Return if player doesn't have permissions.
        if (!player.hasPermission("fancyglow.update")) return;

        UpdateChecker.init(plugin, 116326).requestUpdateCheck().whenComplete((result, exception) -> {
            if (result.requiresUpdate()) {
                plugin.getLogger().info(String.format("There is a new update available! FancyGlow %s may be downloaded on SpigotMC", result.getNewestVersion()));
                plugin.getLogger().info("Download it at https://www.spigotmc.org/resources/116326/updates");
            }
        });
    }
}
