package hhitt.fancyglow.utils;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.PlayerGlowManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyGlowPlaceholder extends PlaceholderExpansion {

    private final PlayerGlowManager playerGlowManager;

    public FancyGlowPlaceholder(FancyGlow plugin) {
        this.playerGlowManager = plugin.getPlayerGlowManager();
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
        return "1.4.1";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        String enabled = getPlaceholderAPI().getPlaceholderAPIConfig().booleanTrue();
        String disabled = getPlaceholderAPI().getPlaceholderAPIConfig().booleanFalse();

        if (params.startsWith("status_")) {
            String[] paramsArgs = params.split("_", 2);
            String paramsArg = paramsArgs[1];

            if (paramsArgs.length == 2 && !paramsArg.equalsIgnoreCase("formatted")) {
                String colorName = playerGlowManager.getPlayerGlowColorName(player);
                return colorName.equalsIgnoreCase(paramsArg) ? enabled : disabled;
            }
        }

        return switch (params) {
            case "color" -> playerGlowManager.getPlayerGlowColor(player);
            case "color_name" -> playerGlowManager.getPlayerGlowingMode(player);
            case "status_formatted" -> playerGlowManager.getPlayerGlowingStatus(player);
            case "status" ->
                    !playerGlowManager.getPlayerGlowingMode(player).equalsIgnoreCase("NONE") ? enabled : disabled;
            default -> "";
        };
    }
}
