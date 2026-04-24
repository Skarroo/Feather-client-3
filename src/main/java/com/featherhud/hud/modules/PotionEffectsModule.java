package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotionEffectsModule extends HudModule {

    public PotionEffectsModule() {
        super("potion_effects", "Potion Effects", 210, 60);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
        if (effects.isEmpty()) return;

        List<String> lines = new ArrayList<>();
        for (StatusEffectInstance effect : effects) {
            String name = effect.getEffectType().value().getName().getString();
            int amplifier = effect.getAmplifier();
            String level = amplifier > 0 ? " " + toRoman(amplifier + 1) : "";

            int ticks = effect.getDuration();
            String duration;
            if (ticks == Integer.MAX_VALUE) {
                duration = "∞";
            } else {
                int sec = ticks / 20;
                duration = String.format("%d:%02d", sec / 60, sec % 60);
            }

            lines.add(name + level + "  " + duration);
        }

        drawLines(ctx, client, lines);
    }

    private static String toRoman(int n) {
        return switch (n) {
            case 2  -> "II";
            case 3  -> "III";
            case 4  -> "IV";
            case 5  -> "V";
            default -> String.valueOf(n);
        };
    }

    @Override
    public int getWidth(MinecraftClient client)  { return 120; }

    @Override
    public int getHeight(MinecraftClient client) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return 13;
        int count = Math.max(1, mc.player.getStatusEffects().size());
        return count * 10 + 3;
    }
}
