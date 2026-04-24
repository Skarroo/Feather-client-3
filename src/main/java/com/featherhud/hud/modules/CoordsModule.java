package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class CoordsModule extends HudModule {

    public CoordsModule() {
        super("coords", "Coordinates", 2, 14);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();

        List<String> lines = new ArrayList<>();
        lines.add(String.format("X: %.1f  Y: %.1f  Z: %.1f", x, y, z));
        lines.add("Facing: " + getFacingString(client));
        drawLines(ctx, client, lines);
    }

    private String getFacingString(MinecraftClient client) {
        float yaw = client.player.getYaw() % 360f;
        if (yaw < 0) yaw += 360f;

        String dir;
        if      (yaw < 22.5f || yaw >= 337.5f) dir = "South";
        else if (yaw < 67.5f)                  dir = "South-West";
        else if (yaw < 112.5f)                 dir = "West";
        else if (yaw < 157.5f)                 dir = "North-West";
        else if (yaw < 202.5f)                 dir = "North";
        else if (yaw < 247.5f)                 dir = "North-East";
        else if (yaw < 292.5f)                 dir = "East";
        else                                    dir = "South-East";

        return String.format("%s (%.1f°)", dir, yaw);
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return 160;
    }

    @Override
    public int getHeight(MinecraftClient client) {
        return 24;
    }
}
