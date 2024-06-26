package hhitt.fancyglow.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class HeadUtils {

    public static ItemStack getCustomSkull(String textureUrl) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        if (textureUrl == null || textureUrl.isEmpty()) {
            return skull;
        }

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwnerProfile(Bukkit.createPlayerProfile(UUID.randomUUID(), null));
            ItemMeta itemMeta = skull.getItemMeta();

            skullMeta.setOwnerProfile(Bukkit.createPlayerProfile(UUID.randomUUID(), textureUrl));
            skull.setItemMeta(itemMeta);
        }
        return skull;
    }
}
