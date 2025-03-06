package hhitt.fancyglow.commands.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import hhitt.fancyglow.utils.MessageHandler;
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

        //TODO: Use own messages.
        if (schematic.isOnlyFirst()) {
            sender.sendMessage(schematic.first());
            return;
        }

        for (String scheme : schematic.all()) {
            sender.sendMessage(scheme);
        }
    }
}