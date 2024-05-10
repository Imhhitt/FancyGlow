package hhitt.org.example.fancyglow.Inventory;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Utils.IsGlowingVariable;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;

public class InventorManager implements CommandExecutor {

    // Filling and management for the inventory

    private final FancyGlow plugin;

    public InventorManager(FancyGlow plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player) sender;
        String actualWorld = ((Player) sender).getWorld().getName();
        List<String> noAllowedWorlds = plugin.getConfig().getStringList("Disabled_Worlds");

        if (noAllowedWorlds.contains(actualWorld)) {
            player.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getDisabledWorldMessage()));
            return true;
        }

        if (!sender.hasPermission("fancyglow.command")) {
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
        } else {

            if(args == null || args.length == 0){

                if (sender instanceof Player) {
                    CreatingInventory inventory = new CreatingInventory(plugin, (Player) sender);
                    ((Player) sender).openInventory(inventory.getInventory());
                }
                return true;

            }

        }

        if(args[0].equalsIgnoreCase("disable")  && sender instanceof Player){
            Scoreboard scoreboard = ((Player) sender).getScoreboard();

            for (Team team : scoreboard.getTeams()) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                }
            }
            ((Player) sender).setGlowing(false);
            return true;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("disable")){

            Player playerToDisable = Bukkit.getPlayer(args[1]);
            Scoreboard scoreboard = ((Player) playerToDisable).getScoreboard();

            for (Team team : scoreboard.getTeams()) {
                if (team.hasEntry(playerToDisable.getName())) {
                    team.removeEntry(playerToDisable.getName());
                }
            }
            playerToDisable.setGlowing(false);
            return true;

        }


        return true;
    }





}
