package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public class BiomeModule extends HudModule {

    public BiomeModule() {
        super("biome", "Biome", 2, 38);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null || client.world == null) return;

        try {
            RegistryEntry<Biome> biomeEntry = client.world.getBiome(client.player.getBlockPos());
            String biomeName = biomeEntry.getKey()
                    .map(k -> toTitleCase(k.getValue().getPath()))
                    .orElse("Unknown");
            drawText(ctx, client, "Biome: " + biomeName);
        } catch (Exception ignored) {
            // Chunk not loaded yet
        }
    }

    private static String toTitleCase(String snakeCase) {
        String[] words = snakeCase.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!sb.isEmpty()) sb.append(' ');
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Biome: Dark Forest") + 4;
    }
}
