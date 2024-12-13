package hhitt.fancyglow.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ColorUtils {

    // Implementation for the logic to map leather armor colors to colors
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

        //Add available colors
        colorMap.values().forEach(color -> colorValues.put(color.name(), color));
        //Manually add common used color names
        colorValues.put("PINK", ChatColor.LIGHT_PURPLE);
        colorValues.put("PURPLE", ChatColor.DARK_PURPLE);

        colorMap.forEach((key, value) -> reverseColorMap.put(value, key));
    }

    public static ChatColor getColorFromArmorColor(org.bukkit.Color armorColor) {
        return colorMap.getOrDefault(armorColor, ChatColor.WHITE);
    }

    public static Color getArmorColorFromChatColor(ChatColor chatColor) {
        return reverseColorMap.getOrDefault(chatColor, Color.WHITE);
    }

    public static Collection<String> getChatColorValues() {
        return colorValues.keySet();
    }

    public static ChatColor findColor(String value) {
        return colorValues.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(value))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
