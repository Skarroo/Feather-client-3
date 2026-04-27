package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.resource.ResourcePackManager;
import java.util.*;
public class PackDisplayModule extends HudModule {
    public PackDisplayModule(){super("pack_display","Pack Display",210,220);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled||c.player==null)return;
        ResourcePackManager rpm=c.getResourcePackManager();
        List<String> packs=new ArrayList<>(rpm.getEnabledIds());
        if(packs.isEmpty())return;
        int x=config.x,y=config.y;
        int maxW=0;
        for(String p:packs){
            String name=p.replace("file/","").replace(".zip","");
            int w=c.textRenderer.getWidth(name);
            if(w>maxW)maxW=w;
        }
        maxW+=14;
        int totalH=packs.size()*(c.textRenderer.fontHeight+3)+6;
        ctx.fill(x-2,y-2,x+maxW+2,y+totalH,0xCC111111);
        ctx.drawText(c.textRenderer,"\u00a77Packs",x+2,y+2,0xFFFFFF55,true);
        int py=y+c.textRenderer.fontHeight+4;
        for(String p:packs){
            String name=p.replace("file/","").replace(".zip","");
            ctx.drawText(c.textRenderer,"\u00a77\u25aa \u00a7f"+name,x+2,py,0xFFFFFFFF,false);
            py+=c.textRenderer.fontHeight+2;
        }
    }
    @Override public int getWidth(MinecraftClient c){return 120;}
    @Override public int getHeight(MinecraftClient c){return 100;}
}
