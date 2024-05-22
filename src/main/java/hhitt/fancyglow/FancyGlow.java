package hhitt.fancyglow;

import hhitt.fancyglow.commands.MainCommand;
import hhitt.fancyglow.commands.SubcommandTabSuggestion;
import hhitt.fancyglow.config.MainConfigManager;
import hhitt.fancyglow.listeners.HeadClickListener;
import hhitt.fancyglow.listeners.MenuClickListener;
import hhitt.fancyglow.listeners.PlayerChangeWorldListener;
import hhitt.fancyglow.listeners.PlayerQuitListener;
import hhitt.fancyglow.utils.FancyGlowPlaceholder;
import hhitt.fancyglow.utils.IsGlowingVariable;
import hhitt.fancyglow.utils.PlayerGlowingColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FancyGlow extends JavaPlugin {

    private MainConfigManager mainConfigManager;

    @Override
    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();
        PlayerGlowingColor playerGlowingColor = new PlayerGlowingColor(this);
        getCommand("glow").setTabCompleter(new SubcommandTabSuggestion());
        Objects.requireNonNull(getCommand("glow")).setExecutor(new MainCommand(this));
        registerEvents();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new FancyGlowPlaceholder(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }


    @Override
    public void onDisable() {
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new IsGlowingVariable(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);
    }
}