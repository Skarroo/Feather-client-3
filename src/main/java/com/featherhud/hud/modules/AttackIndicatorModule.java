package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class AttackIndicatorModule extends HudModule {
    public AttackIndicatorModule(){super("attack_indicator","Attack Indicator",100,75);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        float pct=client.player.getAttackCooldownProgress(0f);
        int x=config.x,y=config.y,bw=60;
        if(config.showBackground)ctx.fill(x-2,y-2,x+bw+2,y+14,0x90000000);
        ctx.fill(x,y+8,x+bw,y+12,0xFF333333);
        int color=pct>=1f?0xFF00C853:pct>0.5f?0xFFFFAA00:0xFFFF5555;
        ctx.fill(x,y+8,x+(int)(bw*pct),y+12,color);
        String label=pct>=1f?"\u00a7aREADY":String.format("\u00a7e%.0f%%",pct*100);
        ctx.drawText(client.textRenderer,"Atk: "+label,x,y,textColor(),true);
    }
    @Override public int getWidth(MinecraftClient c){return 64;}
    @Override public int getHeight(MinecraftClient c){return 14;}
}