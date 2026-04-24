package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingModule extends HudModule {

    public PingModule() {
        super("ping", "Ping", 2, 50);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        if (client.getNetworkHandler() == null) return;

        PlayerListEntry entry = client.getNetworkHandler()
                .getPlayerListEntry(client.player.getUuid());
        if (entry == null) return;

        int latency = entry.getLatency();
        String color = latency < 80 ? "§a" : latency < 150 ? "§e" : latency < 300 ? "§c" : "§4";
        drawText(ctx, client, "Ping: " + color + latency + "§r ms");
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Ping: 999 ms") + 4;
    }
}
