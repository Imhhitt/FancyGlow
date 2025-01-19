package hhitt.fancyglow.managers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import hhitt.fancyglow.utils.MessageUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

// TODO: Proper implementation.
public class GlowEffectManager {

    private static final String TEAM_NAME_PREFIX = "glow_";

    public static void setPlayerGlow(Player player, NamedTextColor color) {
        String teamName = TEAM_NAME_PREFIX + player.getName();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);

        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        team.setColor(org.bukkit.ChatColor.valueOf(color.toString()));
        team.addEntry(player.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                MessageUtils.parse(teamName),
                null,
                null,
                WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                WrapperPlayServerTeams.CollisionRule.ALWAYS,
                color,
                WrapperPlayServerTeams.OptionData.ALL
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, teamInfo);
    }

    public static void removePlayerGlow(Player player) {
        String teamName = TEAM_NAME_PREFIX + player.getName();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);

        if (team != null) {
            team.removeEntry(player.getName());

            WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                    MessageUtils.parse(teamName),
                    null,
                    null,
                    WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                    WrapperPlayServerTeams.CollisionRule.ALWAYS,
                    NamedTextColor.WHITE,
                    WrapperPlayServerTeams.OptionData.NONE
            );

            PacketEvents.getAPI().getPlayerManager().sendPacket(player, teamInfo);

            team.unregister();
        }
    }
}