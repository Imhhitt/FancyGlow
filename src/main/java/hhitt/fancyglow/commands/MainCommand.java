package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.commands.lamp.ColorSuggestion;
import hhitt.fancyglow.commands.lamp.OnlinePlayersSuggestionProvider;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
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

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    @Command({"glow", "fancyglow"})
    @Description("Main command for FancyGlow")
    public void command(BukkitCommandActor actor) {
        CommandSender sender = actor.sender();

        if (actor.isConsole()) {
            //TODO: Console command usages.
            return;
        }

        // Prevent command usage in target worlds
        List<String> noAllowedWorlds = plugin.getConfig().getStringList("Disabled_Worlds");
        if (actor.isPlayer()) {
            Player player = (Player) sender;

            // Check the world
            if (noAllowedWorlds.contains(player.getWorld().getName())) {
                messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            }
        }

        // Check gui permissions
        Player player = (Player) sender;
        if (!player.hasPermission("fancyglow.command.gui")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        // Open the gui
        player.openInventory(new CreatingInventory(plugin, player).getInventory());
    }

    @Command({"glow reload", "fancyglow reload"})
    @CommandPermission("fancyglow.command.reload")
    @Description("FancyGlow reload sub-command.")
    public void reloadCommand(BukkitCommandActor actor) {
        CommandSender sender = actor.sender();
        if (sender.hasPermission("fancyglow.command.reload")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        try {
            plugin.getConfiguration().reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            messageHandler.sendMessage(sender, Messages.RELOADED);
        }
    }

    @Command({"glow color", "fancyglow color"})
    @CommandPermission("fancyglow.command.color")
    @Description("FancyGlow color sub-command, also opens a GUI if no argument.")
    public void colorCommand(BukkitCommandActor actor, @ColorSuggestion @Optional String arg) {
        if (actor.isConsole()) {
            // TODO: Allow console to change player glowing color.
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

        ChatColor color = ColorUtils.findColor(arg.toUpperCase());

        if (arg.equalsIgnoreCase("rainbow")) {
            if (!(
                    player.hasPermission("fancyglow.rainbow") ||
                            player.hasPermission("fancyglow.all_colors")
            )) {
                messageHandler.sendMessage(player, Messages.NO_PERMISSION);
                return;
            }
            glowManager.toggleMulticolorGlow(player);
            return;
        }

        if (color == null) {
            messageHandler.sendMessage(player, Messages.INVALID_COLOR);
            return;
        }

        if (!(glowManager.hasGlowPermission(player, color) || player.hasPermission("fancyglow.all_colors") || player.hasPermission("fancyglow.admin"))) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        glowManager.toggleGlow(player, color);
    }

    @Command({"glow disable", "fancyglow disable"})
    @Description("Allow player to disable its own glow.")
    @CommandPermission("fancyglow.command.disable")
    public void disableCommand(BukkitCommandActor actor) {
        CommandSender sender = actor.sender();

        if (actor.isConsole()) {
            messageHandler.sendMessage(sender, Messages.DISABLE_COMMAND_USAGE);
            return;
        }

        // Check if the player has permission to disable their own glow
        Player player = (Player) sender;
        if (!player.hasPermission("fancyglow.command.disable")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        // Check if the player is glowing
        if (!player.isGlowing()) {
            messageHandler.sendMessage(player, Messages.NOT_GLOWING);
            return;
        }

        // Disable the player's glow
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
            messageHandler.sendMessage(sender, Messages.DISABLE_GLOW);
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
        if (!sender.hasPermission("fancyglow.command.disable.everyone") && !sender.hasPermission("fancyglow.admin")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
        }

        // Disable glow for all online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            messageHandler.sendMessage(player, Messages.DISABLE_GLOW);
        });

        messageHandler.sendMessage(sender, Messages.DISABLE_GLOW_EVERYONE);
    }

}
