package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class ArmorStatusModule extends HudModule {

    // Slot indices: 3=helmet, 2=chestplate, 1=leggings, 0=boots
    private static final int[] ARMOR_SLOTS = {3, 2, 1, 0};
    private static final int ICON_SIZE = 16;
    private static final int GAP = 4;
    private static final int SLOT_WIDTH = ICON_SIZE + GAP;

    public ArmorStatusModule() {
        super("armor_status", "Armor Status", 2, 140);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        List<ItemStack> armorPieces = Arrays.stream(ARMOR_SLOTS)
                .mapToObj(i -> client.player.getInventory().getArmorStack(i))
                .toList();

        // Check if player has any armor at all
        boolean hasArmor = armorPieces.stream().anyMatch(s -> !s.isEmpty());
        if (!hasArmor) return;

        int startX = config.x;
        int startY = config.y;
        int totalWidth = ARMOR_SLOTS.length * SLOT_WIDTH - GAP;
        int totalHeight = ICON_SIZE + 2 + 3; // icon + gap + durability bar

        if (config.showBackground) {
            ctx.fill(startX - 2, startY - 2, startX + totalWidth + 2, startY + totalHeight + 2, 0x90000000);
        }

        for (int i = 0; i < armorPieces.size(); i++) {
            ItemStack stack = armorPieces.get(i);
            int x = startX + i * SLOT_WIDTH;
            int y = startY;

            if (stack.isEmpty()) {
                // Draw placeholder ghost
                ctx.fill(x, y, x + ICON_SIZE, y + ICON_SIZE, 0x30FFFFFF);
                continue;
            }

            ctx.drawItem(stack, x, y);

            // Draw durability bar below icon
            if (stack.isDamageable() && stack.isDamaged()) {
                int maxDmg = stack.getMaxDamage();
                int dmg = stack.getDamage();
                float pct = (float)(maxDmg - dmg) / maxDmg;
                int barW = (int)(ICON_SIZE * pct);
                int color = durabilityColor(pct);

                int barY = y + ICON_SIZE + 2;
                ctx.fill(x, barY, x + ICON_SIZE, barY + 2, 0xFF000000);
                ctx.fill(x, barY, x + barW, barY + 2, 0xFF000000 | color);
            }
        }
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return ARMOR_SLOTS.length * SLOT_WIDTH - GAP + 4;
    }

    @Override
    public int getHeight(MinecraftClient client) {
        return ICON_SIZE + 2 + 3 + 4;
    }
}
