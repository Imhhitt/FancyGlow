package hhitt.fancyglow.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageUtils {

    private static BukkitAudiences adventure;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacySection();

    public static Component parse(String message) {
        return MINI_MESSAGE.deserialize(message);
    }

    public static @NotNull String miniMessageParse(String message) {
        return LEGACY_COMPONENT_SERIALIZER.serialize(MINI_MESSAGE.deserialize(message));
    }

    public static void miniMessageSender(Player player, String message) {
        adventure.player(player).sendMessage(parse(message));
    }

    public static void setAdventure(BukkitAudiences adventure) {
        MessageUtils.adventure = adventure;
    }
}
