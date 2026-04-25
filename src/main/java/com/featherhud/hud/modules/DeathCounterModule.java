package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class DeathCounterModule extends HudModule {
    private int deaths=0;
    private boolean wasDead=false;
    public DeathCounterModule(){super("death_counter","Death Counter",210,74);}
    @Override public void tick(MinecraftClient client){
        if(client.player==null)return;
        boolean dead=client.player.isDead()||client.player.getHealth()<=0;
        if(dead&&!wasDead)deaths++;
        wasDead=dead;
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"\u2620 Deaths: \u00a7c"+deaths);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u2620 Deaths: 999")+4;}
}
