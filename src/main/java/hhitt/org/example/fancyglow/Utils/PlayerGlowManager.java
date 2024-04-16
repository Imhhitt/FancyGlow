package hhitt.org.example.fancyglow.Utils;

import hhitt.org.example.fancyglow.FancyGlow;
import org.bukkit.entity.Player;

public class PlayerGlowManager {

    private final FancyGlow plugin;

    public PlayerGlowManager(FancyGlow plugin) {
        this.plugin = plugin;
    }

    public String getPlayerGlowingStatus(Player player) {
        if (player.isGlowing()) {
            return MessageUtils.getColoredMessages(plugin.getMainConfigManager().getGlowStatusTrue());
        } else {
            return MessageUtils.getColoredMessages(plugin.getMainConfigManager().getGlowStatusFalse());
        }
    }
}
