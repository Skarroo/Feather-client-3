package com.featherhud.mixin;
import com.featherhud.config.ConfigManager;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method="renderCrosshair",at=@At("HEAD"),cancellable=true)
    private void customCrosshair(DrawContext ctx, net.minecraft.client.render.RenderTickCounter tc, CallbackInfo ci){
        var cfg = ConfigManager.configs.get("custom_crosshair");
        if(cfg == null || !cfg.enabled) return;
        ci.cancel();
        int cx = ctx.getScaledWindowWidth()/2;
        int cy = ctx.getScaledWindowHeight()/2;
        int color = 0xFF000000 | cfg.color;
        int size = 5, gap = 2, thick = 1;
        // horizontal
        ctx.fill(cx-size, cy-thick, cx-gap, cy+thick, color);
        ctx.fill(cx+gap,  cy-thick, cx+size,cy+thick, color);
        // vertical
        ctx.fill(cx-thick, cy-size, cx+thick, cy-gap, color);
        ctx.fill(cx-thick, cy+gap,  cx+thick, cy+size, color);
        // center dot
        ctx.fill(cx-1, cy-1, cx+1, cy+1, color);
    }
}
