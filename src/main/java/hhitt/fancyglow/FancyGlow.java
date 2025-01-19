package hhitt.fancyglow;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import hhitt.fancyglow.listeners.HeadClickListener;
import hhitt.fancyglow.listeners.MenuClickListener;
import hhitt.fancyglow.listeners.PlayerChangeWorldListener;
import hhitt.fancyglow.listeners.PlayerQuitListener;
import hhitt.fancyglow.managers.CommandManager;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.FancyGlowPlaceholder;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.MessageUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import revxrsal.zapper.ZapperJavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class FancyGlow extends ZapperJavaPlugin {

    private BukkitAudiences adventure;

    private YamlDocument configuration;
    private MessageHandler messageHandler;

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
        MessageUtils.setAdventure(adventure);

        // Init config manager
        try {
            this.configuration = YamlDocument.create(
                    new File(this.getDataFolder(), "config.yml"),
                    Objects.requireNonNull(getResource("config.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE)), "config-version").build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.messageHandler = new MessageHandler(this, configuration);

        // Init managers
        this.glowManager = new GlowManager(this);
        this.playerGlowManager = new PlayerGlowManager(this);

        // Register command and suggestions
        this.commandManager = new CommandManager(this);
        this.commandManager.registerCommands();

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

        if (this.commandManager != null) {
            this.commandManager.unregisterAll();
        }

        if (this.glowManager != null) {
            this.glowManager.cancelMulticolorTasks();
        }
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);
    }

    public YamlDocument getConfiguration() {
        return configuration;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public GlowManager getGlowManager() {
        return glowManager;
    }

    public PlayerGlowManager getPlayerGlowManager() {
        return playerGlowManager;
    }
}