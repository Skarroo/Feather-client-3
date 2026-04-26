package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class MotionBlurModule extends HudModule {
    public static float strength = 0.5f;
    public static boolean active() {
        var cfg = com.featherhud.config.ConfigManager.configs.get("motion_blur");
        return cfg != null && cfg.enabled;
    }
    public MotionBlurModule(){super("motion_blur","Motion Blur",210,110);}
    @Override public void render(DrawContext ctx, MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,String.format("Motion Blur: %.0f%%", strength*100));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Motion Blur: 100%")+4;}
}