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
        ChatColor colorOfArg;
        switch (arg) {
            case "dark_red":
                colorOfArg = ChatColor.DARK_RED;
                break;
            case "red":
                colorOfArg = ChatColor.RED;
                break;
            case "gold":
                colorOfArg = ChatColor.GOLD;
                break;
            case "yellow":
                colorOfArg = ChatColor.YELLOW;
                break;
            case "dark_green":
                colorOfArg = ChatColor.DARK_GREEN;
                break;
            case "green":
                colorOfArg = ChatColor.GREEN;
                break;
            case "aqua":
                colorOfArg = ChatColor.AQUA;
                break;
            case "dark_aqua":
                colorOfArg = ChatColor.DARK_AQUA;
                break;
            case "dark_blue":
                colorOfArg = ChatColor.DARK_BLUE;
                break;
            case "blue":
                colorOfArg = ChatColor.BLUE;
                break;
            case "pink":
                colorOfArg = ChatColor.LIGHT_PURPLE;
                break;
            case "purple":
                colorOfArg = ChatColor.DARK_PURPLE;
                break;
            case "black":
                colorOfArg = ChatColor.BLACK;
                break;
            case "dark_gray":
                colorOfArg = ChatColor.DARK_GRAY;
                break;
            case "gray":
                colorOfArg = ChatColor.GRAY;
                break;
            case "white":
                colorOfArg = ChatColor.WHITE;
                break;
            case "rainbow":
                if (!p.hasPermission("fancyglow.rainbow") || !p.hasPermission("fancyglow.all_colors")) {
                    MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
                    return;
                }
                glowManager.toggleMulticolorGlow(p);
                return;
            default:
                MessageUtils.miniMessageSender(
                        p, plugin.getConfig().getString("Messages.Not_Valid_Color"));
                return;
        }

        ChatColor color = colorOfArg;
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
