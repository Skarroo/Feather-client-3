package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ServerInfo;
public class ServerIPModule extends HudModule {
    public ServerIPModule(){super("server_ip","Server IP",210,80);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        if(client.isIntegratedServerRunning()){drawText(ctx,client,"Singleplayer");return;}
        ServerInfo si=client.getCurrentServerEntry();
        String addr=si!=null?si.address:"Unknown";
        drawText(ctx,client,"\u25ba "+addr);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u25ba hypixel.net")+4;}
}
