package hhitt.org.example.fancyglow;

import hhitt.org.example.fancyglow.Config.MainConfigManager;
import hhitt.org.example.fancyglow.Inventory.InventorManager;
import hhitt.org.example.fancyglow.Listeners.HeadClickListener;
import hhitt.org.example.fancyglow.Listeners.MenuClickListener;
import hhitt.org.example.fancyglow.Listeners.PlayerQuitListener;
import hhitt.org.example.fancyglow.Utils.IsGlowingVariable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FancyGlow extends JavaPlugin {

    private MainConfigManager mainConfigManager;
    @Override
    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();
        Objects.requireNonNull(getCommand("glow")).setExecutor(new InventorManager(this, null));
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new IsGlowingVariable(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}