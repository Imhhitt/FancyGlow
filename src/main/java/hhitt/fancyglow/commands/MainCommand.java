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

        Player player = (Player) sender;
        String actualWorld = ((Player) sender).getWorld().getName();
        List<String> noAllowedWorlds = plugin.getConfig().getStringList("Disabled_Worlds");

        //Users commands (/glow) that open the gui
        if (args == null || args.length == 0) {
            if (!sender.hasPermission("fancyglow.command.gui") && !sender.hasPermission("fancyglow.admin")) {
                MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
                return true;
            }

            //Check the world
            if (noAllowedWorlds.contains(actualWorld)) {
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisabledWorldMessage());
                return true;
            }

            //Open the gui
            CreatingInventory inventory = new CreatingInventory(plugin, (Player) sender);
            ((Player) sender).openInventory(inventory.getInventory());
            return true;
        }


        //Logic for /glow disable
        if (args[0].equalsIgnoreCase("disable") && args.length == 1) {
            if (!sender.hasPermission("fancyglow.command.disable") && !sender.hasPermission("fancyglow.admin")) {
                MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
                return true;
            }


            //If the player is not glowing, "exception" error
            if (!player.isGlowing()) {
                MessageUtils.miniMessageSender(player, plugin.getConfig().getString("Messages.Not_Glowing"));
                return true;
            }

            //If it's glowing, then disabled message
            MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            glowManager.removeGlow(player);
            return true;
        }


        //Logic for /glow color <color>
        if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("fancyglow.command.color") && !sender.hasPermission("fancyglow.admin")) {
                MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
                return true;
            }

            if (args.length == 1) {
                MessageUtils.miniMessageSender(
                        player, plugin.getConfig().getString("Messages.Wrong_Usage_Color_Command"));
                return true;
            }

            colorCommandLogic.glowColorCommand(player, args[1]);
            return true;
        }


        //Admin commands
        if (!sender.hasPermission("fancyglow.admin")) {
            MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
            return true;
        }

        // Logic for /glow reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.getMainConfigManager().reloadConfig();
            MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getReloadConfigMessage());
            return true;
        }

        // Logic for /glow disable <player>
        if (args[0].equalsIgnoreCase("disable") && args.length >= 2) {

            // Disable glow to everyone online.
            if (args[1].equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(online -> {
                    glowManager.removeGlow(online);
                    MessageUtils.miniMessageSender(online, this.plugin.getMainConfigManager().getDisableGlow());
                });

                MessageUtils.miniMessageSender((Player) sender, this.plugin.getConfig().getString("Messages.Disable_Glow_Everyone"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                //noinspection DataFlowIssue
                MessageUtils.miniMessageSender((Player) sender, this.plugin.getConfig().getString("Messages.Unknown_Target").replace("%player_name%", args[1]));
                return true;
            }

            if (!target.isGlowing()) {
                MessageUtils.miniMessageSender((Player) sender, this.plugin.getConfig().getString("Messages.Target_Not_Glowing"));
                return true;
            }

            glowManager.removeGlow(target);
            MessageUtils.miniMessageSender(target, this.plugin.getMainConfigManager().getDisableGlow());
            //noinspection DataFlowIssue
            MessageUtils.miniMessageSender((Player) sender, this.plugin.getConfig().getString("Messages.Disable_Glow_Others").replace("%player_name%", target.getName()));
            return true;
        }
        return true;
    }
}
