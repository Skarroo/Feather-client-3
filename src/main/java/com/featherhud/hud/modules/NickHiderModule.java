package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class NickHiderModule extends HudModule {
    public static String getFakeName(){
        var cfg=com.featherhud.config.ConfigManager.configs.get("nick_hider");
        return (cfg!=null&&cfg.enabled)?"Player":"";
    }
    public NickHiderModule(){super("nick_hider","Nick Hider",0,0);}
    @Override public void render(DrawContext ctx,MinecraftClient c){
        if(!config.enabled||c.player==null)return;
        drawText(ctx,c,"\u00a77Nick \u00a7fPlayer");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Nick Player")+10;}
}
