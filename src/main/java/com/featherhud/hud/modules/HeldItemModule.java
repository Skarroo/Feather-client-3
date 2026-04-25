package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
public class HeldItemModule extends HudModule {
    public HeldItemModule(){super("held_item","Held Item",100,85);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        ItemStack held=client.player.getMainHandStack();
        if(held.isEmpty())return;
        int x=config.x,y=config.y;
        String name=held.getName().getString();
        int w=client.textRenderer.getWidth(name)+24;
        if(config.showBackground)ctx.fill(x-2,y-2,x+w+2,y+20,0x90000000);
        ctx.drawItem(held,x,y+1);
        ctx.drawText(client.textRenderer,name,x+20,y+5,textColor(),true);
        if(held.isDamageable()&&held.isDamaged()){
            float pct=(float)(held.getMaxDamage()-held.getDamage())/held.getMaxDamage();
            ctx.fill(x+20,y+14,x+80,y+16,0xFF333333);
            ctx.fill(x+20,y+14,x+20+(int)(60*pct),y+16,0xFF000000|durabilityColor(pct));
        }
    }
    @Override public int getWidth(MinecraftClient c){return 100;}
    @Override public int getHeight(MinecraftClient c){return 20;}
}
