package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class MemoryModule extends HudModule {

    public MemoryModule() {
        super("memory", "Memory", 2, 62);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        Runtime rt = Runtime.getRuntime();
        long used  = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
        long max   = rt.maxMemory() / (1024 * 1024);
        float pct  = (float) used / max;

        String color = pct < 0.6f ? "§a" : pct < 0.85f ? "§e" : "§c";
        drawText(ctx, client, String.format("RAM: %s%d§rMB / %dMB", color, used, max));
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("RAM: 9999MB / 9999MB") + 4;
    }
}
