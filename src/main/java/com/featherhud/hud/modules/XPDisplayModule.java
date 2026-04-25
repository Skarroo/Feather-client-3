package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class XPDisplayModule extends HudModule {
    public XPDisplayModule(){super("xp","XP Display",2,134);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        int lvl=client.player.experienceLevel;
        float prog=client.player.experienceProgress;
        String col=lvl>=30?"\u00a7b":lvl>=15?"\u00a7a":"\u00a7e";
        drawText(ctx,client,String.format("XP: %s%d\u00a7r (%.0f%%)",col,lvl,prog*100));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("XP: 999 (100%)")+4;}
}
