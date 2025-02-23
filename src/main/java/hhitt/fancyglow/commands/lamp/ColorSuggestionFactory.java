package hhitt.fancyglow.commands.lamp;

import hhitt.fancyglow.FancyGlow;
import hhitt.fancyglow.managers.GlowManager;
import hhitt.fancyglow.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ColorSuggestionFactory implements SuggestionProvider.Factory<BukkitCommandActor> {

    private final GlowManager glowManager;

    public ColorSuggestionFactory(FancyGlow plugin) {
        this.glowManager = plugin.getGlowManager();
    }

    @Override
    public @org.jetbrains.annotations.Nullable SuggestionProvider<BukkitCommandActor> create(
            @NotNull Type type,
            @NotNull AnnotationList annotations,
            @NotNull Lamp<BukkitCommandActor> lamp
    ) {
        // Verify if annotation is present.
        if (annotations.get(ColorSuggestion.class) == null) return null;

        // Just reuse available-colors set instead of create a new one.
        Set<String> availableColors = ColorUtils.getAvailableColorsSet();
        return context -> {
            // Basically, we reduce a bit the object-alloc and we avoid us an extra method-call for a
            // simple [instanceof] check.
            if (!(context.actor().sender() instanceof Player player)) return availableColors;

            List<String> list = new ArrayList<>(availableColors.size());
            for (String name : availableColors) {
                if (glowManager.hasGlowPermission(player, name)) {
                    list.add(name);
                }
            }
            return list;
        };
    }
}
