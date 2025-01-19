package hhitt.fancyglow.inventory;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.HeadUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class CreatingInventory implements InventoryHolder {

    // Paper inventory holder to manage the gui creation and others

    private final Inventory inventory;
    private final FancyGlow plugin;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;
    private final String customTextureUrl =
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI0OTMyYmI5NDlkMGM2NTcxN2IxMjFjOGNkOWEyMWI2OWU4NmMwZjdlMzQyMWFlOWI4YzY0ZDhiOTkwZWI2MCJ9fX0=";

    public CreatingInventory(FancyGlow plugin, Player sender) {
        this.plugin = plugin;
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
        this.inventory = plugin.getServer().createInventory(this, 45, messageHandler.getMessage(Messages.INVENTORY_TITLE));

        // Player head
        ItemStack playerHead = getPlayerHead(sender);
        inventory.setItem(41, playerHead);

        // Rainbow head
        ItemStack rainbowHead = HeadUtils.getCustomSkull(customTextureUrl);
        ItemMeta rainbowHeadMeta = rainbowHead.getItemMeta();

        assert rainbowHeadMeta != null;
        rainbowHeadMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rainbowHeadMeta.addItemFlags(ItemFlag.HIDE_DYE);
        rainbowHeadMeta.setDisplayName(messageHandler.getMessage(Messages.RAINBOW_HEAD_NAME));
        rainbowHeadMeta.setLore(messageHandler.getMessages(Messages.RAINBOW_HEAD_LORE));
        rainbowHead.setItemMeta(rainbowHeadMeta);

        inventory.setItem(39, rainbowHead);

        // Buttons
        ItemStack red = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack darkRed = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack gold = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack yellow = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack darkGreen = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack green = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack aqua = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack darkAqua = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack darkBlue = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack blue = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack pink = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack purple = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack black = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack darkGray = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack gray = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack white = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        // Button data
        LeatherArmorMeta darkRedMeta = (LeatherArmorMeta) darkRed.getItemMeta();
        LeatherArmorMeta redMeta = (LeatherArmorMeta) red.getItemMeta();
        LeatherArmorMeta goldMeta = (LeatherArmorMeta) gold.getItemMeta();
        LeatherArmorMeta yellowMeta = (LeatherArmorMeta) yellow.getItemMeta();
        LeatherArmorMeta darkGreenMeta = (LeatherArmorMeta) darkGreen.getItemMeta();
        LeatherArmorMeta greenMeta = (LeatherArmorMeta) green.getItemMeta();
        LeatherArmorMeta aquaMeta = (LeatherArmorMeta) aqua.getItemMeta();
        LeatherArmorMeta darkAquaMeta = (LeatherArmorMeta) darkAqua.getItemMeta();
        LeatherArmorMeta darkBlueMeta = (LeatherArmorMeta) darkBlue.getItemMeta();
        LeatherArmorMeta blueMeta = (LeatherArmorMeta) blue.getItemMeta();
        LeatherArmorMeta pinkMeta = (LeatherArmorMeta) pink.getItemMeta();
        LeatherArmorMeta purpleMeta = (LeatherArmorMeta) purple.getItemMeta();
        LeatherArmorMeta blackMeta = (LeatherArmorMeta) black.getItemMeta();
        LeatherArmorMeta darkGrayMeta = (LeatherArmorMeta) darkGray.getItemMeta();
        LeatherArmorMeta grayMeta = (LeatherArmorMeta) gray.getItemMeta();
        LeatherArmorMeta whiteMeta = (LeatherArmorMeta) white.getItemMeta();
        ItemMeta fillMeta = fill.getItemMeta();

        // Hide attributes and set lore
        assert darkRedMeta != null;
        darkRedMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkRedMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkRedMeta.setLore(messageHandler.getMessages(sender, Messages.COLOR_LORE));

        assert redMeta != null;
        redMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        redMeta.addItemFlags(ItemFlag.HIDE_DYE);
        redMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert goldMeta != null;
        goldMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        goldMeta.addItemFlags(ItemFlag.HIDE_DYE);
        goldMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert yellowMeta != null;
        yellowMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        yellowMeta.addItemFlags(ItemFlag.HIDE_DYE);
        yellowMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert darkGreenMeta != null;
        darkGreenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkGreenMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkGreenMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert greenMeta != null;
        greenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        greenMeta.addItemFlags(ItemFlag.HIDE_DYE);
        greenMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert aquaMeta != null;
        aquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        aquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
        aquaMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert darkAquaMeta != null;
        darkAquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkAquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkAquaMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert darkBlueMeta != null;
        darkBlueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkBlueMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkBlueMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert blueMeta != null;
        blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blueMeta.addItemFlags(ItemFlag.HIDE_DYE);
        blueMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert pinkMeta != null;
        pinkMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pinkMeta.addItemFlags(ItemFlag.HIDE_DYE);
        pinkMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert purpleMeta != null;
        purpleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        purpleMeta.addItemFlags(ItemFlag.HIDE_DYE);
        purpleMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert blackMeta != null;
        blackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blackMeta.addItemFlags(ItemFlag.HIDE_DYE);
        blackMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert darkGrayMeta != null;
        darkGrayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkGrayMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkGrayMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert grayMeta != null;
        grayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        grayMeta.addItemFlags(ItemFlag.HIDE_DYE);
        grayMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        assert whiteMeta != null;
        whiteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        whiteMeta.addItemFlags(ItemFlag.HIDE_DYE);
        whiteMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));

        // Names of the glows
        darkRedMeta.setDisplayName(messageHandler.getMessage(Messages.DARK_RED_NAME));
        darkRedMeta.setColor(Color.MAROON);

        redMeta.setDisplayName(messageHandler.getMessage(Messages.RED_NAME));
        redMeta.setColor(Color.RED);

        goldMeta.setDisplayName(messageHandler.getMessage(Messages.GOLD_NAME));
        goldMeta.setColor(Color.ORANGE);

        yellowMeta.setDisplayName(messageHandler.getMessage(Messages.YELLOW_NAME));
        yellowMeta.setColor(Color.YELLOW);

        darkGreenMeta.setDisplayName(messageHandler.getMessage(Messages.DARK_GREEN_NAME));
        darkGreenMeta.setColor(Color.GREEN);

        greenMeta.setDisplayName(messageHandler.getMessage(Messages.GREEN_NAME));
        greenMeta.setColor(Color.LIME);

        aquaMeta.setDisplayName(messageHandler.getMessage(Messages.AQUA_NAME));
        aquaMeta.setColor(Color.AQUA);

        darkAquaMeta.setDisplayName(messageHandler.getMessage(Messages.DARK_AQUA_NAME));
        darkAquaMeta.setColor(Color.TEAL);

        darkBlueMeta.setDisplayName(messageHandler.getMessage(Messages.DARK_BLUE_NAME));
        darkBlueMeta.setColor(Color.NAVY);

        blueMeta.setDisplayName(messageHandler.getMessage(Messages.BLUE_NAME));
        blueMeta.setColor(Color.BLUE);

        pinkMeta.setDisplayName(messageHandler.getMessage(Messages.PINK_NAME));
        pinkMeta.setColor(Color.FUCHSIA);

        purpleMeta.setDisplayName(messageHandler.getMessage(Messages.PURPLE_NAME));
        purpleMeta.setColor(Color.PURPLE);

        blackMeta.setDisplayName(messageHandler.getMessage(Messages.BLACK_NAME));
        blackMeta.setColor(Color.BLACK);

        darkGrayMeta.setDisplayName(messageHandler.getMessage(Messages.DARK_GRAY_NAME));
        darkGrayMeta.setColor(Color.GRAY);

        grayMeta.setDisplayName(messageHandler.getMessage(Messages.GRAY_NAME));
        grayMeta.setColor(Color.SILVER);

        whiteMeta.setDisplayName(messageHandler.getMessage(Messages.WHITE_NAME));
        whiteMeta.setColor(Color.WHITE);

        assert fillMeta != null;
        fillMeta.setDisplayName(messageHandler.getMessage(Messages.FILL_MATERIAL_NAME));

        // Save the assigned data
        darkRed.setItemMeta(darkRedMeta);
        red.setItemMeta(redMeta);
        gold.setItemMeta(goldMeta);
        yellow.setItemMeta(yellowMeta);
        darkGreen.setItemMeta(darkGreenMeta);
        green.setItemMeta(greenMeta);
        aqua.setItemMeta(aquaMeta);
        darkAqua.setItemMeta(darkAquaMeta);
        darkBlue.setItemMeta(darkBlueMeta);
        blue.setItemMeta(blueMeta);
        purple.setItemMeta(purpleMeta);
        pink.setItemMeta(pinkMeta);
        black.setItemMeta(blackMeta);
        darkGray.setItemMeta(darkGrayMeta);
        gray.setItemMeta(grayMeta);
        white.setItemMeta(whiteMeta);
        fill.setItemMeta(fillMeta);

        // Place items on the buttons
        // TODO Better inventory layout
        inventory.setItem(0, fill);
        inventory.setItem(1, fill);
        inventory.setItem(2, fill);
        inventory.setItem(3, fill);
        inventory.setItem(4, fill);
        inventory.setItem(5, fill);
        inventory.setItem(6, fill);
        inventory.setItem(7, fill);
        inventory.setItem(8, fill);
        inventory.setItem(9, darkRed);
        inventory.setItem(10, red);
        inventory.setItem(11, gold);
        inventory.setItem(12, yellow);
        inventory.setItem(13, darkGreen);
        inventory.setItem(14, green);
        inventory.setItem(15, aqua);
        inventory.setItem(16, darkAqua);
        inventory.setItem(17, darkBlue);
        inventory.setItem(18, fill);
        inventory.setItem(19, blue);
        inventory.setItem(20, pink);
        inventory.setItem(21, purple);
        inventory.setItem(22, black);
        inventory.setItem(23, darkGray);
        inventory.setItem(24, gray);
        inventory.setItem(25, white);
        inventory.setItem(26, fill);
        inventory.setItem(27, fill);
        inventory.setItem(28, fill);
        inventory.setItem(29, fill);
        inventory.setItem(30, fill);
        inventory.setItem(31, fill);
        inventory.setItem(32, fill);
        inventory.setItem(33, fill);
        inventory.setItem(34, fill);
        inventory.setItem(35, fill);
        inventory.setItem(36, fill);
        inventory.setItem(37, fill);
        inventory.setItem(38, fill);
        inventory.setItem(40, fill);
        inventory.setItem(42, fill);
        inventory.setItem(43, fill);
        inventory.setItem(44, fill);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    private ItemStack getPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        assert meta != null;
        meta.setDisplayName(messageHandler.getMessage(Messages.HEAD_NAME));
        meta.setLore(messageHandler.getMessages(Messages.HEAD_LORE));

        meta.setOwningPlayer(player);
        head.setItemMeta(meta);

        // Update the item's lore with the player's glow status
        playerGlowManager.updateItemLore(head, player);

        return head;
    }
}
