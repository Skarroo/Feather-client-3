package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class GlintModule extends HudModule {
    public static boolean isEnabled(){
        var cfg=com.featherhud.config.ConfigManager.configs.get("glint");
        return cfg==null||cfg.enabled;
    }
    public GlintModule(){super("glint","Glint",0,0);}
    @Override public void render(DrawContext ctx,MinecraftClient c){}
}
