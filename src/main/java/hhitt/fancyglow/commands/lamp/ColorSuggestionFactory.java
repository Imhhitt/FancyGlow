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
import java.util.List;
import java.util.stream.Collectors;

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
        ColorSuggestion colorSuggestion = annotations.get(ColorSuggestion.class);
        if (colorSuggestion == null) return null;

        List<String> availableColors = ColorUtils.getChatColorValues()
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return context -> {
            BukkitCommandActor actor = context.actor();

            if (actor.isPlayer()) {
                Player player = actor.asPlayer();

                if (glowManager.hasGlowPermission(player, "rainbow")) {
                    availableColors.add("rainbow");
                }

                return availableColors
                        .stream()
                        .filter(name -> glowManager.hasGlowPermission(player, name))
                        .collect(Collectors.toList());
            }
            return availableColors;
        };
    }
}
