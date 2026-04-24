package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class PlayerCountModule extends HudModule {

    public PlayerCountModule() {
        super("player_count", "Player Count", 210, 26);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        if (client.getNetworkHandler() == null) return;

        int count = client.getNetworkHandler().getPlayerList().size();
        drawText(ctx, client, "Players: " + count);
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Players: 999") + 4;
    }
}
