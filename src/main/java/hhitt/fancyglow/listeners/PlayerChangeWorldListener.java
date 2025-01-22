package hhitt.fancyglow.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class PlayerChangeWorldListener implements Listener {

    private final YamlDocument config;
    private final MessageHandler messageHandler;

    public PlayerChangeWorldListener(FancyGlow plugin) {
        this.config = plugin.getConfiguration();
        this.messageHandler = plugin.getMessageHandler();
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        String actualWorld = player.getWorld().getName();

        List<String> noAllowedWorlds = config.getStringList("Disabled_Worlds");
        if (noAllowedWorlds.contains(actualWorld)) {
            player.setGlowing(false);
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
        }
    }
}
