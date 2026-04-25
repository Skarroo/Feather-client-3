package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.ItemEntity;
public class ItemCounterModule extends HudModule {
    private int count=0;
    public ItemCounterModule(){super("item_counter","Item Counter",210,98);}
    @Override public void tick(MinecraftClient client){
        if(client.world==null)return;
        count=0;
        client.world.getEntities().forEach(e->{if(e instanceof ItemEntity)count++;});
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        drawText(ctx,client,"Items: \u00a7e"+count);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Items: 9999")+4;}
}
