package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ColorCommandLogic implements CommandExecutor {

    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public ColorCommandLogic(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                glowColorCommand(player, args[0]);
            } else {
                MessageUtils.miniMessageSender(player, plugin.getConfig().getString("Messages.Not_Valid_Color"));
            }
        }
        return true;
    }

    public void glowColorCommand(Player p, String arg) {
        ChatColor color = ColorUtils.findColor(arg.toUpperCase());

        if (arg.equalsIgnoreCase("rainbow")) {
            if (!(
                    p.hasPermission("fancyglow.rainbow") ||
                            p.hasPermission("fancyglow.all_colors")
            )) {
                MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
                return;
            }
            glowManager.toggleMulticolorGlow(p);
            return;
        }

        if (color == null) {
            MessageUtils.miniMessageSender(p, plugin.getConfig().getString("Messages.Not_Valid_Color"));
            return;
        }

        if (!(glowManager.hasGlowPermission(p, color) || p.hasPermission("fancyglow.all_colors") || p.hasPermission("fancyglow.admin"))) {
            MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
            return;
        }

        glowManager.toggleGlow(p, color);
    }
}
