package hhitt.fancyglow.commands;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.inventory.CreatingInventory;
import hhitt.fancyglow.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand implements CommandExecutor {

    private final FancyGlow plugin;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {

        Player player = (Player) sender;
        String actualWorld = ((Player) sender).getWorld().getName();
        List<String> noAllowedWorlds = plugin.getConfig().getStringList("Disabled_Worlds");


        //Users commands (/glow) that open the gui

        if (!sender.hasPermission("fancyglow.command")) {
            MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
            return true;
        }

        if(args == null || args.length == 0){

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
        if(!sender.hasPermission("fancyglow.command.disable")){
            MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
            return true;
        }

        if(args[0].equalsIgnoreCase("disable")){
            Scoreboard scoreboard = ((Player) sender).getScoreboard();

            for (Team team : scoreboard.getTeams()) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                }
            }

            //If the player is not glowing, "exception" error
            if(!player.isGlowing()){
                MessageUtils.miniMessageSender(player, plugin.getConfig().getString("Messages.Not_Glowing"));
            }else{
                //If is glowing, then disabled message
                MessageUtils.miniMessageSender(player, plugin.getMainConfigManager().getDisableGlow());
            }
            ((Player) sender).setGlowing(false);
            return true;
        }


        //Admin commands
        if(!sender.hasPermission("fancyglow.admin")){
            MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getNoPermissionMessage());
        } else{

            // Logic for /glow reload
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                plugin.getMainConfigManager().reloadConfig();
                MessageUtils.miniMessageSender((Player) sender, plugin.getMainConfigManager().getReloadConfigMessage());
                return true;
            }

            // Logic for /glow disable <player>
            if (args.length >= 2 && args[0].equalsIgnoreCase("disable")){

                Player playerToDisable = Bukkit.getPlayer(args[1]);
                Scoreboard scoreboard = playerToDisable.getScoreboard();

                for (Team team : scoreboard.getTeams()) {
                    if (team.hasEntry(playerToDisable.getName())) {
                        team.removeEntry(playerToDisable.getName());
                    }
                }
                MessageUtils.miniMessageSender(playerToDisable, this.plugin.getMainConfigManager().getDisableGlow());
                MessageUtils.miniMessageSender((Player) sender, this.plugin.getConfig().getString("Messages.Disable_Glow_Others"));
                playerToDisable.setGlowing(false);
                return true;

            }

        }

        return true;
    }
}
