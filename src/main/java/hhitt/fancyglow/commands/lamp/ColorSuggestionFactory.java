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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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

        Set<String> availableColors = new HashSet<>(ColorUtils.getAvailableColorsSet());

        // Add optional modes.
        availableColors.add("rainbow");
        availableColors.add("flashing");

        return context -> {
            BukkitCommandActor actor = context.actor();
            if (actor.isConsole()) return availableColors;

            Player player = Objects.requireNonNull(actor.asPlayer());
            List<String> list = new ArrayList<>();
            for (String name : availableColors) {
                if (glowManager.hasGlowPermission(player, name)) {
                    list.add(name);
                }
            }
            return list;
        };
    }
}
