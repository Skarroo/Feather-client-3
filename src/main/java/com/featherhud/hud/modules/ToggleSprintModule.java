package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ToggleSprintModule extends HudModule {

    public ToggleSprintModule() {
        super("toggle_sprint", "Toggle Sprint", 210, 38);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        boolean sprinting = client.player.isSprinting();
        boolean swimming  = client.player.isTouchingWater() && client.player.isSprinting();
        boolean flying    = client.player.getAbilities().flying;

        String status;
        int statusColor;
        if (flying) {
            status = "✦ FLYING";
            statusColor = 0xFF00AAFF;
        } else if (swimming) {
            status = "~ SWIMMING";
            statusColor = 0xFF55FFFF;
        } else if (sprinting) {
            status = "▶ SPRINTING";
            statusColor = 0xFF55FF55;
        } else {
            status = "● WALKING";
            statusColor = 0xFFAAAAAA;
        }

        int x = config.x;
        int y = config.y;
        int w = client.textRenderer.getWidth(status);

        if (config.showBackground) {
            ctx.fill(x - 2, y - 2, x + w + 2, y + 9, 0x90000000);
        }
        ctx.drawText(client.textRenderer, status, x, y, statusColor, true);
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("▶ SPRINTING") + 4;
    }
}
