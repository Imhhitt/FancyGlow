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
                ItemStack red = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack blue = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack lime = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack pink = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack black = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack white = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

                // Datos de botones.
                LeatherArmorMeta aquaMeta = (LeatherArmorMeta) aqua.getItemMeta();
                LeatherArmorMeta redMeta = (LeatherArmorMeta) red.getItemMeta();
                LeatherArmorMeta blueMeta = (LeatherArmorMeta) blue.getItemMeta();
                LeatherArmorMeta limeMeta = (LeatherArmorMeta) lime.getItemMeta();
                LeatherArmorMeta pinkMeta = (LeatherArmorMeta) pink.getItemMeta();
                LeatherArmorMeta blackMeta = (LeatherArmorMeta) black.getItemMeta();
                LeatherArmorMeta whiteMeta = (LeatherArmorMeta) white.getItemMeta();
                ItemMeta fillMeta = fill.getItemMeta();

                // Ocultar atributos y poner lore
                aquaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                aquaMeta.addItemFlags(ItemFlag.HIDE_DYE);
                aquaMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                redMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                redMeta.addItemFlags(ItemFlag.HIDE_DYE);
                redMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                blueMeta.addItemFlags(ItemFlag.HIDE_DYE);
                blueMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                limeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                limeMeta.addItemFlags(ItemFlag.HIDE_DYE);
                limeMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                pinkMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                pinkMeta.addItemFlags(ItemFlag.HIDE_DYE);
                pinkMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                blackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                blackMeta.addItemFlags(ItemFlag.HIDE_DYE);
                blackMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));
                whiteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                whiteMeta.addItemFlags(ItemFlag.HIDE_DYE);
                whiteMeta.setLore(Collections.singletonList(MessageUtils.getColoredMessages(
                        plugin.getMainConfigManager().getColorLore())));

                // Nombres de los destellos
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
