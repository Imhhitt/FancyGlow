package hhitt.fancyglow.utils;

public enum Messages {

    // Plugin messages.
    COLOR_COMMAND_USAGE("Messages.Color_Command_Usage"),
    DISABLE_COMMAND_USAGE("Messages.Disable_Usage"),
    UNKNOWN_TARGET("Messages.Unknown_Target"),
    NOT_GLOWING("Messages.Not_Glowing"),
    INVALID_COLOR("Messages.Not_Valid_Color"),
    ENABLE_GLOW("Messages.Enable_Glow"),
    DISABLE_GLOW("Messages.Disable_Glow"),
    COLOR_ALREADY_SELECTED("Messages.Color_Already_Selected"),
    FLASHING_WITH_RAINBOW("Messages.Flashing_With_Rainbow"),
    DISABLED_WORLD("Messages.Disabled_World_Message"),
    DISABLE_GLOW_EVERYONE("Messages.Disable_Glow_Everyone"),
    DISABLE_GLOW_OTHERS("Messages.Disable_Glow_Others"),
    TARGET_NOT_GLOWING("Messages.Target_Not_Glowing"),
    GLOW_STATUS_TRUE("Messages.Glow_Status_True"),
    GLOW_STATUS_FALSE("Messages.Glow_Status_False"),
    GLOW_STATUS_NONE("Messages.Glow_Status_None"),

    // General messages.
    NO_PERMISSION("Messages.No_Permission"),
    RELOADED("Messages.Reload_Message"),
    MESSAGE_NOT_FOUND("Messages.Message_Not_Found"),

    // Inventory messages.
    COLOR_LORE("Inventory.Items.Color_Lore"),
    DARK_RED_NAME("Inventory.Items.Dark_Red_Glow_Name"),
    RED_NAME("Inventory.Items.Red_Glow_Name"),
    GOLD_NAME("Inventory.Items.Gold_Glow_Name"),
    YELLOW_NAME("Inventory.Items.Yellow_Glow_Name"),
    DARK_GREEN_NAME("Inventory.Items.Dark_Green_Glow_Name"),
    GREEN_NAME("Inventory.Items.Green_Glow_Name"),
    DARK_AQUA_NAME("Inventory.Items.Dark_Aqua_Glow_Name"),
    AQUA_NAME("Inventory.Items.Aqua_Glow_Name"),
    DARK_BLUE_NAME("Inventory.Items.Dark_Blue_Glow_Name"),
    BLUE_NAME("Inventory.Items.Blue_Glow_Name"),
    LIGHT_PURPLE_NAME("Inventory.Items.Pink_Glow_Name"),
    DARK_PURPLE_NAME("Inventory.Items.Purple_Glow_Name"),
    BLACK_NAME("Inventory.Items.Black_Glow_Name"),
    DARK_GRAY_NAME("Inventory.Items.Dark_Gray_Glow_Name"),
    GRAY_NAME("Inventory.Items.Gray_Glow_Name"),
    WHITE_NAME("Inventory.Items.White_Glow_Name"),

    // Inventory
    FILLER_NAME("Inventory.Filler.Name"),
    HEAD_NAME("Inventory.Status.Name"),
    HEAD_LORE("Inventory.Status.Lore"),
    RAINBOW_HEAD_NAME("Inventory.Rainbow.Name"),
    RAINBOW_HEAD_LORE("Inventory.Rainbow.Lore"),
    FLASHING_HEAD_NAME("Inventory.Flashing.Name"),
    FLASHING_HEAD_LORE("Inventory.Flashing.Lore"),
    INVENTORY_TITLE("Inventory.Title");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static Messages match(String string) {
        for (final Messages message : values()) {
            if (!message.name().equalsIgnoreCase(string)) continue;
            return message;
        }
        return MESSAGE_NOT_FOUND;
    }
}
