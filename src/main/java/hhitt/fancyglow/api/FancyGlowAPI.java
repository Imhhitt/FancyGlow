package hhitt.fancyglow.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface FancyGlowAPI {

    /**
     * Gets the current glow color of a player as a string.
     * The returned string represents the color in a format that can be used for further processing.
     *
     * @param player The player whose glow color is being retrieved. Must not be null.
     *
     * @return The color format to use in another place or empty if the player does not have a glow effect.
     */
    String getPlayerGlowColor(Player player);

    /**
     * Gets the display name of the player's current glow color.
     *
     * @param player The player whose glow color name is being retrieved. Must not be null.
     *
     * @return A string representing the display name of the player's glow color, or "NONE" (configurable at plugin config) if the player does not have a glow effect.
     */
    String getPlayerGlowColorName(Player player);

    /**
     * Sets the glow color of a player.
     * This method applies the specified glow color to the player, overriding any existing glow effect.
     *
     * @param player The player whose glow color is being set. Must not be null.
     * @param color  The ChatColor to set as the player's glow color. Must not be null.
     */
    void setPlayerGlowColor(Player player, ChatColor color);

    /**
     * Removes the glow effect from a player.
     * This method clears any active glow effect applied to the player.
     *
     * @param player The player whose glow effect is being removed. Must not be null.
     */
    void removePlayerGlow(Player player);

    /**
     * Checks if a player has a glow effect.
     * This method determines whether the player currently has any glow effect applied.
     *
     * @param player The player to check. Must not be null.
     *
     * @return True if the player has a glow effect, false otherwise.
     */
    boolean hasGlow(Player player);

    /**
     * Checks if a player has rainbow mode enabled for their glow effect.
     * Rainbow mode cycles through multiple colors for the player's glow effect.
     *
     * @param player The player to check. Must not be null.
     *
     * @return Returns true if the player has rainbow mode enabled, false otherwise.
     */
    boolean hasRainbowMode(Player player);

    /**
     * Enables or disables rainbow mode for a player.
     * Rainbow mode cycles through multiple colors for the player's glow effect.
     *
     * @param player The player whose rainbow mode is being toggled. Must not be null.
     * @param status If true will enable player rainbow mode.
     */
    void setRainbowMode(Player player, boolean status);

    /**
     * Checks if a player has flashing mode enabled for their glow effect.
     * Flashing mode makes the player's glow effect blink or flash at regular intervals.
     *
     * @param player The player to check. Must not be null.
     *
     * @return Returns true if the player has flashing mode enabled, false otherwise.
     */
    boolean hasFlashingMode(Player player);

    /**
     * Enables or disables flashing mode for a player.
     * Flashing mode makes the player's glow effect blink or flash at regular intervals.
     *
     * @param player The player whose flashing mode is being toggled. Must not be null.
     * @param status If true will enable player flashing mode.
     */
    void setFlashingMode(Player player, boolean status);
}