package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class CompassModule extends HudModule {

    private static final int COMPASS_W = 100;
    private static final int COMPASS_H = 14;

    public CompassModule() {
        super("compass", "Compass", 100, 2);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        float yaw = client.player.getYaw() % 360f;
        if (yaw < 0) yaw += 360f;

        int x = config.x;
        int y = config.y;

        // Background
        if (config.showBackground) {
            ctx.fill(x - 2, y - 2, x + COMPASS_W + 2, y + COMPASS_H + 2, 0x90000000);
        }

        // Draw 4 cardinal points scrolling with the camera
        // Each degree = COMPASS_W / 90 pixels (90° visible arc)
        float pixelsPerDegree = COMPASS_W / 90f;
        int centerX = x + COMPASS_W / 2;

        // Draw markers for N(0/360), E(270), S(180), W(90) and intermediates
        String[] labels  = {"S","SW","W","NW","N","NE","E","SE","S"};
        float[]  degrees = {0f, 45f, 90f, 135f, 180f, 225f, 270f, 315f, 360f};

        for (int i = 0; i < labels.length; i++) {
            float diff = degrees[i] - yaw;
            // Wrap to [-180, 180]
            while (diff > 180)  diff -= 360f;
            while (diff < -180) diff += 360f;

            int px = centerX + (int)(diff * pixelsPerDegree);
            if (px < x || px > x + COMPASS_W) continue;

            boolean cardinal = i % 2 == 0;
            int labelColor = cardinal ? (0xFF000000 | config.color) : 0xFFAAAAAA;
            int tw = client.textRenderer.getWidth(labels[i]);
            ctx.drawText(client.textRenderer, labels[i], px - tw / 2, y, labelColor, true);
        }

        // Center tick mark (player direction indicator)
        ctx.fill(centerX, y + 10, centerX + 1, y + COMPASS_H, 0xFFFF5555);
    }

    @Override
    public int getWidth(MinecraftClient client) { return COMPASS_W + 4; }

    @Override
    public int getHeight(MinecraftClient client) { return COMPASS_H + 4; }
}
