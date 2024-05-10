package hhitt.org.example.fancyglow.Config;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {

    // Juts the reload command

    private final FancyGlow plugin;

    public ReloadConfigCommand(FancyGlow plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args){
        if(!(sender instanceof Player)){
            plugin.getMainConfigManager().reloadConfig();
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getReloadConfigMessage()));
            return true;
        }
        if(!sender.hasPermission("fancyglow.admin")){
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
        }else{
            plugin.getMainConfigManager().reloadConfig();
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getReloadConfigMessage()));
        }
        return true;

    }
}