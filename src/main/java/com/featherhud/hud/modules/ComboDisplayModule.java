package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class ComboDisplayModule extends HudModule {
    private int combo=0;
    private long lastHit=0;
    private boolean prevAttack=false;
    public ComboDisplayModule(){super("combo","Combo Display",100,60);}
    @Override public void tick(MinecraftClient client){
        if(client.player==null)return;
        long now=System.currentTimeMillis();
        if(now-lastHit>4000&&combo>0)combo=0;
        boolean atk=client.options.attackKey.isPressed();
        if(atk&&!prevAttack){combo++;lastHit=now;}
        prevAttack=atk;
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null||combo==0)return;
        String col=combo>=10?"\u00a7c":combo>=5?"\u00a7e":"\u00a7a";
        drawText(ctx,client,String.format("Combo: %s%dx",col,combo));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Combo: 999x")+4;}
}
