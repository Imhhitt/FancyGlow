package hhitt.org.example.fancyglow.Config;

import hhitt.org.example.fancyglow.FancyGlow;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfigManager {

    // Management for the config.yml
    private final MainConfig configFile;
    private String rainbowLore;
    private String rainbowName;
    private String noPermissionMessage;
    private String reloadConfigMessage;
    private String enableGlow;
    private String disableGlow;
    private String inventoryTittle;
    private String aquaName;
    private String darkAquaName;
    private String darkRedName;
    private String darkBlueName;
    private String darkGreenName;
    private String grayName;
    private String darkGrayName;
    private String purpleName;
    private String goldName;
    private String yellowName;
    private String redName;
    private String blueName;
    private String greenName;
    private String pinkName;
    private String blackName;
    private String whiteName;
    private String headName;
    private String headLore;
    private String glowStatusTrue;
    private String glowStatusFalse;
    private String fillMaterialName;
    private String colorLore;
    private String disabledWorldMessage;
    private boolean nicknameColor;


    // Cargar la configuración
    public MainConfigManager(FancyGlow FancyGlow) {
        configFile = new MainConfig("config.yml", null, FancyGlow);
        configFile.registerConfig();
        loadConfig();
    }

    // Cargar los mensajes de la config en variables
    public void loadConfig(){
        FileConfiguration configuration = configFile.getConfig();
        nicknameColor = configuration.getBoolean("Options.Color_Nickname");
        disabledWorldMessage = configuration.getString("Messages.Disabled_World_Message");
        noPermissionMessage = configuration.getString("Messages.No_Permission");
        reloadConfigMessage = configuration.getString("Messages.Reload_Message");
        enableGlow = configuration.getString("Messages.Enable_Glow");
        disableGlow = configuration.getString("Messages.Disable_Glow");
        inventoryTittle = configuration.getString("Inventory.Tittle");
        rainbowName = configuration.getString("Inventory.Items.Ender_Eye_Name");
        rainbowLore = configuration.getString("Inventory.Items.Ender_Eye_Lore");
        aquaName = configuration.getString("Inventory.Items.Aqua_Glow_Name");
        darkAquaName = configuration.getString("Inventory.Items.Dark_Aqua_Glow_Name");
        redName = configuration.getString("Inventory.Items.Red_Glow_Name");
        darkRedName = configuration.getString("Inventory.Items.Dark_Red_Glow_Name");
        goldName = configuration.getString("Inventory.Items.Gold_Glow_Name");
        yellowName = configuration.getString("Inventory.Items.Yellow_Glow_Name");
        blueName = configuration.getString("Inventory.Items.Blue_Glow_Name");
        darkBlueName = configuration.getString("Inventory.Items.Dark_Blue_Glow_Name");
        greenName = configuration.getString("Inventory.Items.Green_Glow_Name");
        darkGreenName = configuration.getString("Inventory.Items.Dark_Green_Glow_Name");
        pinkName = configuration.getString("Inventory.Items.Pink_Glow_Name");
        purpleName = configuration.getString("Inventory.Items.Purple_Glow_Name");
        blackName = configuration.getString("Inventory.Items.Black_Glow_Name");
        darkGrayName = configuration.getString("Inventory.Items.Dark_Gray_Glow_Name");
        grayName = configuration.getString("Inventory.Items.Gray_Glow_Name");
        whiteName = configuration.getString("Inventory.Items.White_Glow_Name");
        headName = configuration.getString("Inventory.Items.Player_Head.Name");
        headLore = configuration.getString("Inventory.Items.Player_Head.Lore");
        colorLore = configuration.getString("Inventory.Items.Color_Lore");
        glowStatusTrue = configuration.getString("Messages.Glow_Status_True");
        glowStatusFalse = configuration.getString("Messages.Glow_Status_False");
        fillMaterialName = configuration.getString("Inventory.Items.Fill_Material_Name");
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }

    // Métodos para tener el valor de la config
    public String getDisabledWorldMessage(){ return disabledWorldMessage    ; }
    public String getNoPermissionMessage(){
        return noPermissionMessage;
    }
    public String getReloadConfigMessage(){
        return reloadConfigMessage;
    }
    public String getEnableGlow(){
        return enableGlow;
    }
    public String getDisableGlow(){
        return disableGlow;
    }
    public String getInventoryTittle(){
        return inventoryTittle;
    }
    public String getAquaName(){
        return aquaName;
    }
    public String getRedName(){
        return redName;
    }
    public String getDarkRedName(){
        return darkRedName;
    }
    public String getBlueName(){
        return blueName;
    }
    public String getGreenName(){
        return greenName;
    }
    public String getDarkGreenName(){
        return darkGreenName;
    }
    public String getPinkName(){
        return pinkName;
    }
    public String getPurpleName(){
        return purpleName;
    }
    public String getDarkGrayName(){
        return darkGrayName;
    }
    public String getGrayName(){
        return grayName;
    }
    public String getBlackName(){
        return blackName;
    }
    public String getWhiteName(){
        return whiteName;
    }
    public String getDarkAquaName(){
        return darkAquaName;
    }
    public String getDarkBlueName(){
        return darkBlueName;
    }
    public String getGoldName(){
        return goldName;
    }
    public String getYellowName(){
        return yellowName;
    }
    public String getHeadName(){
        return headName;
    }
    public String getHeadLore(){
        return headLore;
    }
    public String getColorLore(){
        return colorLore;
    }
    public String getFillMaterialName(){
        return fillMaterialName;
    }
    public String getGlowStatusTrue(){
        return glowStatusTrue;
    }
    public String getGlowStatusFalse(){
        return glowStatusFalse;
    }
    public String getEnderName(){
        return rainbowName;
    }
    public String getEnderLore(){
        return rainbowLore;
    }

}
