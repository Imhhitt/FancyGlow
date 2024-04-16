package hhitt.org.example.fancyglow.Config;

import hhitt.org.example.fancyglow.FancyGlow;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfigManager {
    private final MainConfig configFile;
    private String noPermissionMessage;
    private String enableGlow;
    private String disableGlow;
    private String inventoryTittle;
    private String aquaName;
    private String redName;
    private String blueName;
    private String limeName;
    private String pinkName;
    private String blackName;
    private String whiteName;
    private String headName;
    private String headLore;
    private String glowStatusTrue;
    private String glowStatusFalse;
    private String fillMaterialName;
    private String colorLore;


    // Cargar la configuración
    public MainConfigManager(FancyGlow FancyGlow) {
        configFile = new MainConfig("config.yml", null, FancyGlow);
        configFile.registerConfig();
        loadConfig();
    }

    // Cargar los mensajes de la config en variables
    public void loadConfig(){
        FileConfiguration configuration = configFile.getConfig();
        noPermissionMessage = configuration.getString("Messages.No_Permission");
        enableGlow = configuration.getString("Messages.Enable_Glow");
        disableGlow = configuration.getString("Messages.Disable_Glow");
        inventoryTittle = configuration.getString("Inventory.Tittle");
        aquaName = configuration.getString("Inventory.Items.Aqua_Glow_Name");
        redName = configuration.getString("Inventory.Items.Red_Glow_Name");
        blueName = configuration.getString("Inventory.Items.Blue_Glow_Name");
        limeName = configuration.getString("Inventory.Items.Lime_Glow_Name");
        pinkName = configuration.getString("Inventory.Items.Pink_Glow_Name");
        blackName = configuration.getString("Inventory.Items.Black_Glow_Name");
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

    public String getNoPermissionMessage(){
        return noPermissionMessage;
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
    public String getBlueName(){
        return blueName;
    }
    public String getLimeName(){
        return limeName;
    }
    public String getPinkName(){
        return pinkName;
    }
    public String getBlackName(){
        return blackName;
    }
    public String getWhiteName(){
        return whiteName;
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


    public String getString(String s) {
        return s;
    }
}
