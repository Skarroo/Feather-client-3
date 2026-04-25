package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class NoHurtCamModule extends HudModule {
    public static boolean isEnabled(){
        return com.featherhud.config.ConfigManager.configs.containsKey("no_hurt_cam")&&com.featherhud.config.ConfigManager.configs.get("no_hurt_cam").enabled;
    }
    public NoHurtCamModule(){super("no_hurt_cam","No Hurt Cam",210,178);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"No Hurt Cam: \u00a7aON");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("No Hurt Cam: ON")+4;}
}
