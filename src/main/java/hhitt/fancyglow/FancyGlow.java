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
import hhitt.fancyglow.utils.UpdateChecker;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.EventBus;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.event.plugin.TabLoadEvent;
import me.neznamy.tab.api.nametag.NameTagManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import revxrsal.zapper.ZapperJavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class FancyGlow extends ZapperJavaPlugin {
    private final Logger logger = super.getLogger();

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
        MessageUtils.setAdventure(adventure());

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
        // Initialize tasks for glow-effects and avoid do it at every glow-effect toggle.
        this.glowManager.scheduleFlashingTask();
        this.glowManager.scheduleMulticolorTask();

        // Register command and suggestions
        this.commandManager = new CommandManager(this);
        this.commandManager.registerCommands();

        // Register events
        registerEvents();

        // Check for plugin updates.
        checkUpdates();

        // Attempts to hook into placeholderapi.
        hookPlaceholderAPI();
        // Attempts to hook onto TAB.
        hookTAB();
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
            this.glowManager.stopFlashingTask();
            this.glowManager.stopMulticolorTask();
        }
    }

    public void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new MenuClickListener(this), this);
        pluginManager.registerEvents(new HeadClickListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerChangeWorldListener(this), this);
    }

    private void checkUpdates() {
        if (!configuration.getBoolean("Notify_Updates")) return;
        UpdateChecker.init(this, 116326).requestUpdateCheck().whenComplete((result, exception) -> {
            if (result.requiresUpdate()) {
                this.logger.info(String.format("There is a new update available! FancyGlow %s may be downloaded on SpigotMC", result.getNewestVersion()));
                this.logger.info("Download it at https://www.spigotmc.org/resources/116326/updates");
            }
        });
    }

    private void hookPlaceholderAPI() {
        // Check if PlaceholderAPI is available.
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            this.logger.warning("Could not find PlaceholderAPI!");
            this.logger.warning("This plugin is required if you want to use its placeholders.");
        } else {
            // Actually register placeholderapi extension.
            new FancyGlowPlaceholder(this).register();
        }
    }

    private void hookTAB() {
        Plugin tabPlugin = getServer().getPluginManager().getPlugin("TAB");
        if (tabPlugin == null || !tabPlugin.isEnabled()) return;

        String desiredVersion = "5.0.4";
        String tabVersion = tabPlugin.getDescription().getVersion();

        try {
            if (!isCompatibleTAB(tabVersion, desiredVersion)) {
                this.logger.warning("This function only works with TAB version 5.0.4 or newer.");
                return;
            }

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            this.logger.warning("Failed to parse TAB version numbers: " + tabVersion);
            this.logger.warning("Function will not work due to version parsing errors.");
            return;
        }

        this.logger.info("TAB " + tabVersion + " has been found, using it.");

        // Register player placeholder directly to tab.
        EventBus eventBus = Objects.requireNonNull(TabAPI.getInstance().getEventBus(), "TAB EventBus is not available.");

        eventBus.register(TabLoadEvent.class, event -> {
            Bukkit.getScheduler().runTask(this, () -> {
                TabAPI.getInstance().getPlaceholderManager()
                        .registerPlayerPlaceholder(
                                "%fancyglow_tab_color%",
                                50,
                                player -> playerGlowManager.getPlayerGlowColor((Player) player.getPlayer()));
            });
        });

        // Register tab listener.
        eventBus.register(PlayerLoadEvent.class, event -> {
            // Ignore if option not enabled.
            if (!configuration.getBoolean("Auto_Tag")) return;

            Bukkit.getScheduler().runTask(this, () -> {
                TabPlayer player = event.getPlayer();

                NameTagManager nameTagManager = Objects.requireNonNull(TabAPI.getInstance().getNameTagManager(), "TAB NameTagManager is unavailable.");
                String originalPrefix = nameTagManager.getOriginalPrefix(player);

                String modifiedPrefix = originalPrefix + "%fancyglow_tab_color%";
                nameTagManager.setPrefix(player, modifiedPrefix);
            });
        });
    }

    // TODO: Maybe use regex?
    private boolean isCompatibleTAB(String tabVersion, String desiredVersion) {
        if (tabVersion.contains("-")) {
            tabVersion = tabVersion.split("-")[0];
        }

        String[] versionParts = tabVersion.split("\\.");
        String[] desiredVersionParts = desiredVersion.split("\\.");

        int major = Integer.parseInt(versionParts[0]);
        int minor = Integer.parseInt(versionParts[1]);
        int patch = Integer.parseInt(versionParts[2]);

        int desiredMajor = Integer.parseInt(desiredVersionParts[0]);
        int desiredMinor = Integer.parseInt(desiredVersionParts[1]);
        int desiredPatch = Integer.parseInt(desiredVersionParts[2]);

        // Check version compatibility
        return major > desiredMajor ||
                (major == desiredMajor && minor > desiredMinor) ||
                (major == desiredMajor && minor == desiredMinor && patch >= desiredPatch);
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