package hhitt.fancyglow.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorldListener implements Listener {

    private final YamlDocument config;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    public PlayerChangeWorldListener(FancyGlow plugin) {
        this.config = plugin.getConfiguration();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        String worldName = player.getWorld().getName();
        if (config.getStringList("Disabled_Worlds").contains(worldName) && !playerGlowManager.getPlayerGlowingMode(player).equalsIgnoreCase("NONE")) {
            player.setGlowing(false);
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
        }
    }
}
