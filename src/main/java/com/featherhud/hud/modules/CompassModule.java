package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class CompassModule extends HudModule {
    private static final int W=110,H=16;
    public CompassModule(){super("compass","Compass",80,2);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        float yaw=client.player.getYaw()%360f;if(yaw<0)yaw+=360f;
        int x=config.x,y=config.y,cx=x+W/2;
        ctx.fill(x+2,y,x+W-2,y+H,0xCC111111);
        ctx.fill(x,y+1,x+W,y+H-1,0xCC111111);
        ctx.fill(x,y,x+W,y+1,0xFF222222);ctx.fill(x,y+H-1,x+W,y+H,0xFF222222);
        float ppd=W/90f;
        String[]lb={"S","SW","W","NW","N","NE","E","SE","S"};
        float[]dg={0f,45f,90f,135f,180f,225f,270f,315f,360f};
        for(int i=0;i<lb.length;i++){
            float diff=dg[i]-yaw;while(diff>180)diff-=360f;while(diff<-180)diff+=360f;
            int px=cx+(int)(diff*ppd);if(px<x+2||px>x+W-2)continue;
            boolean card=i%2==0;
            int lc=card?(lb[i].equals("N")?0xFFFF5555:0xFFFFFFFF):0xFF888888;
            int tw=client.textRenderer.getWidth(lb[i]);
            ctx.drawText(client.textRenderer,lb[i],px-tw/2,y+4,lc,true);
        }
        ctx.fill(cx-1,y+H-4,cx+1,y+H,0xFFE53935);
    }
    @Override public int getWidth(MinecraftClient c){return W;}
    @Override public int getHeight(MinecraftClient c){return H;}
}
