package hhitt.fancyglow.commands.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.command.CommandSender;

public class FancyInvalidUsageHandler implements InvalidUsageHandler<CommandSender> {

    private final MessageHandler messageHandler;

    public FancyInvalidUsageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        messageHandler.sendMessage(sender, Messages.COMMAND_MANAGER_MESSAGE);
        messageHandler.sendMessage(sender, Messages.COMMAND_MANAGER_HEADER);

        String prefix = messageHandler.getMessage(Messages.COMMAND_MANAGER_PREFIX);
        if (schematic.isOnlyFirst()) {
            sender.sendMessage(prefix + schematic.first());
            return;
        }

        for (String scheme : schematic.all()) {
            sender.sendMessage(prefix + scheme);
        }
    }
}