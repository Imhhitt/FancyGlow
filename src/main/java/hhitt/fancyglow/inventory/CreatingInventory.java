package hhitt.fancyglow.inventory;

import dev.dejvokep.boostedyaml.YamlDocument;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.managers.PlayerGlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import hhitt.fancyglow.utils.HeadUtils;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class CreatingInventory implements InventoryHolder {

    private final YamlDocument config;
    private final Inventory inventory;
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;
    private final PlayerGlowManager playerGlowManager;

    public CreatingInventory(FancyGlow plugin, Player player) {
        this.config = plugin.getConfiguration();
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
        this.playerGlowManager = plugin.getPlayerGlowManager();
        this.inventory = plugin.getServer().createInventory(this, 45, messageHandler.getMessage(Messages.INVENTORY_TITLE));

        // Set fill items.
        setFiller();

        // Set color items.
        int i = 9;
        for (ChatColor availableColor : glowManager.getAvailableColors()) {
            ItemStack colorItem = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta colorMeta = (LeatherArmorMeta) Objects.requireNonNull(colorItem.getItemMeta());

            colorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            colorMeta.addItemFlags(ItemFlag.HIDE_DYE);
            colorMeta.setLore(messageHandler.getMessages(Messages.COLOR_LORE));
            colorMeta.setDisplayName(messageHandler.getMessage(Messages.match(availableColor.name() + "_NAME")));
            colorMeta.setColor(ColorUtils.getArmorColorFromChatColor(availableColor));
            colorItem.setItemMeta(colorMeta);

            // Skip slot 18.
            if (i == 18) {
                i++;
            }
            inventory.setItem(i++, colorItem);
        }

        setRainbowItem();
        setFlashingItem(player);
        setPlayerStatusItem(player);
    }

    private void setFiller() {
        // Return if filler is not enabled.
        if (!config.getBoolean("Inventory.Filler.Enabled")) return;

        String fillerMaterial = config.getString("Inventory.Filler.Material", "GRAY_STAINED_GLASS_PANE");
        Material material = Material.matchMaterial(fillerMaterial);
        ItemStack fill = new ItemStack(Objects.requireNonNull(material));
        ItemMeta fillMeta = Objects.requireNonNull(fill.getItemMeta());
        fillMeta.setDisplayName(messageHandler.getMessage(Messages.FILLER_NAME));
        fill.setItemMeta(fillMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, fill);
        }
    }

    private void setFlashingItem(Player player) {
        // Flashing head
        String flashingTexture = config.getString("Inventory.Flashing.Texture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFjNTgxYThiNTk3NjkyYjViOTRkM2I4YmViOWM1MmY1Njk5OWQ2MmY3Mzk1NjY4ZmFjNTdhYzk1MmZlNGRjNCJ9fX0=");
        ItemStack flashingHead = HeadUtils.getCustomSkull(flashingTexture);
        ItemMeta flashingHeadMeta = Objects.requireNonNull(flashingHead.getItemMeta());

        flashingHeadMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        flashingHeadMeta.setDisplayName(messageHandler.getMessage(Messages.FLASHING_HEAD_NAME));
        flashingHeadMeta.setLore(messageHandler.getMessages(Messages.FLASHING_HEAD_LORE));
        flashingHead.setItemMeta(flashingHeadMeta);

        if (player.isGlowing() || playerGlowManager.findPlayerTeam(player) != null) {
            inventory.setItem(config.getInt("Inventory.Flashing.Slot", 40), flashingHead);
        }
    }

    private void setRainbowItem() {
        // Rainbow head
        String rainbowTexture = config.getString("Inventory.Rainbow.Texture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI0OTMyYmI5NDlkMGM2NTcxN2IxMjFjOGNkOWEyMWI2OWU4NmMwZjdlMzQyMWFlOWI4YzY0ZDhiOTkwZWI2MCJ9fX0");
        ItemStack rainbowHead = HeadUtils.getCustomSkull(rainbowTexture);
        ItemMeta rainbowHeadMeta = Objects.requireNonNull(rainbowHead.getItemMeta());

        rainbowHeadMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rainbowHeadMeta.setDisplayName(messageHandler.getMessage(Messages.RAINBOW_HEAD_NAME));
        rainbowHeadMeta.setLore(messageHandler.getMessages(Messages.RAINBOW_HEAD_LORE));
        rainbowHead.setItemMeta(rainbowHeadMeta);

        inventory.setItem(config.getInt("Inventory.Rainbow.Slot", 39), rainbowHead);
    }

    private void setPlayerStatusItem(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) Objects.requireNonNull(head.getItemMeta());
        meta.setDisplayName(messageHandler.getMessage(Messages.HEAD_NAME));
        meta.setLore(messageHandler.getMessages(Messages.HEAD_LORE));

        meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        playerGlowManager.updateItemLore(head, player);

        inventory.setItem(config.getInt("Inventory.Status.Slot", 41), head);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
