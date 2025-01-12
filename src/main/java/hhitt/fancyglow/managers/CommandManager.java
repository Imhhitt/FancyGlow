package hhitt.fancyglow.managers;

import hhitt.fancyglow.FancyGlow;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class CommandManager {

    private final FancyGlow plugin;
    private final Lamp<BukkitCommandActor> lamp;

    public CommandManager(FancyGlow plugin) {
        this.plugin = plugin;
        this.lamp = BukkitLamp.builder(plugin).build();
    }

    public void registerCommands() {
    }

    public void unregisterAll() {
        lamp.unregisterAllCommands();
    }
}
