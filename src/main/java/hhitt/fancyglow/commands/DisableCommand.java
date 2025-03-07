package hhitt.fancyglow.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "glow disable")
@Permission("fancyglow.command.disable")
@Description("Allow player to disable its own glow and others if it has permissions to.")
public class DisableCommand {

    private final GlowManager glowManager;
    private final MessageHandler messageHandler;

    public DisableCommand(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    @Execute
    @Permission("fancyglow.command.disable")
    public void disableCommand(@Context Player player) {
        if (!player.isGlowing() && !glowManager.isFlashingTaskActive(player)) {
            messageHandler.sendMessage(player, Messages.NOT_GLOWING);
            return;
        }

        glowManager.removeGlow(player);
        messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
    }

    @Execute(name = "all")
    @Permission("fancyglow.command.disable.everyone")
    public void disableEveryoneCommand(@Context CommandSender sender) {

        // Disable glow for all online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        });

        messageHandler.sendMessage(sender, Messages.DISABLE_GLOW_EVERYONE);
    }

    @Execute
    @Permission("fancyglow.command.disable.others")
    public void disableOtherCommand(@Context CommandSender sender, @Arg Player target) {
        if (!target.isGlowing()) {
            messageHandler.sendMessage(sender, Messages.TARGET_NOT_GLOWING);
            return;
        }

        glowManager.removeGlow(target);
        messageHandler.sendMessage(target, Messages.DISABLE_GLOW);
        messageHandler.sendMessageBuilder(sender, Messages.DISABLE_GLOW_OTHERS)
                .placeholder("%player_name%", target.getName())
                .send();
    }
}
