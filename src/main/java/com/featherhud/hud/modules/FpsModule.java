package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsModule extends HudModule {

    public FpsModule() {
        super("fps", "FPS", 2, 2);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        drawText(ctx, client, client.getCurrentFps() + " FPS");
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("999 FPS") + 4;
    }
}
