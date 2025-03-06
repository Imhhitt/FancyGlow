package hhitt.fancyglow.commands.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.command.CommandSender;

public class FancyMissingPermissionHandler implements MissingPermissionsHandler<CommandSender> {

    private final MessageHandler messageHandler;

    public FancyMissingPermissionHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        String permissions = missingPermissions.asJoinedText();
        CommandSender sender = invocation.sender();

        messageHandler.sendMessageBuilder(sender, Messages.NO_PERMISSION)
                .placeholder("%permission%", permissions)
                .send();
    }
}
