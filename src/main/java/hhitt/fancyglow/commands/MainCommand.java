package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand implements CommandExecutor {

    private final FancyGlow plugin;
    private final GlowManager glowManager;
    private final ColorCommandLogic colorCommandLogic;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.colorCommandLogic = new ColorCommandLogic(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        List<String> noAllowedWorlds = plugin.getConfig().getStringList("Disabled_Worlds");

        //Prevent command usage in target worlds.
        if (isPlayer(sender)) {
            Player player = (Player) sender;

            //Check the world
            if (noAllowedWorlds.contains(player.getWorld().getName())) {
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisabledWorldMessage());
                return true;
            }
        }

        if (args.length == 0) {
            // Prevent open gui when in console.
            if (!isPlayer(sender)) {
                sender.sendMessage("That command can only be performed by players!");
                return true;
            }

            Player player = (Player) sender;

            //Check gui permissions.
            if (!hasPermission(player, "fancyglow.command.gui")) return true;

            //Open the gui
            player.openInventory(new CreatingInventory(plugin, player).getInventory());
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "disable":
                // Logic for /glow disable
                handleDisableCommand(sender, args);
                break;
            case "color":
                // Logic for /glow color <color>
                handleColorCommand(sender, args);
                break;
            case "reload":
                // Logic for /glow reload
                handleReloadCommand(sender);
                break;
            default:
                // Handle unknown sub-commands.
                sendMessage(sender, plugin.getConfig().getString("Messages.Invalid_Sub_Command"));
                break;
        }
        return true;
    }

    private void handleDisableCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            handleSelfDisable(sender);
        } else if (args.length >= 2) {
            handleTargetDisable(sender, args[1]);
        }
    }

    private void handleSelfDisable(CommandSender sender) {
        // Check if the sender is a player
        if (!isPlayer(sender)) {
            sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Usage"));
            return;
        }

        Player player = (Player) sender;

        // Check if the player has permission to disable their own glow
        if (!hasPermission(player, "fancyglow.command.disable")) return;

        // Check if the player is glowing
        if (!player.isGlowing()) {
            sendMessage(player, plugin.getConfig().getString("Messages.Not_Glowing"));
            return;
        }

        // Disable the player's glow
        glowManager.removeGlow(player);
        sendMessage(player, plugin.getConfig().getString("Messages.Disable_Glow"));
    }

    private void handleTargetDisable(CommandSender sender, String targetArg) {
        if (targetArg.equalsIgnoreCase("all")) {
            handleDisableAll(sender);
            return;
        }

        Player target = Bukkit.getPlayer(targetArg);

        if (target == null) {
            sendMessage(sender, plugin.getConfig().getString("Messages.Unknown_Target").replace("%player_name%", targetArg));
            return;
        }

        if (!target.isGlowing()) {
            sendMessage(sender, plugin.getConfig().getString("Messages.Target_Not_Glowing"));
            return;
        }

        glowManager.removeGlow(target);
        MessageUtils.miniMessageSender(target, plugin.getMainConfigManager().getDisableGlow());
        sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Glow_Others").replace("%player_name%", target.getName()));
    }

    private void handleDisableAll(CommandSender sender) {
        // Check permissions
        if (!hasPermission(sender, "fancyglow.command.disable.everyone") && !hasPermission(sender, "fancyglow.admin"))
            return;

        // Disable glow for all online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            glowManager.removeGlow(player);
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
        });

        sendMessage(sender, plugin.getConfig().getString("Messages.Disable_Glow_Everyone"));
    }

    private void handleColorCommand(CommandSender sender, String[] args) {
        if (!isPlayer(sender)) {
            sender.sendMessage("That command can only be performed by players!");
            return;
        }

        Player player = (Player) sender;

        if (!hasPermission(player, "fancyglow.command.color")) return;

        if (args.length == 1) {
            sendMessage(player, plugin.getConfig().getString("Messages.Wrong_Usage_Color_Command"));
            return;
        }

        colorCommandLogic.glowColorCommand(player, args[1]);
    }

    private void handleReloadCommand(CommandSender sender) {
        if (!hasPermission(sender, "fancyglow.command.reload")) return;

        plugin.getMainConfigManager().reloadConfig();
        sendMessage(sender, plugin.getMainConfigManager().getReloadConfigMessage());
    }

    // Utility Methogs

    private boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission(permission)) {
            if (sender instanceof Player) {
                MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
            } else {
                sender.sendMessage(plugin.getMainConfigManager().getNoPermissionMessage());
            }
            return false;
        }
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            MessageUtils.miniMessageSender((Player) sender, message);
        } else {
            sender.sendMessage(MessageUtils.miniMessageParse(message));
        }
    }

}
