package hhitt.fancyglow.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageHandler {

    private final FancyGlow plugin;
    private final YamlDocument messages;

    public MessageHandler(FancyGlow plugin, YamlDocument messages) {
        this.plugin = plugin;
        this.messages = messages;
    }

    public MessageBuilder sendMessageBuilder(CommandSender sender, Messages message) {
        return new MessageBuilder(sender, message);
    }

    /**
     * Handle message placeholders if plugin is enabled
     *
     * @param sender  Command sender.
     * @param message Message with placeholders.
     *
     * @return Message with parsed placeholders from placeholder api.
     */
    private String handlePAPIPlaceholders(CommandSender sender, String message) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && sender instanceof Player) {
            message = PlaceholderAPI.setPlaceholders((Player) sender, message);
        }
        return message;
    }

    /**
     * Intercepts command sender message and apply colors with mini message.
     *
     * @param sender  Command sender.
     * @param message Message to intercept.
     *
     * @return Message with parsed placeholders and color applied.
     */
    public String intercept(CommandSender sender, String message) {
        message = handlePAPIPlaceholders(sender, message);

        return MessageUtils.miniMessageParse(message);
    }

    /**
     * Gets raw message from the config.
     *
     * @param path Config path to search to.
     *
     * @return Raw message at config or a message if not found.
     */
    public String getRawMessage(String path) {
        if (messages.isList(path) && !messages.getStringList(path).isEmpty()) {
            return String.join(" ", messages.getStringList(path));
        }
        return messages.getString(path, String.format("Message not found in path %s", path));
    }

    /**
     * @param message Message to get.
     *
     * @return Message with color applied using {@link hhitt.fancyglow.utils.Messages}.
     */
    public String getMessage(Messages message) {
        return MessageUtils.miniMessageParse(messages.getString(message.getPath()));
    }

    /**
     * @param sender   Command sender.
     * @param messages Message enum to search.
     *
     * @return Message with parsed placeholders and color applied using {@link hhitt.fancyglow.utils.Messages} enum.
     */
    public String getMessage(CommandSender sender, Messages messages) {
        return intercept(sender, getRawMessage(messages.getPath()));
    }

    /**
     * @param sender Command sender.
     * @param path   Message path to search.
     *
     * @return Message with parsed placeholders and color applied using message path.
     */
    public String getMessage(CommandSender sender, String path) {
        return intercept(sender, getRawMessage(path));
    }

    /**
     * @param message Message enum to search.
     *
     * @return Messages list with color applied.
     */
    public List<String> getMessages(Messages message) {
        List<String> rawMessages = messages.isList(message.getPath()) ? messages.getStringList(message.getPath()) : Collections.singletonList(getRawMessage(message.getPath()));
        return rawMessages.stream()
                .map(MessageUtils::miniMessageParse)
                .collect(Collectors.toList());
    }

    /**
     * @param sender  Command sender.
     * @param message Message enum to search.
     *
     * @return Messages list with placeholder parsed and color applied using {@link hhitt.fancyglow.utils.Messages} enum.
     */
    public List<String> getMessages(CommandSender sender, Messages message) {
        return getMessages(sender, message.getPath());
    }

    /**
     * @param sender Command sender.
     * @param path   Message path to search.
     *
     * @return Messages list with placeholder parsed and color applied using message path.
     */
    public List<String> getMessages(CommandSender sender, String path) {
        List<String> rawMessages = messages.isList(path) ? messages.getStringList(path) : Collections.singletonList(getRawMessage(path));
        return rawMessages.stream()
                .map(message -> intercept(sender, message))
                .collect(Collectors.toList());
    }

    /**
     * Manually sends a message to the command sender intercepted with {@link hhitt.fancyglow.utils.MessageHandler#intercept}.
     *
     * @param sender  Command sender
     * @param message Message to send.
     *
     * @see hhitt.fancyglow.utils.MessageHandler#sendMessage(CommandSender, String)
     * @see hhitt.fancyglow.utils.MessageHandler#sendMessage(CommandSender, Messages)
     */
    private void sendManualMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            message = handlePAPIPlaceholders(sender, message);

            MessageUtils.miniMessageSender((Player) sender, message);
            return;
        }

        sender.sendMessage(intercept(sender, message));
    }

    /**
     * Sends a message to the command sender using {@link hhitt.fancyglow.utils.Messages} enum.
     *
     * @param sender  Command sender
     * @param message Message to search.
     *
     * @see hhitt.fancyglow.utils.MessageHandler#sendManualMessage
     * @see hhitt.fancyglow.utils.MessageHandler#sendMessage(CommandSender, String)
     */
    public void sendMessage(CommandSender sender, Messages message) {
        sendMessage(sender, message.getPath());
    }

    /**
     * Sends a message to the command sender using message path.
     *
     * @param sender Command sender
     * @param path   Message path to search.
     *
     * @see hhitt.fancyglow.utils.MessageHandler#sendManualMessage(CommandSender, String)
     * @see hhitt.fancyglow.utils.MessageHandler#sendMessage(CommandSender, Messages)
     */
    public void sendMessage(CommandSender sender, String path) {
        sendManualMessage(sender, getRawMessage(path));
    }

    public List<String> getRawStringList(String path) {
        return messages.isList(path) ? messages.getStringList(path) : Collections.singletonList(getRawMessage(path));
    }

    public void sendListMessage(CommandSender sender, Messages messages) {
        sendListMessage(sender, messages.getPath());
    }

    public void sendListMessage(CommandSender sender, String path) {
        getMessages(sender, path).forEach(string -> sendManualMessage(sender, string));
    }

    public class MessageBuilder {
        private final CommandSender sender;
        private final Messages message;
        private final Map<String, String> placeholders = new HashMap<>();

        public MessageBuilder(CommandSender sender, Messages message) {
            this.sender = sender;
            this.message = message;
        }

        /**
         * Adds a placeholder and its replacement value.
         *
         * @param placeholder Placeholder to replace (e.g., %player%).
         * @param value       Value to replace the placeholder with.
         *
         * @return The current builder instance.
         */
        public MessageBuilder placeholder(String placeholder, String value) {
            placeholders.put(placeholder, value);
            return this;
        }

        /**
         * Sends the message to the CommandSender with all placeholders applied.
         */
        public void send() {
            String rawMessage = getRawMessage(message.getPath());
            String parsedMessage = applyPlaceholders(rawMessage, placeholders);
            String finalMessage = intercept(sender, parsedMessage);
            sendManualMessage(sender, finalMessage);
        }

        private String applyPlaceholders(String message, Map<String, String> placeholders) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
    }
}
