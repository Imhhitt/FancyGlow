package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.commands.MainCommand;
import hhitt.fancyglow.commands.lamp.ColorSuggestionFactory;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class CommandManager {

    private final FancyGlow plugin;
    private Lamp<BukkitCommandActor> lamp;

    public CommandManager(FancyGlow plugin) {
        this.plugin = plugin;
        initializeLamp();
    }

    private void initializeLamp() {
        this.lamp = BukkitLamp.builder(plugin)
                .suggestionProviders(suggestions ->
                        suggestions.addProviderFactory(new ColorSuggestionFactory(plugin)))
                .build();
    }

    public void registerCommands() {
        if (lamp != null) {
            lamp.register(new MainCommand(plugin));
        }
    }

    public void unregisterAllCommands() {
        if (lamp != null) {
            lamp.unregisterAllCommands();
        }
    }
}
