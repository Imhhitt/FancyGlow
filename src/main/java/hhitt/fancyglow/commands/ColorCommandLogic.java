package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class ColorCommandLogic {

    private final Map<ChatColor, Team> glowTeams;
    private final FancyGlow plugin;
    public ColorCommandLogic(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowTeams = new HashMap<>();
    }


    public void glowColorCommand(Player p, String arg){
        ChatColor color;
        try {
            color = ChatColor.valueOf(arg.toUpperCase());
        } catch (IllegalStateException e) {
            MessageUtils.miniMessageSender(p, plugin.getConfig().getString("Messages.Not_Valid_Color"));
            return;
        }

        if (
            hasGlowPermission(p, color) ||
            p.hasPermission("fancyglow.all_colors") ||
            p.hasPermission("fancyglow.admin")
        ) {
            toggleGlow(p, color);
        } else {
            MessageUtils.miniMessageSender(p, plugin.getMainConfigManager().getNoPermissionMessage());
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


    private boolean hasGlowPermission(Player player, ChatColor color){
        return player.hasPermission("fancyglow."+ color.name().toLowerCase());
    }


    private Team getOrCreateTeam(ChatColor color) {
        Team glowTeam = glowTeams.get(color);
        try { glowTeam.getName(); } catch (IllegalStateException | NullPointerException e) {
            glowTeam = createTeam(color);
            glowTeams.put(color, glowTeam);
        }
        return glowTeam;
    }

    //Thanks to https://github.com/FragsVoid for suggest me the null check to avoid a common bug on server restart!

    private Team createTeam(ChatColor color) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        if (board.getTeam(color.name()) == null) {
            Team team = board.registerNewTeam(color.name());
            team.setColor(color);
            return team;
        }
        return board.getTeam(color.name());
    }
}
