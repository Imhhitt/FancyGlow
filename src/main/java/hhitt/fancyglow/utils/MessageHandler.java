package hhitt.fancyglow.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandler {

    private static final String DEFAULT_MESSAGE_NOT_FOUND = "Message not found in path %s";

    private final YamlDocument messages;
    private final boolean isPlaceholderAPIEnabled;

    public MessageHandler(FancyGlow plugin, YamlDocument messages) {
        this.messages = messages;
        this.isPlaceholderAPIEnabled = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public MessageBuilder sendMessageBuilder(CommandSender sender, Messages message) {
        return new MessageBuilder(sender, message);
    }

    /**
     * Handle message placeholders if plugin is enabled.
     *
     * @param sender  Command sender.
     * @param message Message with placeholders.
     *
     * @return Message with parsed placeholders from PlaceholderAPI.
     */
    private String handlePlaceholders(CommandSender sender, String message) {
        if (isPlaceholderAPIEnabled && sender instanceof Player) {
            message = PlaceholderAPI.setPlaceholders((Player) sender, message);
        }
        return message;
    }

    /**
     * Formats the message by applying placeholders and colors.
     *
     * @param sender  Command sender.
     * @param message Message to format.
     *
     * @return Formatted message with placeholders and colors applied.
     */
    public String intercept(CommandSender sender, String message) {
        message = handlePlaceholders(sender, message);
        return MessageUtils.miniMessageParse(message);
    }

    /**
     * Gets raw message from the config.
     *
     * @param path Config path to search.
     *
     * @return Raw message at config or a default message if not found.
     */
    public String getRawMessage(String path) {
        return messages.getString(path, String.format(DEFAULT_MESSAGE_NOT_FOUND, path));
    }

    /**
     * Gets a formatted message using the Messages enum.
     *
     * @param message Message enum to search.
     *
     * @return Formatted message with colors applied.
     */
    public String getMessage(Messages message) {
        return MessageUtils.miniMessageParse(messages.getString(message.getPath()));
    }

    /**
     * Gets a formatted message using the Messages enum and applies placeholders.
     *
     * @param sender  Command sender.
     * @param message Message enum to search.
     *
     * @return Formatted message with placeholders and colors applied.
     */
    public String getMessage(CommandSender sender, Messages message) {
        return intercept(sender, getRawMessage(message.getPath()));
    }

    /**
     * Gets a formatted message using the message path and applies placeholders.
     *
     * @param sender Command sender.
     * @param path   Message path to search.
     *
     * @return Formatted message with placeholders and colors applied.
     */
    public String getMessage(CommandSender sender, String path) {
        return intercept(sender, getRawMessage(path));
    }

    /**
     * Gets a list of formatted messages using the Messages enum.
     *
     * @param message Message enum to search.
     *
     * @return List of formatted messages with colors applied.
     */
    public List<String> getMessages(Messages message) {
        return getMessages(message.getPath());
    }

    /**
     * Gets a list of formatted messages using the message path.
     *
     * @param path Message path to search.
     *
     * @return List of formatted messages with colors applied.
     */
    public List<String> getMessages(String path) {
        List<String> messages = new ArrayList<>();
        for (String rawMessage : getRawStringList(path)) {
            messages.add(MessageUtils.miniMessageParse(rawMessage));
        }
        return messages;
    }

    /**
     * Gets a list of formatted messages using the Messages enum and applies placeholders.
     *
     * @param sender  Command sender.
     * @param message Message enum to search.
     *
     * @return List of formatted messages with placeholders and colors applied.
     */
    public List<String> getMessages(CommandSender sender, Messages message) {
        return getMessages(sender, message.getPath());
    }

    /**
     * Gets a list of formatted messages using the message path and applies placeholders.
     *
     * @param sender Command sender.
     * @param path   Message path to search.
     *
     * @return List of formatted messages with placeholders and colors applied.
     */
    public List<String> getMessages(CommandSender sender, String path) {
        List<String> messages = new ArrayList<>();
        for (final String rawMessage : this.messages.isList(path)
                ? this.messages.getStringList(path)
                : Collections.singletonList(getRawMessage(path))
        ) {
            messages.add(intercept(sender, rawMessage));
        }
        return messages;
    }

    /**
     * Sends a formatted message to the command sender.
     *
     * @param sender  Command sender.
     * @param message Message to send.
     */
    public void sendManualMessage(CommandSender sender, String message) {
        if (!message.isEmpty() && !message.isBlank()) {
            if (sender instanceof Player) {
                MessageUtils.miniMessageSender((Player) sender, handlePlaceholders(sender, message));
            } else {
                sender.sendMessage(intercept(sender, message));
            }
        }
    }

    /**
     * Sends a formatted message to the command sender using the Messages enum.
     *
     * @param sender  Command sender.
     * @param message Message enum to search.
     */
    public void sendMessage(CommandSender sender, Messages message) {
        sendMessage(sender, message.getPath());
    }

    /**
     * Sends a formatted message to the command sender using the message path.
     *
     * @param sender Command sender.
     * @param path   Message path to search.
     */
    public void sendMessage(CommandSender sender, String path) {
        sendManualMessage(sender, getRawMessage(path));
    }

    /**
     * Gets a raw list of messages from the config.
     *
     * @param path Message path to search.
     *
     * @return List of raw messages.
     */
    public List<String> getRawStringList(String path) {
        return messages.isList(path) ? messages.getStringList(path) : Collections.singletonList(getRawMessage(path));
    }

    /**
     * Sends a list of formatted messages to the command sender using the Messages enum.
     *
     * @param sender  Command sender.
     * @param message Message enum to search.
     */
    public void sendListMessage(CommandSender sender, Messages message) {
        sendListMessage(sender, message.getPath());
    }

    /**
     * Sends a list of formatted messages to the command sender using the message path.
     *
     * @param sender Command sender.
     * @param path   Message path to search.
     */
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
            sendManualMessage(sender, parse());
        }

        public String parse() {
            return applyPlaceholders(getRawMessage(message.getPath()), placeholders);
        }

        public List<String> parseList() {
            List<String> list = new ArrayList<>();
            for (String line : getRawStringList(message.getPath())) {
                list.add(applyPlaceholders(line, placeholders));
            }
            return list;
        }

        public void sendList() {
            parseList().forEach(message -> sendManualMessage(sender, message));
        }

        private String applyPlaceholders(String message, Map<String, String> placeholders) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
    }
}