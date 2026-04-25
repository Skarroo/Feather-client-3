package com.featherhud.hud;
import com.featherhud.hud.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import java.util.*;
public class HudRenderer {
    private final List<HudModule> modules = new ArrayList<>();
    public HudRenderer() {
        modules.add(new FpsModule());
        modules.add(new CoordsModule());
        modules.add(new ChunkModule());
        modules.add(new DirectionModule());
        modules.add(new BiomeModule());
        modules.add(new PingModule());
        modules.add(new MemoryModule());
        modules.add(new CpsModule());
        modules.add(new SpeedometerModule());
        modules.add(new ReachModule());
        modules.add(new TpsModule());
        modules.add(new ClockModule());
        modules.add(new DayCounterModule());
        modules.add(new PlayerCountModule());
        modules.add(new ToggleSprintModule());
        modules.add(new CompassModule());
        modules.add(new ArmorStatusModule());
        modules.add(new KeystrokesModule());
        modules.add(new PotionEffectsModule());
        modules.add(new TargetHealthModule());
        modules.add(new HeldItemModule());
        modules.add(new FoodDisplayModule());
        modules.add(new XPDisplayModule());
        modules.add(new HealthDisplayModule());
        modules.add(new SaturationModule());
        modules.add(new ServerIPModule());
        modules.add(new EntityCounterModule());
        modules.add(new ArrowCountModule());
        modules.add(new TotemCountModule());
        modules.add(new PearlCountModule());
        modules.add(new FullBrightModule());
        modules.add(new ZoomModule());
        modules.add(new NoHurtCamModule());
        modules.add(new NoFogModule());
        modules.add(new PlaytimeModule());
        modules.add(new StopwatchModule());
        modules.add(new ComboDisplayModule());
        modules.add(new AttackIndicatorModule());
        modules.add(new DeathCounterModule());
        modules.add(new FOVChangerModule());
        modules.add(new ItemCounterModule());
        modules.add(new BlockAboveModule());
    }
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player==null||client.world==null||client.options.hudHidden) return;
        for (HudModule m : modules) if (m.getConfig().enabled) m.render(context, client);
    }
    public void tick(MinecraftClient client) { for (HudModule m : modules) m.tick(client); }
    public List<HudModule> getModules() { return Collections.unmodifiableList(modules); }
}
