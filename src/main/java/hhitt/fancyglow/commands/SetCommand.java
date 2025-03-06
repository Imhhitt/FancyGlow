package hhitt.fancyglow.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.argument.Key;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.literal.Literal;
import dev.rollczi.litecommands.annotations.permission.Permission;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "glow set")
@Permission("fancyglow.command.set")
@Description("FancyGlow set sub-command, applies colors to target.")
public class SetCommand {

    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    public SetCommand(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @Execute
    @Permission("fancyglow.command.set")
    public void setCommand(@Context CommandSender sender, @Arg Player target, @Arg @Key("available-colors") String colorName, @Flag(value = "silent") boolean silent) {
        glowManager.setGlow(target, ColorUtils.findColor(colorName));
        if (!silent) {
            messageHandler.sendMessage(target, Messages.ENABLE_GLOW);
            messageHandler.sendManualMessage(sender, "You have applied the glow color " + colorName + " to " + target.getName());
        }
    }

    @Execute
    @Permission("fancyglow.command.set")
    public void setFlashingCommand(@Context CommandSender sender, @Arg Player target, @Literal("flashing") String literal, @Flag(value = "silent") boolean silent) {
        if (playerGlowManager.findPlayerTeam(target) == null) {
            messageHandler.sendManualMessage(sender, "Target needs to have a color first!");
            return;
        }

        boolean toggled = glowManager.toggleFlashingGlow(target);
        if (!silent) {
            messageHandler.sendMessage(target, toggled ? Messages.ENABLE_FLASHING : Messages.DISABLE_FLASHING);
            messageHandler.sendManualMessage(sender, "You have " + (toggled ? "enabled" : "disabled") + " player " + target.getName() + " flashing mode.");
        }
    }

    @Execute
    @Permission("fancyglow.command.set")
    public void setRainbowCommand(@Context CommandSender sender, @Arg Player target, @Literal("rainbow") String literal, @Flag(value = "silent") boolean silent) {
        boolean toggled = glowManager.toggleMulticolorGlow(target);
        if (!silent) {
            messageHandler.sendMessage(target, toggled ? Messages.ENABLE_RAINBOW : Messages.DISABLE_RAINBOW);
            messageHandler.sendManualMessage(sender, "You have " + (toggled ? "enabled" : "disabled") + " player " + target.getName() + " rainbow mode.");
        }
    }
}
