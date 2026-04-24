package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class SpeedometerModule extends HudModule {

    private double prevX, prevY, prevZ;
    private double speed = 0.0;       // horizontal blocks/second
    private double vertSpeed = 0.0;   // vertical blocks/second (+ = up)
    private boolean hasPrev = false;

    public SpeedometerModule() {
        super("speedometer", "Speedometer", 2, 86);
    }

    @Override
    public void tick(MinecraftClient client) {
        if (client.player == null) { hasPrev = false; return; }

        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();

        if (hasPrev) {
            double dx = x - prevX;
            double dz = z - prevZ;
            double dy = y - prevY;
            // 20 ticks/second
            speed      = Math.sqrt(dx * dx + dz * dz) * 20.0;
            vertSpeed  = dy * 20.0;
        }

        prevX = x;  prevY = y;  prevZ = z;
        hasPrev = true;
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        String vertStr = vertSpeed > 0.05 ? String.format(" ↑%.1f", vertSpeed)
                       : vertSpeed < -0.05 ? String.format(" ↓%.1f", Math.abs(vertSpeed))
                       : "";
        drawText(ctx, client, String.format("Speed: %.1f%s bps", speed, vertStr));
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Speed: 99.9 ↑99.9 bps") + 4;
    }
}
