package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class DirectionModule extends HudModule {
    public DirectionModule(){super("direction","Direction",100,14);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        float yaw=client.player.getYaw()%360f;if(yaw<0)yaw+=360f;
        String d;int color;
        if(yaw<22.5f||yaw>=337.5f){d="S";color=0xFFFF5555;}
        else if(yaw<67.5f){d="SW";color=0xFFFFAA00;}
        else if(yaw<112.5f){d="W";color=0xFFFFFF55;}
        else if(yaw<157.5f){d="NW";color=0xFF55FF55;}
        else if(yaw<202.5f){d="N";color=0xFF55FFFF;}
        else if(yaw<247.5f){d="NE";color=0xFF5555FF;}
        else if(yaw<292.5f){d="E";color=0xFFFF55FF;}
        else{d="SE";color=0xFFAAAAAA;}
        int x=config.x,y=config.y,tw=client.textRenderer.getWidth(d);
        if(config.showBackground)ctx.fill(x-3,y-2,x+tw+3,y+10,0x90000000);
        ctx.drawText(client.textRenderer,d,x,y,color,true);
    }
    @Override public int getWidth(MinecraftClient c){return 20;}
}
