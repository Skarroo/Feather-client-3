package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
public class PearlCountModule extends HudModule {
    public PearlCountModule(){super("pearl_count","Pearl Count",210,154);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        int count=0;
        for(int i=0;i<client.player.getInventory().size();i++){
            var stack=client.player.getInventory().getStack(i);
            if(stack.getItem()==Items.ENDER_PEARL)count+=stack.getCount();
        }
        String col=count>8?"\u00a7a":count>2?"\u00a7e":"\u00a7c";
        drawText(ctx,client,"\u25CE Pearls: "+col+count);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u25CE Pearls: 999")+4;}
}
