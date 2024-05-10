package hhitt.org.example.fancyglow;

import hhitt.org.example.fancyglow.Config.MainConfigManager;
import hhitt.org.example.fancyglow.Config.ReloadConfigCommand;
import hhitt.org.example.fancyglow.Inventory.InventorManager;
import hhitt.org.example.fancyglow.Listeners.*;
import hhitt.org.example.fancyglow.Utils.FancyGlowPlaceholder;
import hhitt.org.example.fancyglow.Utils.IsGlowingVariable;
import hhitt.org.example.fancyglow.Utils.PlayerGlowingColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FancyGlow extends JavaPlugin {

    private MainConfigManager mainConfigManager;
    private PlayerGlowingColor playerGlowingColor;
    @Override
    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();
        playerGlowingColor = new PlayerGlowingColor(this);
        Objects.requireNonNull(getCommand("glow")).setExecutor(new InventorManager(this));
        Objects.requireNonNull(this.getCommand("glowreload")).setExecutor(new ReloadConfigCommand(this));
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new IsGlowingVariable(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new FancyGlowPlaceholder(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}