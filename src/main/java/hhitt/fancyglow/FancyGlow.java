package hhitt.fancyglow;

import hhitt.fancyglow.commands.ColorCommandLogic;
import hhitt.fancyglow.commands.MainCommand;
import hhitt.fancyglow.commands.SubcommandTabSuggestion;
import hhitt.fancyglow.config.MainConfigManager;
import hhitt.fancyglow.listeners.HeadClickListener;
import hhitt.fancyglow.listeners.MenuClickListener;
import hhitt.fancyglow.listeners.PlayerChangeWorldListener;
import hhitt.fancyglow.listeners.PlayerQuitListener;
import hhitt.fancyglow.tasks.MulticolorTask;
import hhitt.fancyglow.utils.FancyGlowPlaceholder;
import hhitt.fancyglow.utils.GlowManager;
import hhitt.fancyglow.utils.IsGlowingVariable;
import hhitt.fancyglow.utils.MessageUtils;
import hhitt.fancyglow.utils.PlayerGlowingColor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Objects;

public final class FancyGlow extends JavaPlugin {

    private BukkitAudiences adventure;
    private MainConfigManager mainConfigManager;
    private ColorCommandLogic colorCommandLogic;
    private Map<Player, MulticolorTask> multicolorTasks;
    private MenuClickListener menuClickListener;
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

        //Bstats hook
        int pluginId = 22057;
        new Metrics(this, pluginId);

        this.adventure = BukkitAudiences.create(this);
        glowManager = new GlowManager(this);
        this.menuClickListener = new MenuClickListener(this);
        this.colorCommandLogic = new ColorCommandLogic(this, this.glowManager);
        MessageUtils.setAdventure(this.adventure);

        mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();
        //TODO: Also being instantiated at FancyGlowPlaceholder, not being used yet?
        PlayerGlowingColor playerGlowingColor = new PlayerGlowingColor(this);
        getCommand("glow").setTabCompleter(new SubcommandTabSuggestion());

        playerGlowManager = new PlayerGlowManager(this);

        Objects.requireNonNull(getCommand("glow")).setExecutor(new MainCommand(this));
        Objects.requireNonNull(getCommand("glow")).setTabCompleter(new SubcommandTabSuggestion(this));

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

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        cancelMulticolorTasks();

    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public ColorCommandLogic getColorCommandLogic() {
        return colorCommandLogic;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new IsGlowingVariable(this), this);
        getServer().getPluginManager().registerEvents(new HeadClickListener(this, this.glowManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);
    }

    public void cancelMulticolorTasks() {
        if (multicolorTasks != null) {
            for (MulticolorTask task : multicolorTasks.values()) {
                task.cancel();
            }
            multicolorTasks.clear();
        }

    }

    public GlowManager getGlowManager() {
        return glowManager;
    }

    public PlayerGlowManager getPlayerGlowManager() {
        return playerGlowManager;
    }
}