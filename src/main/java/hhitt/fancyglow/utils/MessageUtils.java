package hhitt.fancyglow.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.BaseComponent;

public class MessageUtils {

    //Just a method to translate MiniMessage format
    //MiniMessage is GOD!!!!
    public static BaseComponent miniMessageParse (String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }
}
