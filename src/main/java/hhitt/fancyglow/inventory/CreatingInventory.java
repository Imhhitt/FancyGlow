package hhitt.fancyglow.inventory;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.HeadUtils;
import hhitt.fancyglow.utils.MessageUtils;
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

import java.util.Collections;

public class CreatingInventory implements InventoryHolder {

    // Paper inventory holder to manage the gui creation and others

    private final Inventory inventory;
    private final FancyGlow plugin;
    private final PlayerGlowManager playerGlowManager;
    private final String customTextureUrl =
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI0OTMyYmI5NDlkMGM2NTcxN2IxMjFjOGNkOWEyMWI2OWU4NmMwZjdlMzQyMWFlOWI4YzY0ZDhiOTkwZWI2MCJ9fX0=";

    public CreatingInventory(FancyGlow plugin, Player sender) {
        this.plugin = plugin;
        this.playerGlowManager = plugin.getPlayerGlowManager();
        this.inventory = plugin.getServer().createInventory(this, 45,
                MessageUtils.miniMessageParse(plugin.getMainConfigManager().getInventoryTittle()));

        // Player head
        ItemStack playerHead = getPlayerHead(sender);
        inventory.setItem(41, playerHead);

        // Rainbow head
        ItemStack rainbowHead = HeadUtils.getCustomSkull(customTextureUrl);
        ItemMeta rainbowHeadMeta = rainbowHead.getItemMeta();

        assert rainbowHeadMeta != null;
        rainbowHeadMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rainbowHeadMeta.addItemFlags(ItemFlag.HIDE_DYE);
        rainbowHeadMeta.setDisplayName(MessageUtils.miniMessageParse(
                plugin.getConfig().getString("Inventory.Items.Rainbow_Head")));
        rainbowHeadMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getConfig().getString("Inventory.Items.Rainbow_Head_Lore"))));
        rainbowHead.setItemMeta(rainbowHeadMeta);

        inventory.setItem(39, rainbowHead);

        // Botones
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

        // Datos de botones.
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

        // Ocultar atributos y poner lore
        assert darkRedMeta != null;
        darkRedMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkRedMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkRedMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert redMeta != null;
        redMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        redMeta.addItemFlags(ItemFlag.HIDE_DYE);
        redMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert goldMeta != null;
        goldMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        goldMeta.addItemFlags(ItemFlag.HIDE_DYE);
        goldMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert yellowMeta != null;
        yellowMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        yellowMeta.addItemFlags(ItemFlag.HIDE_DYE);
        yellowMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert darkGreenMeta != null;
        darkGreenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkGreenMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkGreenMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert greenMeta != null;
        greenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        greenMeta.addItemFlags(ItemFlag.HIDE_DYE);
        greenMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert aquaMeta != null;
        aquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        aquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
        aquaMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert darkAquaMeta != null;
        darkAquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkAquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkAquaMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert darkBlueMeta != null;
        darkBlueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkBlueMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkBlueMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert blueMeta != null;
        blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blueMeta.addItemFlags(ItemFlag.HIDE_DYE);
        blueMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert pinkMeta != null;
        pinkMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pinkMeta.addItemFlags(ItemFlag.HIDE_DYE);
        pinkMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert purpleMeta != null;
        purpleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        purpleMeta.addItemFlags(ItemFlag.HIDE_DYE);
        purpleMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert blackMeta != null;
        blackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        blackMeta.addItemFlags(ItemFlag.HIDE_DYE);
        blackMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert darkGrayMeta != null;
        darkGrayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        darkGrayMeta.addItemFlags(ItemFlag.HIDE_DYE);
        darkGrayMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert grayMeta != null;
        grayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        grayMeta.addItemFlags(ItemFlag.HIDE_DYE);
        grayMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        assert whiteMeta != null;
        whiteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        whiteMeta.addItemFlags(ItemFlag.HIDE_DYE);
        whiteMeta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getColorLore())));

        // Nombres de los destellos
        darkRedMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getDarkRedName()));
        darkRedMeta.setColor(Color.MAROON);

        redMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getRedName()));
        redMeta.setColor(Color.RED);

        goldMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGoldName()));
        goldMeta.setColor(Color.ORANGE);

        yellowMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getYellowName()));
        yellowMeta.setColor(Color.YELLOW);

        darkGreenMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getDarkGreenName()));
        darkGreenMeta.setColor(Color.GREEN);

        greenMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGreenName()));
        greenMeta.setColor(Color.LIME);

        aquaMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getAquaName()));
        aquaMeta.setColor(Color.AQUA);

        darkAquaMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getDarkAquaName()));
        darkAquaMeta.setColor(Color.TEAL);

        darkBlueMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getDarkBlueName()));
        darkBlueMeta.setColor(Color.NAVY);

        blueMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getBlueName()));
        blueMeta.setColor(Color.BLUE);

        pinkMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getPinkName()));
        pinkMeta.setColor(Color.FUCHSIA);

        purpleMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getPurpleName()));
        purpleMeta.setColor(Color.PURPLE);

        blackMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getBlackName()));
        blackMeta.setColor(Color.BLACK);

        darkGrayMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getDarkGrayName()));
        darkGrayMeta.setColor(Color.GRAY);

        grayMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getGrayName()));
        grayMeta.setColor(Color.SILVER);

        whiteMeta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getWhiteName()));
        whiteMeta.setColor(Color.WHITE);

        assert fillMeta != null;
        fillMeta.setDisplayName(MessageUtils.miniMessageParse(
                plugin.getMainConfigManager().getFillMaterialName()));


        // Guardamos los datos asignados
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

        // Colocar items en los botones
        // No, no soy imbecil, es sólo que cambiaré esto en poco por un inventario más "guay"
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
        meta.setDisplayName(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getHeadName()));
        meta.setLore(Collections.singletonList(MessageUtils.miniMessageParse(plugin.getMainConfigManager().getHeadLore())));

        meta.setOwningPlayer(player);
        head.setItemMeta(meta);

        // Actualiza el lore del item con el estado de brillo del jugador
        playerGlowManager.updateItemLore(head, player);

        return head;
    }
}
