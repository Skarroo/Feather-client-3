package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class ReachModule extends HudModule {
    public ReachModule(){super("reach","Reach Display",2,98);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        double b = client.player.isCreative() ? 5.0 : 4.5;
        double e = client.player.isCreative() ? 5.0 : 3.0;
        drawText(ctx,client,String.format("Reach: %.1f blk  %.1f ent",b,e));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Reach: 0.0 blk  0.0 ent")+4;}
}
