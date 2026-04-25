package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
public class ArrowCountModule extends HudModule {
    public ArrowCountModule(){super("arrow_count","Arrow Count",210,130);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        int count=0;
        for(int i=0;i<client.player.getInventory().size();i++){
            var stack=client.player.getInventory().getStack(i);
            if(stack.getItem()==Items.ARROW||stack.getItem()==Items.SPECTRAL_ARROW||stack.getItem()==Items.TIPPED_ARROW)
                count+=stack.getCount();
        }
        String col=count>32?"\u00a7a":count>8?"\u00a7e":"\u00a7c";
        drawText(ctx,client,"\u27A5 Arrows: "+col+count);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\u27A5 Arrows: 999")+4;}
}
