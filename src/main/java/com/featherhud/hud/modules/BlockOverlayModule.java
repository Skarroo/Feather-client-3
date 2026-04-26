package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.border.WorldBorder;
public class BlockOverlayModule extends HudModule {
    public BlockOverlayModule(){super("block_overlay","Block Overlay",0,0);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null || c.world == null) return;
        if(c.crosshairTarget == null) return;
        if(c.crosshairTarget.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult bhr = (BlockHitResult) c.crosshairTarget;
        BlockPos pos = bhr.getBlockPos();
        String name = c.world.getBlockState(pos).getBlock().getName().getString();
        int sw = ctx.getScaledWindowWidth();
        int sh = ctx.getScaledWindowHeight();
        // Show block name at bottom center
        int tw = c.textRenderer.getWidth(name);
        int bx = sw/2 - tw/2 - 3;
        int by = sh - 60;
        ctx.fill(bx, by-1, bx+tw+6, by+10, 0xCC111111);
        ctx.drawText(c.textRenderer, name, bx+3, by, 0xFFFFFFFF, true);
    }
}
