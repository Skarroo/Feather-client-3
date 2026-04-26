package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import java.util.*;
public class ArmorStatusModule extends HudModule {
    private static final int[] SLOTS = {3, 2, 1, 0};
    public ArmorStatusModule(){super("armor_status","Armor Status",2,220);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        List<ItemStack> pieces = Arrays.stream(SLOTS)
            .mapToObj(i -> c.player.getInventory().getArmorStack(i))
            .toList();
        if(pieces.stream().noneMatch(s -> !s.isEmpty())) return;

        int x = config.x, y = config.y;
        int rowH = 20;

        for(int i = 0; i < pieces.size(); i++){
            ItemStack stack = pieces.get(i);
            if(stack.isEmpty()) continue;

            int ry = y + i * rowH;

            // Durability number
            int dur = stack.isDamageable()
                ? stack.getMaxDamage() - stack.getDamage()
                : stack.getMaxDamage();
            float pct = stack.isDamageable()
                ? (float)(stack.getMaxDamage()-stack.getDamage())/stack.getMaxDamage()
                : 1f;
            String col = pct > 0.6f ? "\u00a7f" : pct > 0.3f ? "\u00a7e" : "\u00a7c";

            // Background pill
            int numW = c.textRenderer.getWidth(String.valueOf(dur));
            int totalW = numW + 24;
            if(config.showBackground){
                ctx.fill(x-2, ry-1, x+totalW+2, ry+17, 0xCC111111);
            }

            // Durability number on left
            ctx.drawText(c.textRenderer, col+dur, x+2, ry+4, 0xFFFFFFFF, true);

            // Item icon on right
            ctx.drawItem(stack, x + numW + 6, ry);
        }
    }
    @Override public int getWidth(MinecraftClient c){ return 60; }
    @Override public int getHeight(MinecraftClient c){ return 4 * 20; }
}
