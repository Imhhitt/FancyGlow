package hhitt.fancyglow.listeners;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import org.bukkit.entity.Player;
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

    // Actions of persistent mode
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Remove from teams if persistent mode is disabled or player is in rainbow mode
        if (!plugin.getConfiguration().getBoolean("Persistent_Mode") || glowManager.isMulticolorTaskActive(player)) {
            glowManager.removeGlow(player);
        }
    }
}
