package hhitt.fancyglow.commands.handler;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AvailableColorMode extends ArgumentResolver<CommandSender, String> {

    public static final String KEY = "available-colors";

    private final GlowManager glowManager;
    private final MessageHandler messageHandler;

    public AvailableColorMode(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    @Override
    protected ParseResult<String> parse(Invocation<CommandSender> invocation, Argument<String> context, String argument) {
        ChatColor color = ColorUtils.findColor(argument.toUpperCase());
        if (color == null) {
            return ParseResult.failure(messageHandler.getMessage(Messages.INVALID_COLOR));
        }

        return ParseResult.success(argument);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<String> argument, SuggestionContext context) {
        Set<String> availableColors = ColorUtils.getAvailableColorsSet();
        if (!(invocation.sender() instanceof Player)) SuggestionResult.of(availableColors);

        Player player = (Player) invocation.sender();
        List<String> list = new ArrayList<>(availableColors.size());
        for (String name : availableColors) {
            if (glowManager.hasGlowPermission(player, name)) {
                list.add(name);
            }
        }
        return SuggestionResult.of(list);
    }
}
