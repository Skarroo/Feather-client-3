package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
public class TotemCountModule extends HudModule {
    public TotemCountModule(){super("totem_count","Totem Count",210,142);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        int count=0;
        for(int i=0;i<client.player.getInventory().size();i++){
            var stack=client.player.getInventory().getStack(i);
            if(stack.getItem()==Items.TOTEM_OF_UNDYING)count+=stack.getCount();
        }
        String col=count>1?"\u00a7a":count==1?"\u00a7e":"\u00a7c";
        drawText(ctx,client,"\u2764 Totems: "+col+count);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u2764 Totems: 99")+4;}
}
