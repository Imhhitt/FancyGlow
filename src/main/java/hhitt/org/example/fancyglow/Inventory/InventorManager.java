package hhitt.org.example.fancyglow.Inventory;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Utils.IsGlowingVariable;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class InventorManager implements CommandExecutor {

    private final FancyGlow plugin;
    private final IsGlowingVariable isGlowingVariable;

    public InventorManager(FancyGlow plugin, IsGlowingVariable isGlowingVariable){
        this.plugin = plugin;
        this.isGlowingVariable = isGlowingVariable;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("fancyglow.command")) {
            sender.sendMessage(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getNoPermissionMessage()));
        } else {
            if (sender instanceof Player) {

                CreatingInventory inventory = new CreatingInventory(plugin);

                // Colocar la cabeza del jugador
                ItemStack playerHead = getPlayerHead((Player) sender);
                inventory.getInventory().setItem(31, playerHead);

                // Botones
                ItemStack aqua = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack darkAqua = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack blue = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack darkBlue = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack red = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack darkRed = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack yellow = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack gold = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack lime = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack green = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack pink = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack purple = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack black = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack gray = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack darkGray = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack white = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

                // Datos de botones.
                LeatherArmorMeta aquaMeta = (LeatherArmorMeta) aqua.getItemMeta();
                LeatherArmorMeta darkAquaMeta = (LeatherArmorMeta) darkAqua.getItemMeta();
                LeatherArmorMeta blueMeta = (LeatherArmorMeta) blue.getItemMeta();
                LeatherArmorMeta darkBlueMeta = (LeatherArmorMeta) darkBlue.getItemMeta();
                LeatherArmorMeta redMeta = (LeatherArmorMeta) red.getItemMeta();
                LeatherArmorMeta darkRedMeta = (LeatherArmorMeta) darkRed.getItemMeta();
                LeatherArmorMeta yellowMeta = (LeatherArmorMeta) yellow.getItemMeta();
                LeatherArmorMeta goldMeta = (LeatherArmorMeta) gold.getItemMeta();
                LeatherArmorMeta limeMeta = (LeatherArmorMeta) lime.getItemMeta();
                LeatherArmorMeta greenMeta = (LeatherArmorMeta) green.getItemMeta();
                LeatherArmorMeta pinkMeta = (LeatherArmorMeta) pink.getItemMeta();
                LeatherArmorMeta purpleMeta = (LeatherArmorMeta) purple.getItemMeta();
                LeatherArmorMeta blackMeta = (LeatherArmorMeta) black.getItemMeta();
                LeatherArmorMeta darkGrayMeta = (LeatherArmorMeta) darkGray.getItemMeta();
                LeatherArmorMeta grayMeta = (LeatherArmorMeta) gray.getItemMeta();
                LeatherArmorMeta whiteMeta = (LeatherArmorMeta) white.getItemMeta();
                ItemMeta fillMeta = fill.getItemMeta();

                // Ocultar atributos y poner lore
                assert aquaMeta != null;
                aquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                aquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
                aquaMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert darkAquaMeta != null;
                darkAquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                darkAquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
                darkAquaMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert blueMeta != null;
                blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                blueMeta.addItemFlags(ItemFlag.HIDE_DYE);
                blueMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert darkBlueMeta != null;
                darkBlueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                darkBlueMeta.addItemFlags(ItemFlag.HIDE_DYE);
                darkBlueMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert redMeta != null;
                redMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                redMeta.addItemFlags(ItemFlag.HIDE_DYE);
                redMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert darkRedMeta != null;
                darkRedMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                darkRedMeta.addItemFlags(ItemFlag.HIDE_DYE);
                darkRedMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert yellowMeta != null;
                yellowMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                yellowMeta.addItemFlags(ItemFlag.HIDE_DYE);
                yellowMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert goldMeta != null;
                goldMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                goldMeta.addItemFlags(ItemFlag.HIDE_DYE);
                goldMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert limeMeta != null;
                limeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                limeMeta.addItemFlags(ItemFlag.HIDE_DYE);
                limeMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert greenMeta != null;
                greenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                greenMeta.addItemFlags(ItemFlag.HIDE_DYE);
                greenMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert pinkMeta != null;
                pinkMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                pinkMeta.addItemFlags(ItemFlag.HIDE_DYE);
                pinkMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert purpleMeta != null;
                purpleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                purpleMeta.addItemFlags(ItemFlag.HIDE_DYE);
                purpleMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert blackMeta != null;
                blackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                blackMeta.addItemFlags(ItemFlag.HIDE_DYE);
                blackMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert darkGrayMeta != null;
                darkGrayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                darkGrayMeta.addItemFlags(ItemFlag.HIDE_DYE);
                darkGrayMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert grayMeta != null;
                grayMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                grayMeta.addItemFlags(ItemFlag.HIDE_DYE);
                grayMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                assert whiteMeta != null;
                whiteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                whiteMeta.addItemFlags(ItemFlag.HIDE_DYE);
                whiteMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));

                // Nombres de los destellos y color del armor
                aquaMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getAquaName()));
                aquaMeta.setColor(Color.AQUA);

                redMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getRedName()));
                redMeta.setColor(Color.RED);

                blueMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getBlueName()));
                blueMeta.setColor(Color.BLUE);

                limeMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getLimeName()));
                limeMeta.setColor(Color.LIME);

                pinkMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getPinkName()));
                pinkMeta.setColor(Color.PURPLE);

                blackMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getBlackName()));
                blackMeta.setColor(Color.BLACK);

                whiteMeta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getWhiteName()));
                whiteMeta.setColor(Color.WHITE);

                assert fillMeta != null;
                fillMeta.setDisplayName(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getFillMaterialName()));


                // Guardamos los datos asignados
                aqua.setItemMeta(aquaMeta);
                red.setItemMeta(redMeta);
                blue.setItemMeta(blueMeta);
                lime.setItemMeta(limeMeta);
                pink.setItemMeta(pinkMeta);
                black.setItemMeta(blackMeta);
                white.setItemMeta(whiteMeta);
                fill.setItemMeta(fillMeta);

                // Colocar items en los botones
                // No, no soy imbecil, es sólo que cambiaré esto en poco por un inventario más "guay"
                inventory.getInventory().setItem(0, fill);
                inventory.getInventory().setItem(1, fill);
                inventory.getInventory().setItem(2, fill);
                inventory.getInventory().setItem(3, fill);
                inventory.getInventory().setItem(4, fill);
                inventory.getInventory().setItem(5, fill);
                inventory.getInventory().setItem(6, fill);
                inventory.getInventory().setItem(7, fill);
                inventory.getInventory().setItem(8, fill);
                inventory.getInventory().setItem(9, fill);
                inventory.getInventory().setItem(10, aqua);
                inventory.getInventory().setItem(11, red);
                inventory.getInventory().setItem(12, blue);
                inventory.getInventory().setItem(13, lime);
                inventory.getInventory().setItem(14, pink);
                inventory.getInventory().setItem(15, black);
                inventory.getInventory().setItem(16, white);
                inventory.getInventory().setItem(17, fill);
                inventory.getInventory().setItem(18, fill);
                inventory.getInventory().setItem(19, fill);
                inventory.getInventory().setItem(20, fill);
                inventory.getInventory().setItem(21, fill);
                inventory.getInventory().setItem(22, fill);
                inventory.getInventory().setItem(23, fill);
                inventory.getInventory().setItem(24, fill);
                inventory.getInventory().setItem(25, fill);
                inventory.getInventory().setItem(26, fill);
                inventory.getInventory().setItem(27, fill);
                inventory.getInventory().setItem(28, fill);
                inventory.getInventory().setItem(29, fill);
                inventory.getInventory().setItem(30, fill);
                inventory.getInventory().setItem(32, fill);
                inventory.getInventory().setItem(33, fill);
                inventory.getInventory().setItem(34, fill);
                inventory.getInventory().setItem(35, fill);


                ((Player) sender).openInventory(inventory.getInventory());
            }
            return true;
        }
        return true;
    }


    // Obtener la cabeza del jugador
    private ItemStack getPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getHeadName()));
        meta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(plugin.getMainConfigManager().getHeadLore())));

        // Comprobación de que el ItemMeta no sea nulo
        if (meta != null) {
            meta.setOwningPlayer(player);
            head.setItemMeta(meta);

            // Actualiza el lore del item con el estado de brillo del jugador
            IsGlowingVariable.updateItemLore(head, player);
        }

        return head;
    }




}
