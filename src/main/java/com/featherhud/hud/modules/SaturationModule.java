package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class SaturationModule extends HudModule {
    public SaturationModule(){super("saturation","Saturation",2,158);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        float sat=client.player.getHungerManager().getSaturationLevel();
        int x=config.x,y=config.y,bw=80;
        if(config.showBackground)ctx.fill(x-2,y-2,x+bw+2,y+14,0x90000000);
        ctx.drawText(client.textRenderer,String.format("Sat: %.1f",sat),x,y,textColor(),true);
        ctx.fill(x,y+9,x+bw,y+12,0xFF333333);
        ctx.fill(x,y+9,x+(int)(bw*Math.min(sat/20f,1f)),y+12,0xFFFFAA00);
    }
    @Override public int getWidth(MinecraftClient c){return 84;}
    @Override public int getHeight(MinecraftClient c){return 14;}
}
