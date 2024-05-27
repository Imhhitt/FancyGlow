package hhitt.fancyglow.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubcommandTabSuggestion implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        //Suggest for disable and color
        if(args.length == 1 && sender.hasPermission("fancyglow.command.disable") && !sender.hasPermission("fancyglow.admin") ||
        args.length == 1 && sender.hasPermission("fancyglow.command.color") && !sender.hasPermission("fancyglow.admin")){
            List<String> completions = new ArrayList<>();
            completions.add("disable");
            completions.add("color");
            return completions;
        }

        if(args.length == 1 && sender.hasPermission("fancyglow.admin")){
            List<String> completions = new ArrayList<>();
            completions.add("disable");
            completions.add("reload");
            completions.add("color");
            return completions;
        }

        //Suggest for all color colors
        if(args.length == 2 && args[0].equalsIgnoreCase("color") && sender.hasPermission("fancyglow.command.color") ||
                args.length == 2 && args[0].equalsIgnoreCase("color") && sender.hasPermission("fancyglow.admin") ){
            List<String> completions = new ArrayList<>();
            completions.add("dark_red");
            completions.add("red");
            completions.add("gold");
            completions.add("yellow");
            completions.add("dark_green");
            completions.add("green");
            completions.add("aqua");
            completions.add("dark_aqua");
            completions.add("dark_blue");
            completions.add("blue");
            completions.add("pink");
            completions.add("purple");
            completions.add("black");
            completions.add("dark_gray");
            completions.add("gray");
            completions.add("white");
            return completions;
        }


        return Collections.emptyList();
    }
}
