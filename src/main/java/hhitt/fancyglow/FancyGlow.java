package hhitt.fancyglow;

import hhitt.fancyglow.config.MainConfigManager;
import hhitt.fancyglow.listeners.HeadClickListener;
import hhitt.fancyglow.listeners.MenuClickListener;
import hhitt.fancyglow.listeners.PlayerChangeWorldListener;
import hhitt.fancyglow.listeners.PlayerQuitListener;
import hhitt.fancyglow.managers.CommandManager;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.FancyGlowPlaceholder;
import hhitt.fancyglow.utils.MessageUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import revxrsal.zapper.ZapperJavaPlugin;

public final class FancyGlow extends JavaPlugin {
public final class FancyGlow extends ZapperJavaPlugin {

    private BukkitAudiences adventure;
    private MainConfigManager mainConfigManager;

    private GlowManager glowManager;
    private PlayerGlowManager playerGlowManager;

    private CommandManager commandManager;

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
        commandManager = new CommandManager(this);
        commandManager.registerCommands();

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

        if (commandManager != null) {
            commandManager.unregisterAll();
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