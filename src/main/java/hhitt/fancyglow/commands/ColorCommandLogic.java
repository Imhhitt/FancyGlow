package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.GlowManager;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ColorCommandLogic implements CommandExecutor {
    private final FancyGlow plugin;
    private final GlowManager glowManager;

    public ColorCommandLogic(FancyGlow plugin, GlowManager glowManager) {
        this.plugin = plugin;
        this.glowManager = glowManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
        ChatColor color;

        if (arg.equals("rainbow")) {
            if (!(
                p.hasPermission("fancyglow.rainbow") ||
                p.hasPermission("fancyglow.all_colors")
            )) {
                MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
                return;
            }
            glowManager.toggleMulticolorGlow(p);
            return;
        } else {
            try {
                color = ChatColor.valueOf(arg.toUpperCase());
            } catch (IllegalArgumentException e) {
                MessageUtils.miniMessageSender(p, plugin.getConfig().getString("Messages.Not_Valid_Color"));
                return;
            }
        }

        if (!(glowManager.hasGlowPermission(p, color) || p.hasPermission("fancyglow.all_colors") || p.hasPermission("fancyglow.admin"))) {
            MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
        } else {
            toggleGlow(p, color);
        }
    }

    private void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = glowManager.getOrCreateTeam(color);
        if (glowTeam != null) {
            String cleanName = ChatColor.stripColor(player.getName());
            if (glowTeam.hasEntry(cleanName)) {
                glowTeam.removeEntry(cleanName);
                player.setGlowing(false);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            } else {
                glowManager.removePlayerFromAllTeams(player);
                glowTeam.addEntry(cleanName);
                player.setGlowing(true);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
            }
        }
    }
}
