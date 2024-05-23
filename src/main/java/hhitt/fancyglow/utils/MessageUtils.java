package hhitt.fancyglow.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageUtils {

    private static BukkitAudiences adventure;

    public static void setAdventure(BukkitAudiences adventure) {
        MessageUtils.adventure = adventure;
    }

    public static @NotNull String miniMessageParse(String message){
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacyAmpersand().serialize(text);
    }

    //Just a method to translate MiniMessage format
    //MiniMessage is GOD!!
    public static void miniMessageSender(Player player, String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        adventure.player(player).sendMessage(text);

    }
}
