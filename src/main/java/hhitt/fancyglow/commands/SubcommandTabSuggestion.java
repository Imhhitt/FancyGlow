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

        //Suggest the player "disable" con tab completion
        if(args.length == 1 && sender.hasPermission("fancyglow.command") && !sender.hasPermission("fancyglow.admin")){
            List<String> completions = new ArrayList<>();
            completions.add("disable");
            return completions;
        }

        if(args.length == 1 && sender.hasPermission("fancyglow.admin")){
            List<String> completions = new ArrayList<>();
            completions.add("disable");
            completions.add("reload");
            return completions;
        }
        return Collections.emptyList();
    }
}
