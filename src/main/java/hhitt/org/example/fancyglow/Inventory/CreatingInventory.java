package hhitt.org.example.fancyglow.Inventory;

import hhitt.org.example.fancyglow.FancyGlow;
import hhitt.org.example.fancyglow.Utils.MessageUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CreatingInventory implements InventoryHolder {

    private final Inventory inventory;

    public CreatingInventory(FancyGlow plugin){
        this.inventory = plugin.getServer().createInventory(this, 36,
                MessageUtils.getColoredMessages(plugin.getMainConfigManager().getInventoryTittle()));
    }

    @Override
    public Inventory getInventory(){
        return this.inventory;
    }
}
