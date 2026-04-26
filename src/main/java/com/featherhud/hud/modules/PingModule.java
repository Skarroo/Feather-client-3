package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
public class PingModule extends HudModule {
    public PingModule(){super("ping","Ping",2,56);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null||client.getNetworkHandler()==null)return;
        PlayerListEntry e=client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if(e==null)return;
        int l=e.getLatency();
        String bar=l<80?"\u00a7a\u2588\u2588\u2588\u2588":l<150?"\u00a7e\u2588\u2588\u2588\u00a77\u2588":l<300?"\u00a7c\u2588\u2588\u00a77\u2588\u2588":"\u00a74\u2588\u00a77\u2588\u2588\u2588";
        String col=l<80?"\u00a7a":l<150?"\u00a7e":l<300?"\u00a7c":"\u00a74";
        drawText(ctx,client,bar+" "+col+l+"\u00a7r ms");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("|||| 999 ms")+10;}
}
