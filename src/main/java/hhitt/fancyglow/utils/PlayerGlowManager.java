package hhitt.fancyglow.utils;

import hhitt.fancyglow.FancyGlow;
import org.bukkit.entity.Player;

public class PlayerGlowManager {

    private final FancyGlow plugin;

    public PlayerGlowManager(FancyGlow plugin) {
        this.plugin = plugin;
    }

    // Used to manage the placeholder of status at gui

    public String getPlayerGlowingStatus(Player player) {
        if (player.isGlowing()) {
            return MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGlowStatusTrue());
        } else {
            return MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGlowStatusFalse());
        }
    }
}
