package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class TimerModule extends HudModule {
    public TimerModule(){super("timer","Timer",210,240);}
    @Override public void render(DrawContext ctx,MinecraftClient c){
        if(!config.enabled||c.player==null)return;
        drawText(ctx,c,"\u00a77Timer \u00a7f1.0x");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Timer 1.0x")+10;}
}
