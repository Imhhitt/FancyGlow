package hhitt.fancyglow.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ColorUtils {

    // Implementation for the logic to map leather armor colors to colors
    private static final Set<String> availableColorSet = new HashSet<>();
    private static final Map<String, ChatColor> colorValues = new HashMap<>();
    private static final Map<Color, ChatColor> colorMap = new HashMap<>();
    private static final Map<ChatColor, Color> reverseColorMap = new HashMap<>();

    static {
        colorMap.put(Color.BLACK, ChatColor.BLACK);
        colorMap.put(Color.BLUE, ChatColor.BLUE);
        colorMap.put(Color.LIME, ChatColor.GREEN);
        colorMap.put(Color.AQUA, ChatColor.AQUA);
        colorMap.put(Color.RED, ChatColor.RED);
        colorMap.put(Color.FUCHSIA, ChatColor.LIGHT_PURPLE);
        colorMap.put(Color.YELLOW, ChatColor.YELLOW);
        colorMap.put(Color.WHITE, ChatColor.WHITE);
        colorMap.put(Color.SILVER, ChatColor.GRAY);
        colorMap.put(Color.GRAY, ChatColor.DARK_GRAY);
        colorMap.put(Color.NAVY, ChatColor.DARK_BLUE);
        colorMap.put(Color.GREEN, ChatColor.DARK_GREEN);
        colorMap.put(Color.TEAL, ChatColor.DARK_AQUA);
        colorMap.put(Color.MAROON, ChatColor.DARK_RED);
        colorMap.put(Color.PURPLE, ChatColor.DARK_PURPLE);
        colorMap.put(Color.ORANGE, ChatColor.GOLD);

        colorMap.forEach((color, chatColor) -> {
            // Reverse colorMap into its own map.
            reverseColorMap.put(chatColor, color);

            // Populate color values.
            colorValues.put(chatColor.name(), chatColor);

            // Populate available color set.
            availableColorSet.add(chatColor.name().toLowerCase(Locale.ROOT));
        });

        //Manually add common used color names
        colorValues.put("PINK", ChatColor.LIGHT_PURPLE);
        colorValues.put("PURPLE", ChatColor.DARK_PURPLE);
        availableColorSet.add("pink");
        availableColorSet.add("purple");
        availableColorSet.add("rainbow");
        availableColorSet.add("flashing");
    }

    /**
     * Gets the ChatColor corresponding to the given armor color.
     *
     * @param armorColor The armor color.
     *
     * @return The corresponding ChatColor, or WHITE if not found.
     */
    public static ChatColor getColorFromArmorColor(org.bukkit.Color armorColor) {
        return colorMap.getOrDefault(armorColor, ChatColor.WHITE);
    }

    /**
     * Gets the Color corresponding to the given ChatColor.
     *
     * @param chatColor The ChatColor.
     *
     * @return The corresponding Color, or WHITE if not found.
     */
    public static Color getArmorColorFromChatColor(ChatColor chatColor) {
        return reverseColorMap.getOrDefault(chatColor, Color.WHITE);
    }

    /**
     * Gets a set of all available color names.
     *
     * @return A set of color names.
     */
    public static Set<String> getAvailableColorsSet() {
        return availableColorSet;
    }

    /**
     * @param name String name to check for.
     *
     * @return Returns if passed value is a valid color from available ones.
     */
    public static boolean isAvailableColor(String name) {
        return availableColorSet.contains(name.toLowerCase(Locale.ROOT));
    }

    /**
     * Finds a ChatColor by its name.
     *
     * @param value The name of the color.
     *
     * @return The corresponding ChatColor, or null if not found.
     */
    public static ChatColor findColor(String value) {
        return colorValues.get(value.toUpperCase(Locale.ROOT));
    }

}
