package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ChunkPos;
public class ChunkModule extends HudModule {
    public ChunkModule(){super("chunk","Chunk Info",2,26);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        ChunkPos cp=client.player.getChunkPos();
        drawText(ctx,client,String.format("Chunk: %d, %d",cp.x,cp.z));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Chunk: -9999, -9999")+4;}
}
