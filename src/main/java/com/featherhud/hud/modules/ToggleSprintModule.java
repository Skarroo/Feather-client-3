package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class ToggleSprintModule extends HudModule {
    public ToggleSprintModule(){super("toggle_sprint","Toggle Sprint",2,72);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        boolean sp=client.player.isSprinting(),sw=client.player.isTouchingWater()&&sp,fl=client.player.getAbilities().flying;
        String s;int bg;
        if(fl){s="\u00a7bFlying";bg=0xCC0044AA;}
        else if(sw){s="\u00a73Swimming";bg=0xCC004444;}
        else if(sp){s="\u00a7aSprinting";bg=0xCC004400;}
        else{s="\u00a77Walking";bg=0xCC111111;}
        int x=config.x,y=config.y,w=client.textRenderer.getWidth(s)+10,h=client.textRenderer.fontHeight+6;
        ctx.fill(x+2,y,x+w-2,y+h,bg);
        ctx.fill(x,y+1,x+w,y+h-1,bg);
        ctx.fill(x,y,x+3,y+h,0xFFE53935);
        ctx.drawText(client.textRenderer,s,x+8,y+3,0xFFFFFFFF,true);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Sprinting")+10;}
}
