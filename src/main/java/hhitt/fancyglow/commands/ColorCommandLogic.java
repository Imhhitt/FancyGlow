package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ColorCommandLogic {

    private final Map<ChatColor, Team> glowTeams;
    private final FancyGlow plugin;
    public ColorCommandLogic(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
    }


    public void glowColorCommand(Player p, String arg){

        ChatColor colorOfArg;
        switch (arg){
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
            default:
                MessageUtils.miniMessageSender(
                        p, plugin.getConfig().getString("Messages.Not_Valid_Color"));
                return;
        }

        ChatColor color = colorOfArg;
        if (!(color != null && hasGlowPermission(p, color) ||
                color != null && p.hasPermission("fancyglow.all_colors") ||
                color != null && p.hasPermission("fancyglow.admin"))) {
            MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
        } else {
            toggleGlow(p, color);
        }

    }


    private void toggleGlow(Player player, ChatColor color) {
        Team glowTeam = getOrCreateTeam(color);
        if (glowTeam != null) {
            if (glowTeam.hasEntry(player.getName())) {
                glowTeam.removeEntry(player.getName());
                player.setGlowing(false);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            } else {
                glowTeam.addEntry(player.getName());
                player.setGlowing(true);
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getEnableGlow());
            }
        }
    }


    public boolean hasGlowPermission(Player player, ChatColor color){
        return player.hasPermission("fancyglow."+ color.name().toLowerCase());
    }


    public Team getOrCreateTeam(ChatColor color) {
        Team glowTeam = glowTeams.get(color);
        if (glowTeam == null) {
            glowTeam = createTeam(color);
            glowTeams.put(color, glowTeam);
        }
        return glowTeam;
    }

    public Team createTeam(ChatColor color) {
        Scoreboard board = Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard();
        Team team = board.registerNewTeam(color.name());
        team.setColor(color);
        return team;
    }


}
