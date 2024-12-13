package hhitt.fancyglow;

import hhitt.fancyglow.commands.MainCommand;
import hhitt.fancyglow.commands.SubcommandTabSuggestion;
import hhitt.fancyglow.config.MainConfigManager;
import hhitt.fancyglow.listeners.HeadClickListener;
import hhitt.fancyglow.listeners.MenuClickListener;
import hhitt.fancyglow.listeners.PlayerChangeWorldListener;
import hhitt.fancyglow.listeners.PlayerQuitListener;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.FancyGlowPlaceholder;
import hhitt.fancyglow.utils.MessageUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public final class FancyGlow extends JavaPlugin {

    private BukkitAudiences adventure;
    private MainConfigManager mainConfigManager;

    private GlowManager glowManager;
    private PlayerGlowManager playerGlowManager;

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // bStats hook / metrics
        new Metrics(this, 22057);

        // Try to create adventure audience
        this.adventure = BukkitAudiences.create(this);
        MessageUtils.setAdventure(this.adventure);

        // Init config manager
        mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();

        // Init managers
        glowManager = new GlowManager(this);
        playerGlowManager = new PlayerGlowManager(this);

        // Register command and suggestions
        Objects.requireNonNull(getCommand("glow")).setExecutor(new MainCommand(this));
        Objects.requireNonNull(getCommand("glow")).setTabCompleter(new SubcommandTabSuggestion(this));

        // Register events
        registerEvents();

        // Attempt to get PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new FancyGlowPlaceholder(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        glowManager.cancelMulticolorTasks();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public GlowManager getGlowManager() {
        return glowManager;
    }

    public PlayerGlowManager getPlayerGlowManager() {
        return playerGlowManager;
    }
}