package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class FOVChangerModule extends HudModule {
    private static final int CUSTOM_FOV=90;
    private boolean applied=false;
    public FOVChangerModule(){super("fov_changer","FOV Changer",210,86);}
    @Override public void tick(MinecraftClient client){
        if(client.player==null)return;
        if(config.enabled&&!applied){client.options.getFov().setValue(CUSTOM_FOV);applied=true;}
        else if(!config.enabled&&applied){client.options.getFov().setValue(70);applied=false;}
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"FOV: "+client.options.getFov().getValue());
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("FOV: 110")+4;}
}
