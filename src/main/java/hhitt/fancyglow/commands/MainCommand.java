package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.commands.lamp.ColorSuggestion;
import hhitt.fancyglow.commands.lamp.OnlinePlayersSuggestionProvider;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.SuggestWith;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.IOException;
import java.util.List;

public class MainCommand {

    private final FancyGlow plugin;
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    private CreatingInventory inventory;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
        // Avoid create a new instance per every command-execution.
        this.inventory = new CreatingInventory(plugin);
        this.inventory.setupContent();
    }

    @Command({"glow", "fancyglow"})
    @Description("Main command for FancyGlow")
    public void command(Player player) {
        // Returns if disabled so player use its own menus.
        if (!plugin.getConfiguration().getBoolean("Open_Glow_Menu")) return;

        // Prevent command usage in target worlds
        List<String> noAllowedWorlds = plugin.getConfiguration().getStringList("Disabled_Worlds");
        if (noAllowedWorlds.contains(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        // Check gui permissions
        if (!player.hasPermission("fancyglow.command.gui")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        // Prepare and open the gui
        inventory.prepareForPlayer(player);
        player.openInventory(inventory.getInventory());
    }

    @Command({"glow reload", "fancyglow reload"})
    @CommandPermission("fancyglow.command.reload")
    @Description("FancyGlow reload sub-command.")
    public void reloadCommand(BukkitCommandActor actor) {
        CommandSender sender = actor.sender();
        if (!sender.hasPermission("fancyglow.command.reload")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        glowManager.stopFlashingTask();
        glowManager.stopMulticolorTask();

        try {
            plugin.getConfiguration().reload();
        } catch (IOException e) {
            // No longer using logger as field, since this is the only place its being used.
            plugin.getLogger().severe("Unexpected exception during configuration-reload with the following message: " + e.getMessage());
        } finally {
            // Re-initialize inventory only when reloading, probably not the best way to do it.
            inventory = new CreatingInventory(plugin);
            inventory.setupContent();

            glowManager.scheduleFlashingTask();
            glowManager.scheduleMulticolorTask();

            messageHandler.sendMessage(sender, Messages.RELOADED);
        }
    }

    @Command({"glow color", "fancyglow color"})
    @Description("FancyGlow color sub-command.")
    public void colorCommand(Player player, @ColorSuggestion @Optional String colorName) {
        if (!player.hasPermission("fancyglow.command.color")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        if (colorName == null) {
            messageHandler.sendMessage(player, Messages.COLOR_COMMAND_USAGE);
            return;
        }

        // Handle rainbow mode
        if (colorName.equalsIgnoreCase("rainbow")) {
            if (!player.hasPermission("fancyglow.rainbow")) {
                messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                return;
            }

            boolean toggled = glowManager.toggleMulticolorGlow(player);
            messageHandler.sendMessage(player, toggled ? Messages.ENABLE_RAINBOW : Messages.DISABLE_RAINBOW);
            return;
        }
        // Handle flashing mode
        if (colorName.equalsIgnoreCase("flashing")) {
            if (!player.hasPermission("fancyglow.flashing")) {
                messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                return;
            }

            if (playerGlowManager.findPlayerTeam(player) == null && glowManager.isFlashingTaskActive(player)) {
                messageHandler.sendMessage(player, Messages.NOT_GLOWING);
                return;
            }

            if (!plugin.getConfiguration().getBoolean("Flash_Rainbow") && glowManager.isMulticolorTaskActive(player)) {
                messageHandler.sendMessage(player, Messages.FLASHING_WITH_RAINBOW);
                return;
            }

            boolean toggled = glowManager.toggleFlashingGlow(player);
            messageHandler.sendMessage(player, toggled ? Messages.ENABLE_FLASHING : Messages.DISABLE_FLASHING);
            return;
        }

        // Handles normal colors.
        if (ColorUtils.findColor(colorName.toUpperCase()) == null) {
            messageHandler.sendMessage(player, Messages.INVALID_COLOR);
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


    @Command({"glow disable", "fancyglow disable"})
    @Description("Allow player to disable its own glow and others if it has permissions to.")
    public void disableCommand(BukkitCommandActor actor, @Optional String targetName) {
        if (actor.isConsole() && targetName == null) {
            messageHandler.sendMessage(actor.sender(), Messages.DISABLE_COMMAND_USAGE);
            return;
        }

        if (actor.isPlayer() && targetName == null) {
            Player player = actor.asPlayer();
            // Check if the player has permission to disable their own glow
            if (!player.hasPermission("fancyglow.command.disable")) {
                messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                return;
            }

            if (!player.isGlowing() && !glowManager.isFlashingTaskActive(player)) {
                messageHandler.sendMessage(player, Messages.NOT_GLOWING);
                return;
            }

            glowManager.removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
            return;
        }

        if (targetName.equalsIgnoreCase("all")) {
            handleDisableAll(actor.sender());
            return;
        }

        disableOtherGlow(actor.sender(), targetName);
    }

    private void disableOtherGlow(CommandSender sender, String targetName) {
        if (!sender.hasPermission("fancyglow.command.disable.others")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            messageHandler.sendMessageBuilder(sender, Messages.UNKNOWN_TARGET)
                    .placeholder("%player_name%", targetName)
                    .send();
            return;
        }

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

    private void handleDisableAll(CommandSender sender) {
        // Check permissions
        if (!sender.hasPermission("fancyglow.command.disable.everyone")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        // Disable glow for all online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        });

        messageHandler.sendMessage(sender, Messages.DISABLE_GLOW_EVERYONE);
    }
}
