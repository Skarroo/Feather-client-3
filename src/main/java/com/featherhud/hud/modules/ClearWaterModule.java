package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class ClearWaterModule extends HudModule {
    public static boolean isEnabled(){
        var cfg=com.featherhud.config.ConfigManager.configs.get("clear_water");
        return cfg!=null&&cfg.enabled;
    }
    public ClearWaterModule(){super("clear_water","Clear Water",0,0);}
    @Override public void tick(MinecraftClient c){
        if(c.options==null)return;
        if(config.enabled){
            c.options.getBiomeBlendRadius().setValue(0);
        }
    }
    @Override public void render(DrawContext ctx,MinecraftClient c){}
}
