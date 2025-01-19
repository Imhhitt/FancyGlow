package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.commands.MainCommand;
import hhitt.fancyglow.commands.lamp.ColorSuggestionFactory;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class CommandManager {

    private final FancyGlow plugin;
    private final Lamp<BukkitCommandActor> lamp;

    public CommandManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.lamp = BukkitLamp.builder(plugin)
                .suggestionProviders(suggestions ->
                        suggestions.addProviderFactory(new ColorSuggestionFactory(plugin)))
                .build();
    }

    public void registerCommands() {
        lamp.register(new MainCommand(plugin));
    }

    public void unregisterAll() {
        lamp.unregisterAllCommands();
    }
}
