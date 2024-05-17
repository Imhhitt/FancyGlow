package hhitt.fancyglow.utils;

import hhitt.fancyglow.FancyGlow;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyGlowPlaceholder extends PlaceholderExpansion {

    // PlaceholderAPI hook to create a placeholder (color one)

    private final FancyGlow plugin;
    private PlayerGlowingColor playerGlowingColor;
    public FancyGlowPlaceholder(FancyGlow plugin){
        this.plugin = plugin;
        this.playerGlowingColor = new PlayerGlowingColor(plugin);
    }


    @Override
    public @NotNull String getIdentifier() {
        return "fancyglow";
    }

    @Override
    public @NotNull String getAuthor() {
        return "hhitt";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public boolean persist(){
      return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player == null){
            return "";
        }
        if(params.equals("color")){
            return playerGlowingColor.getPlayerGlowColor(player);
        }
        return "";
    }
}
