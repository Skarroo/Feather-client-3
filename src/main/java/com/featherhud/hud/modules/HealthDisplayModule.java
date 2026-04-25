package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class HealthDisplayModule extends HudModule {
    public HealthDisplayModule(){super("health_display","Health Display",2,146);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        float hp=client.player.getHealth(),max=client.player.getMaxHealth();
        float abs=client.player.getAbsorptionAmount();
        float pct=hp/max;
        String col=pct>0.6f?"\u00a7a":pct>0.3f?"\u00a7e":"\u00a7c";
        String absStr=abs>0?String.format(" +\u00a7e%.0f\u00a7r",abs):"";
        drawText(ctx,client,String.format("\u2665 %s%.1f\u00a7r/%.0f%s",col,hp,max,absStr));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u2665 20.0/20 +0")+4;}
}
