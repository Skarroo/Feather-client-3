package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class FoodDisplayModule extends HudModule {
    public FoodDisplayModule(){super("food","Food Level",2,122);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        int food=client.player.getHungerManager().getFoodLevel();
        float sat=client.player.getHungerManager().getSaturationLevel();
        String col=food>14?"\u00a7a":food>7?"\u00a7e":"\u00a7c";
        drawText(ctx,client,String.format("Food: %s%d\u00a7r  Sat: %.1f",col,food,sat));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Food: 20  Sat: 20.0")+4;}
}
