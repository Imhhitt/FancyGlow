package hhitt.fancyglow.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.MessageHandler;
import hhitt.fancyglow.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

@Command(name = "glow", aliases = "fancyglow")
@Description("Main command for FancyGlow")
public class MainCommand {

    private final FancyGlow plugin;
    private final GlowManager glowManager;
    private final MessageHandler messageHandler;

    public MainCommand(FancyGlow plugin) {
        this.plugin = plugin;
        this.glowManager = plugin.getGlowManager();
        this.messageHandler = plugin.getMessageHandler();
    }

    @Execute
    public void command(@Context Player player) {
        // Returns if disabled so player use its own menus.
        if (!plugin.getConfiguration().getBoolean("Open_Glow_Menu")) return;

        // Prevent command usage in target worlds
        List<String> noAllowedWorlds = plugin.getConfiguration().getStringList("Disabled_Worlds");
        if (noAllowedWorlds.contains(player.getWorld().getName())) {
            messageHandler.sendMessage(player, Messages.DISABLED_WORLD);
            return;
        }

        // Check gui permissions
        if (!player.hasPermission("fancyglow.command.gui")) {
            messageHandler.sendMessage(player, Messages.NO_PERMISSION);
            return;
        }

        // Prepare and open the gui
        plugin.getInventory().openInventory(player);
    }

    @Execute(name = "reload")
    @Permission("fancyglow.command.reload")
    @Description("FancyGlow reload sub-command.")
    public void reloadCommand(@Context CommandSender sender) {
        if (!sender.hasPermission("fancyglow.command.reload")) {
            messageHandler.sendMessage(sender, Messages.NO_PERMISSION);
            return;
        }

        glowManager.stopFlashingTask();
        glowManager.stopMulticolorTask();

        try {
            plugin.getConfiguration().reload();
        } catch (IOException e) {
            // No longer using logger as field, since this is the only place its being used.
            plugin.getLogger().severe("Unexpected exception during configuration-reload with the following message: " + e.getMessage());
        } finally {
            // Re-initialize inventory only when reloading, probably not the best way to do it.
            plugin.getInventory().setupContent();

            glowManager.scheduleFlashingTask();
            glowManager.scheduleMulticolorTask();

            messageHandler.sendMessage(sender, Messages.RELOADED);
        }
    }
}
