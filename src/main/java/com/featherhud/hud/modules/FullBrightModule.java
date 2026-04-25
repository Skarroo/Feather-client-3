package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class FullBrightModule extends HudModule {
    private double prevGamma = 1.0;
    private boolean wasEnabled = false;
    public FullBrightModule(){super("fullbright","Full Bright",210,50);}
    @Override public void tick(MinecraftClient client){
        if(config.enabled){
            if(!wasEnabled){prevGamma=client.options.getGamma().getValue();wasEnabled=true;}
            client.options.getGamma().setValue(100.0);
        } else if(wasEnabled){
            client.options.getGamma().setValue(prevGamma);
            wasEnabled=false;
        }
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"\u2600 Full Bright: ON");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u2600 Full Bright: ON")+4;}
}
