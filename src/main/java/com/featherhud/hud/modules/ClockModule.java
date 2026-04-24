package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockModule extends HudModule {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClockModule() {
        super("clock", "Clock", 210, 2);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        drawText(ctx, client, LocalTime.now().format(FMT));
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("00:00:00") + 4;
    }
}
