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


        //Users commands
        if (!sender.hasPermission("fancyglow.command")) {
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
        } else{

            //Logic for /glow
            if(args == null || args.length == 0){

                //Check the world
                if (noAllowedWorlds.contains(actualWorld)) {
                    player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getDisabledWorldMessage()));
                    return true;
                }

                //Open the gui
                if (sender instanceof Player) {
                    CreatingInventory inventory = new CreatingInventory(plugin, (Player) sender);
                    ((Player) sender).openInventory(inventory.getInventory());
                }
                return true;

            }

            //Logic for /glow disable
            if(args[0].equalsIgnoreCase("disable")  && sender instanceof Player){
                Scoreboard scoreboard = ((Player) sender).getScoreboard();

                for (Team team : scoreboard.getTeams()) {
                    if (team.hasEntry(player.getName())) {
                        team.removeEntry(player.getName());
                    }
                }

                //If the player is not glowing, "exception" error
                if(!player.isGlowing()){
                    player.sendMessage(MessageUtils.getColoredMessages(plugin.getConfig().getString("Messages.Not_Glowing")));
                }else{
                    //If is glowing, then disabled message
                    player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getDisableGlow()));
                }
                ((Player) sender).setGlowing(false);
                return true;
            }
        }


        //Admin commands
        if(!sender.hasPermission("fancyglow.admin")){
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
        } else{

            // Logic for /glow reload
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                plugin.getMainConfigManager().reloadConfig();
                sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getReloadConfigMessage()));
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
                playerToDisable.sendMessage(MessageUtils.getColoredMessages(this.plugin.getMainConfigManager().getDisableGlow()));
                sender.sendMessage(MessageUtils.getColoredMessages(this.plugin.getConfig().getString("Messages.Disable_Glow_Others")));
                playerToDisable.setGlowing(false);
                return true;

            }

        }

        return true;
    }
}
