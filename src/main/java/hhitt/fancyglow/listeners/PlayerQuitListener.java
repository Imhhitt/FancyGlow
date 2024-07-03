package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.GlowManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public PlayerQuitListener(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
    }

    // Actions of persistant mode
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (!plugin.getConfig().getBoolean("Persistent-Mode")) {
            glowManager.removePlayerFromAllTeams(e.getPlayer());
        }
    }
}
