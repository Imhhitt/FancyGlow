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
import java.util.logging.Logger;

public class MainCommand {
    private final FancyGlow plugin;
    private final Logger logger;
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;
    private CreatingInventory inventory;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
        this.inventory = new CreatingInventory(plugin);
        this.inventory.setupContent();
    }

    @Command({"glow", "fancyglow"})
    @Description("Main command for FancyGlow")
    public void command(Player player) {
        // Prevent command usage in target worlds
        List<String> noAllowedWorlds = plugin.getConfiguration().getStringList("Disabled_Worlds");
        if (noAllowedWorlds.contains(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        // Returns if disabled so player uses their own menus.
        if (!plugin.getConfiguration().getBoolean("Open_Glow_Menu")) return;

        // Check GUI permissions
        if (!player.hasPermission("fancyglow.command.gui")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        // Prepare and open the GUI
        inventory.prepareForPlayer(player);
        player.openInventory(inventory.getInventory());
    }

    @Command({"glow reload", "fancyglow reload"})
    @CommandPermission("fancyglow.command.reload")
    @Description("Reload FancyGlow configuration.")
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
            logger.severe("Unexpected exception during configuration reload: " + e.getMessage());
        } finally {
            // Re-initialize inventory only when reloading
            inventory = new CreatingInventory(plugin);
            inventory.setupContent();

            glowManager.scheduleFlashingTask();
            glowManager.scheduleMulticolorTask();

            messageHandler.sendMessage(sender, Messages.RELOADED);
        }
    }

    @Command({"glow color", "fancyglow color"})
    @CommandPermission("fancyglow.command.color")
    @Description("Change FancyGlow color.")
    public void colorCommand(BukkitCommandActor actor, @ColorSuggestion @Optional String arg) {
        if (actor.isConsole()) {
            actor.sender().sendMessage("That command can only be performed by players!");
            return;
        }

        Player player = actor.asPlayer();
        if (!player.hasPermission("fancyglow.command.color")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        if (arg == null) {
            messageHandler.sendMessage(player, Messages.COLOR_COMMAND_USAGE);
            return;
        }

        if (arg.equalsIgnoreCase("rainbow")) {
            if (!player.hasPermission("fancyglow.rainbow")) {
                messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                return;
            }
            glowManager.toggleMulticolorGlow(player);
            return;
        }
        
        if (arg.equalsIgnoreCase("flashing")) {
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

            glowManager.toggleFlashingGlow(player);
            return;
        }

        if (ColorUtils.findColor(arg.toUpperCase()) == null) {
            messageHandler.sendMessage(player, Messages.INVALID_COLOR);
            return;
        }

        ChatColor color = ColorUtils.findColor(arg.toUpperCase());
        if (!(glowManager.hasGlowPermission(player, color) || player.hasPermission("fancyglow.all_colors"))) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        glowManager.toggleGlow(player, color);
    }

    @Command({"glow disable", "fancyglow disable"})
    @Description("Allow player to disable their own glow.")
    @CommandPermission("fancyglow.command.disable")
    public void disableCommand(BukkitCommandActor actor) {
        if (actor.isConsole()) {
            messageHandler.sendMessage(actor.sender(), Messages.DISABLE_COMMAND_USAGE);
            return;
        }

        Player player = actor.asPlayer();
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
    }

    @Command({"glow disable", "fancyglow disable"})
    @Description("Allow console or staff member to disable player glow.")
    @CommandPermission("fancyglow.command.disable.others")
    public void disableCommand(BukkitCommandActor actor, @SuggestWith(OnlinePlayersSuggestionProvider.class) String targetName) {
        CommandSender sender = actor.sender();

        if (targetName.equalsIgnoreCase("all")) {
            handleDisableAll(sender);
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
        if (!sender.hasPermission("fancyglow.command.disable.everyone") && !sender.hasPermission("fancyglow.admin")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        });

        messageHandler.sendMessage(sender, Messages.DISABLE_GLOW_EVERYONE);
    }
}
