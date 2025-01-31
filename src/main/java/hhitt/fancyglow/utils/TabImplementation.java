package hhitt.fancyglow.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.PlayerGlowManager;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.EventBus;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.nametag.NameTagManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class TabImplementation {

    private final FancyGlow plugin;
    private final YamlDocument configuration;
    private final PlayerGlowManager playerGlowManager;

    private String tabVersion;

    public TabImplementation(FancyGlow plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfiguration();
        this.playerGlowManager = plugin.getPlayerGlowManager();
    }

    public void initialize() {
        Plugin tabPlugin = plugin.getServer().getPluginManager().getPlugin("TAB");
        if (tabPlugin == null || !tabPlugin.isEnabled()) return;

        tabVersion = tabPlugin.getDescription().getVersion();
        if (!isCompatibleTAB(stripTags(tabVersion), "5.0.4")) {
            plugin.getLogger().warning("Any TAB implementation won't work due to version incompatibility.");
            plugin.getLogger().warning("You need at least version 5.0.4 or newer. Current version: " + tabVersion);
            return;
        }

        // Init method right at the constructor, not sure if recommended.
        hook();
    }

    private void hook() {
        // Log message if TAB is now being used.
        plugin.getLogger().info("TAB " + tabVersion + " has been found, using it.");

        // Register player placeholder directly to tab.
        TabAPI instance = TabAPI.getInstance();
        EventBus eventBus = Objects.requireNonNull(instance.getEventBus(), "TAB EventBus is not available.");

        // Register TAB listener.
        eventBus.register(PlayerLoadEvent.class, event -> {
            // Ignore if option not enabled.
            if (!configuration.getBoolean("Auto_Tag")) return;

            //Use ticks to ensure PlayerGlowManager#getPlayerGlowColor doesn't get many calls.
            int ticks = plugin.getConfiguration().getInt("Rainbow_Update_Interval");
            if (ticks <= 0) {
                ticks = 1;
            }

            instance.getPlaceholderManager().registerPlayerPlaceholder(
                    "%fancyglow_tab_color%",
                    50 * ticks,
                    player -> playerGlowManager.getPlayerGlowColor((Player) player.getPlayer()));


            NameTagManager nameTagManager = Objects.requireNonNull(instance.getNameTagManager(), "TAB NameTagManager is unavailable.");

            String originalPrefix = nameTagManager.getOriginalPrefix(event.getPlayer());

            // Somehow tab still fails sometimes to retrieve player original prefix.
            if (originalPrefix == null) return;

            String modifiedPrefix = originalPrefix + "%fancyglow_tab_color%";
            nameTagManager.setPrefix(event.getPlayer(), modifiedPrefix);
        });
    }


    private boolean isCompatibleTAB(String tabVersion, String desiredVersion) {
        if (tabVersion.equals(desiredVersion)) return true;

        try {
            String[] versionParts = tabVersion.split("\\.");
            String[] desiredVersionParts = desiredVersion.split("\\.");

            for (int i = 0; i < desiredVersionParts.length; i++) {
                int current = Integer.parseInt(versionParts[i]);
                int required = Integer.parseInt(desiredVersionParts[i]);

                if (current != required) return current > required;
            }

            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            plugin.getLogger().warning("Failed to parse TAB version numbers: " + tabVersion);
            return false;
        }
    }

    public static String stripTags(final String version) {
        return version.replaceAll("[-;+].+", "");
    }
}
