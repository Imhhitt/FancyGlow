package hhitt.fancyglow.utils;

public enum Messages {

    COLOR_COMMAND_USAGE("Messages.Color_Command_Usage"),
    DISABLE_COMMAND_USAGE("Messages.Disable_Usage"),

    UNKNOWN_TARGET("Messages.Unknown_Target"),
    NOT_GLOWING("Messages.Not_Glowing"),

    INVALID_COLOR("Messages.Not_Valid_Color"),
    COLOR_LORE("Inventory.Items.Color_Lore"),

    ENABLE_GLOW("Messages.Enable_Glow"), DISABLE_GLOW("Messages.Disable_Glow"),

    DISABLED_WORLD("Messages.Disabled_World_Message"),
    DISABLE_GLOW_EVERYONE("Messages.Disable_Glow_Everyone"),
    DISABLE_GLOW_OTHERS("Messages.Disable_Glow_Others"),
    TARGET_NOT_GLOWING("Messages.Target_Not_Glowing"),

    GLOW_STATUS_TRUE("Messages.Glow_Status_True"),
    GLOW_STATUS_FALSE("Messages.Glow_Status_False"),

    // Inventory,
    DARK_RED_NAME("Inventory.Items.Dark_Red_Glow_Name"), RED_NAME("Inventory.Items.Dark_Red_Glow_Name"),
    GOLD_NAME("Inventory.Items.Gold_Glow_Name"), YELLOW_NAME("Inventory.Items.Yellow_Glow_Name"),
    DARK_GREEN_NAME("Inventory.Items.Gold_Glow_Name"), GREEN_NAME("Inventory.Items.Yellow_Glow_Name"),
    DARK_AQUA_NAME("Inventory.Items.Gold_Glow_Name"), AQUA_NAME("Inventory.Items.Yellow_Glow_Name"),
    DARK_BLUE_NAME("Inventory.Items.Gold_Glow_Name"), BLUE_NAME("Inventory.Items.Yellow_Glow_Name"),
    PINK_NAME("Inventory.Items.Gold_Glow_Name"), PURPLE_NAME("Inventory.Items.Yellow_Glow_Name"),
    BLACK_NAME("Inventory.Items.Gold_Glow_Name"), DARK_GRAY_NAME("Inventory.Items.Yellow_Glow_Name"),
    GRAY_NAME("Inventory.Items.Gold_Glow_Name"), WHITE_NAME("Inventory.Items.Yellow_Glow_Name"),
    FILL_MATERIAL_NAME("Inventory.Items.Fill_Material_Name"),

    HEAD_NAME("Inventory.Items.Player_Head.Name"), HEAD_LORE("Inventory.Items.Player_Head.Lore"),
    RAINBOW_HEAD_NAME("Inventory.Items.Rainbow_Head"), RAINBOW_HEAD_LORE("Inventory.Items.Rainbow_Head_Lore"),
    INVENTORY_TITLE("Inventory.Title"),
    // General messages.
    NO_PERMISSION("Messages.No_Permission"), RELOADED("Messages.Reload_Message");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
