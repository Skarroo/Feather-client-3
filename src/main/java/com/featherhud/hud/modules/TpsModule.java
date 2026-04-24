package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class TpsModule extends HudModule {

    private static final int SAMPLE_SIZE = 20;
    private final long[] tickTimes = new long[SAMPLE_SIZE];
    private int index = 0;
    private double tps = 20.0;

    public TpsModule() {
        super("tps", "TPS", 2, 110);
    }

    @Override
    public void tick(MinecraftClient client) {
        if (client.world == null) return;

        tickTimes[index % SAMPLE_SIZE] = System.nanoTime();
        index++;

        if (index >= SAMPLE_SIZE) {
            int oldest = index % SAMPLE_SIZE;
            int newest = (index - 1) % SAMPLE_SIZE;
            long elapsed = tickTimes[newest] - tickTimes[oldest];
            if (elapsed > 0) {
                // (SAMPLE_SIZE - 1) intervals for SAMPLE_SIZE tick timestamps
                tps = Math.min(20.0, (SAMPLE_SIZE - 1) * 1_000_000_000.0 / elapsed);
            }
        }
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        String color = tps >= 19.5 ? "§a" : tps >= 15.0 ? "§e" : "§c";
        drawText(ctx, client, String.format("TPS: %s%.1f", color, tps));
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("TPS: 20.0") + 4;
    }
}
