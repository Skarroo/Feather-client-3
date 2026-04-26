package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
public class ShieldStatusModule extends HudModule {
    public ShieldStatusModule(){super("shield_status","Shield Status",2,240);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        boolean mainShield = c.player.getMainHandStack().getItem() == Items.SHIELD;
        boolean offShield  = c.player.getOffHandStack().getItem()  == Items.SHIELD;
        if(!mainShield && !offShield) return;
        boolean blocking = c.player.isBlocking();
        boolean onCooldown = c.player.getItemCooldownManager()
            .isCoolingDown(Items.SHIELD);
        String status;
        int color;
        if(onCooldown){
            status = "\u00a7cShield \u00a7cCooldown";
            color  = 0xFFFF5555;
        } else if(blocking){
            status = "\u00a7aShield \u00a7aBlocking";
            color  = 0xFF55FF55;
        } else {
            status = "\u00a77Shield \u00a7fReady";
            color  = 0xFFAAAAAA;
        }
        int x=config.x, y=config.y;
        int w=c.textRenderer.getWidth("Shield Cooldown")+10;
        if(config.showBackground) fillPill(ctx,x,y,w,c.textRenderer.fontHeight+6);
        ctx.drawText(c.textRenderer, status, x+5, y+3, color, true);
    }
    @Override public int getWidth(MinecraftClient c){
        return c.textRenderer.getWidth("Shield Cooldown")+10;
    }
}
