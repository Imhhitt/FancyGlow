package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.commands.lamp.ColorSuggestion;
import hhitt.fancyglow.commands.lamp.OnlinePlayersSuggestionProvider;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageUtils;
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

import java.util.List;

public class MainCommand {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
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
                sendMessage(player, plugin.getMainConfigManager().getDisabledWorldMessage());
            }
        }

        // Check gui permissions
        Player player = (Player) sender;
        if (!player.hasPermission("fancyglow.command.gui")) {
            sendMessage(player, plugin.getMainConfigManager().getNoPermissionMessage());
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
            sendMessage(actor.sender(), plugin.getMainConfigManager().getNoPermissionMessage());
            return;
        }

        plugin.getMainConfigManager().reloadConfig();
        sendMessage(actor.sender(), plugin.getMainConfigManager().getReloadConfigMessage());
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
            sendMessage(player, plugin.getMainConfigManager().getNoPermissionMessage());
            return;
        }

        if (arg == null) {
            sendMessage(player, plugin.getConfig().getString("Messages.Color_Command_Usage"));
            return;
        }

        ChatColor color = ColorUtils.findColor(arg.toUpperCase());

        if (arg.equalsIgnoreCase("rainbow")) {
            if (!(
                    player.hasPermission("fancyglow.rainbow") ||
                            player.hasPermission("fancyglow.all_colors")
            )) {
                sendMessage(player, plugin.getMainConfigManager().getNoPermissionMessage());
                return;
            }
            glowManager.toggleMulticolorGlow(player);
            return;
        }

        if (color == null) {
            sendMessage(player, plugin.getConfig().getString("Messages.Not_Valid_Color"));
            return;
        }

        if (!(glowManager.hasGlowPermission(player, color) || player.hasPermission("fancyglow.all_colors") || player.hasPermission("fancyglow.admin"))) {
            sendMessage(player, plugin.getMainConfigManager().getNoPermissionMessage());
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
            sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Usage"));
            return;
        }

        // Check if the player has permission to disable their own glow
        Player player = (Player) sender;
        if (!player.hasPermission("fancyglow.command.disable")) {
            sendMessage(sender, plugin.getMainConfigManager().getNoPermissionMessage());
            return;
        }

        // Check if the player is glowing
        if (!player.isGlowing()) {
            sendMessage(player, plugin.getConfig().getString("Messages.Not_Glowing"));
            return;
        }

        // Disable the player's glow
        glowManager.removeGlow(player);
        sendMessage(player, plugin.getConfig().getString("Messages.Disable_Glow"));
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
            sendMessage(sender, plugin.getConfig().getString("Messages.Unknown_Target").replace("%player_name%", targetName));
            return;
        }

        if (!target.isGlowing()) {
            sendMessage(sender, plugin.getConfig().getString("Messages.Target_Not_Glowing"));
            return;
        }

        glowManager.removeGlow(target);
        sendMessage(target, plugin.getMainConfigManager().getDisableGlow());
        sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Glow_Others").replace("%player_name%", target.getName()));
    }

    private void handleDisableAll(CommandSender sender) {
        // Check permissions
        if (!sender.hasPermission("fancyglow.command.disable.everyone") && !sender.hasPermission("fancyglow.admin")) {
            sendMessage(sender, plugin.getMainConfigManager().getNoPermissionMessage());
        }

        // Disable glow for all online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            sendMessage(player, plugin.getMainConfigManager().getDisableGlow());
        });

        sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Glow_Everyone"));
    }

    private void sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            MessageUtils.miniMessageSender((Player) sender, message);
        } else {
            sender.sendMessage(MessageUtils.miniMessageParse(message));
        }
    }

}
