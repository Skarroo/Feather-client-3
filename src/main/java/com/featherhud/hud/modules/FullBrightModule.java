package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class FullBrightModule extends HudModule {
    private double prevGamma = 1.0;
    private boolean wasEnabled = false;
    public FullBrightModule(){super("fullbright","Full Bright",210,202);}
    @Override public void tick(MinecraftClient c){
        if(c.player == null) return;
        if(config.enabled) {
            if(!wasEnabled) {
                prevGamma = c.options.getGamma().getValue();
                wasEnabled = true;
            }
            if(c.options.getGamma().getValue() < 10.0) {
                c.options.getGamma().setValue(100.0);
                c.options.write();
            }
        } else if(wasEnabled) {
            c.options.getGamma().setValue(prevGamma);
            c.options.write();
            wasEnabled = false;
        }
    }
    @Override public void render(DrawContext ctx,MinecraftClient c){
        if(!config.enabled||c.player==null)return;
        drawText(ctx,c,"\u00a7eFull Bright \u00a7aON");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Full Bright ON")+10;}
}
