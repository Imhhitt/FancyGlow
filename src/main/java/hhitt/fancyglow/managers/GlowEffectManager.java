package hhitt.fancyglow.managers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import hhitt.fancyglow.utils.MessageUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Optional;

public class GlowEffectManager {

    private static final String TEAM_NAME_PREFIX = "glow_";

    public static void setPlayerGlow(Player player, NamedTextColor color) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = generateTeamName(player);
        
        Team team = Optional.ofNullable(scoreboard.getTeam(teamName))
                .orElseGet(() -> scoreboard.registerNewTeam(teamName));

        team.setColor(convertColor(color));
        team.addEntry(player.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        sendGlowPacket(player, color, WrapperPlayServerTeams.OptionData.ALL);
    }

    public static void removePlayerGlow(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = generateTeamName(player);
        Optional.ofNullable(scoreboard.getTeam(teamName)).ifPresent(team -> {
            team.removeEntry(player.getName());
            team.unregister();
            sendGlowPacket(player, NamedTextColor.WHITE, WrapperPlayServerTeams.OptionData.NONE);
        });
    }

    private static void sendGlowPacket(Player player, NamedTextColor color, WrapperPlayServerTeams.OptionData option) {
        WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                MessageUtils.parse(generateTeamName(player)),
                null,
                null,
                WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                WrapperPlayServerTeams.CollisionRule.ALWAYS,
                color,
                option
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, teamInfo);
    }

    private static ChatColor convertColor(NamedTextColor color) {
        try {
            return ChatColor.valueOf(color.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ChatColor.WHITE;
        }
    }

    private static String generateTeamName(Player player) {
        return TEAM_NAME_PREFIX + player.getUniqueId().toString().substring(0, 8);
    }
}
