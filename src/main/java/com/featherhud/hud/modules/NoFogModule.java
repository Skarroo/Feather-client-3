package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class NoFogModule extends HudModule {
    public static boolean isEnabled(){
        return com.featherhud.config.ConfigManager.configs.containsKey("no_fog")&&com.featherhud.config.ConfigManager.configs.get("no_fog").enabled;
    }
    public NoFogModule(){super("no_fog","No Fog",210,190);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"No Fog: \u00a7aON");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("No Fog: ON")+4;}
}
