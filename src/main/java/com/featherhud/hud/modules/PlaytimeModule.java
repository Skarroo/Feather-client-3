package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class PlaytimeModule extends HudModule {
    private long startTime=0;
    public PlaytimeModule(){super("playtime","Playtime",210,50);}
    @Override public void tick(MinecraftClient client){if(startTime==0&&client.player!=null)startTime=System.currentTimeMillis();}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        long s=(System.currentTimeMillis()-startTime)/1000;
        drawText(ctx,client,String.format("\u23F1 %02d:%02d:%02d",s/3600,(s%3600)/60,s%60));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u23F1 99:59:59")+4;}
}