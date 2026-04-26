package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.*;
public class CoordsModule extends HudModule {
    public CoordsModule(){super("coords","Coordinates",2,20);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        double x=client.player.getX(),y=client.player.getY(),z=client.player.getZ();
        List<String> lines=new ArrayList<>();
        lines.add(String.format("\u00a77X \u00a7f%.0f  \u00a77Y \u00a7f%.0f  \u00a77Z \u00a7f%.0f",x,y,z));
        float yaw=client.player.getYaw()%360f;if(yaw<0)yaw+=360f;
        String dir;
        if(yaw<22.5f||yaw>=337.5f)dir="S";
        else if(yaw<67.5f)dir="SW";else if(yaw<112.5f)dir="W";
        else if(yaw<157.5f)dir="NW";else if(yaw<202.5f)dir="N";
        else if(yaw<247.5f)dir="NE";else if(yaw<292.5f)dir="E";
        else dir="SE";
        lines.add("\u00a77Facing \u00a7f"+dir+" \u00a77("+String.format("%.0f",yaw)+"\u00b0)");
        drawLines(ctx,client,lines);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("X -9999  Y -9999  Z -9999")+10;}
    @Override public int getHeight(MinecraftClient c){return (c.textRenderer.fontHeight+2)*2+4;}
}
