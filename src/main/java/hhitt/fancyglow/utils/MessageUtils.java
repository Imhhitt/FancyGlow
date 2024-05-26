package hhitt.fancyglow.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageUtils {

    private static BukkitAudiences adventure;

    public static void setAdventure(BukkitAudiences adventureInstance) {
        adventure = adventureInstance;
    }

    public static @NotNull String miniMessageParse(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacySection().serialize(text);
    }

    public static void miniMessageSender(Player player, String message) {
        if (adventure == null) {
            throw new IllegalStateException("BukkitAudiences is not initialized.");
        }
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        adventure.player(player).sendMessage(text);
    }
}
