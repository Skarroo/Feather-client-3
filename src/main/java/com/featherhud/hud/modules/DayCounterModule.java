package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class DayCounterModule extends HudModule {

    public DayCounterModule() {
        super("day_counter", "Day Counter", 210, 14);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null || client.world == null) return;

        long timeOfDay = client.world.getTimeOfDay();
        long day = timeOfDay / 24000L;
        long ticksInDay = timeOfDay % 24000L;

        // MC time: 0 = 6am, 6000 = noon, 12000 = 6pm, 18000 = midnight
        long mcHour = (ticksInDay / 1000 + 6) % 24;
        long mcMin  = (ticksInDay % 1000) * 60 / 1000;

        String phase = ticksInDay < 12000 ? "☀" : "☽";
        String text = String.format("Day %d  %s %02d:%02d", day + 1, phase, mcHour, mcMin);
        drawText(ctx, client, text);
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Day 9999  ☀ 00:00") + 4;
    }
}
