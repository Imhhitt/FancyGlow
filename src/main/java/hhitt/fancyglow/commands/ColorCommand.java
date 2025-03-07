package hhitt.fancyglow.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.argument.Key;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "glow color")
@Description("FancyGlow color sub-command.")
public class ColorCommand {

    private final FancyGlow plugin;
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    public ColorCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    @Execute
    public void colorCommand(@Context Player player, @Arg @Key("available-colors") String colorName) {
        if (glowManager.isDeniedWorld(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        ChatColor color = ColorUtils.findColor(colorName.toUpperCase());
        if (!(glowManager.hasGlowPermission(player, color) || player.hasPermission("fancyglow.all_colors"))) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        if (!glowManager.isMulticolorTaskActive(player) && playerGlowManager.getPlayerGlowColorName(player).equalsIgnoreCase(color.name())) {
            messageHandler.sendMessage(player, Messages.COLOR_ALREADY_SELECTED);
            player.closeInventory();
            return;
        }

        glowManager.setGlow(player, color);
        messageHandler.sendMessage(player, Messages.ENABLE_GLOW);
    }

    @Execute(name = "rainbow")
    @Permission("fancyglow.rainbow")
    public void colorRainbowCommand(@Context Player player) {
        if (glowManager.isDeniedWorld(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isFlashingTaskActive(player)) {
            messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
            return;
        }

        boolean toggled = glowManager.toggleMulticolorGlow(player);
        messageHandler.sendMessage(player, toggled ? Messages.ENABLE_RAINBOW : Messages.DISABLE_RAINBOW);
    }

    @Execute(name = "flashing")
    @Permission("fancyglow.flashing")
    public void colorFlashingCommand(@Context Player player) {
        if (glowManager.isDeniedWorld(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        if (playerGlowManager.findPlayerTeam(player) == null && !glowManager.isFlashingTaskActive(player)) {
            messageHandler.sendMessage(player, Messages.NOT_GLOWING);
            return;
        }

        if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isMulticolorTaskActive(player)) {
            messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
            return;
        }

        boolean toggled = glowManager.toggleFlashingGlow(player);
        messageHandler.sendMessage(player, toggled ? Messages.ENABLE_FLASHING : Messages.DISABLE_FLASHING);
    }
}
