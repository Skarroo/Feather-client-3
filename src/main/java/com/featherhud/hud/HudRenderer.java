package com.featherhud.hud;

import com.featherhud.hud.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudRenderer {

    private final List<HudModule> modules = new ArrayList<>();

    public HudRenderer() {
        // ── Left column  (x=2) ──────────────────────────────────
        modules.add(new FpsModule());           // 2,  2
        modules.add(new CoordsModule());        // 2, 14
        modules.add(new BiomeModule());         // 2, 38
        modules.add(new PingModule());          // 2, 50
        modules.add(new MemoryModule());        // 2, 62
        modules.add(new CpsModule());           // 2, 74
        modules.add(new SpeedometerModule());   // 2, 86
        modules.add(new ReachModule());         // 2, 98
        modules.add(new TpsModule());           // 2, 110
        // ── Right column (x=210) ────────────────────────────────
        modules.add(new ClockModule());         // 210, 2
        modules.add(new DayCounterModule());    // 210, 14
        modules.add(new PlayerCountModule());   // 210, 26
        modules.add(new ToggleSprintModule());  // 210, 38
        // ── Visual widgets ──────────────────────────────────────
        modules.add(new CompassModule());       // 100, 2
        modules.add(new ArmorStatusModule());   // 2, 140
        modules.add(new KeystrokesModule());    // 160, 100
        modules.add(new PotionEffectsModule()); // 210, 60
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;
        for (HudModule m : modules) {
            if (m.getConfig().enabled) {
                m.render(context, client);
            }
        }
    }

    public void tick(MinecraftClient client) {
        for (HudModule m : modules) {
            m.tick(client);
        }
    }

    public List<HudModule> getModules() {
        return Collections.unmodifiableList(modules);
    }
}
