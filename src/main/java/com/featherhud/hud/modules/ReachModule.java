package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.attribute.EntityAttributes;

public class ReachModule extends HudModule {

    public ReachModule() {
        super("reach", "Reach Display", 2, 98);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        double blockReach;
        double entityReach;

        try {
            blockReach  = client.player.getAttributeValue(EntityAttributes.BLOCK_INTERACTION_RANGE);
            entityReach = client.player.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
        } catch (Exception e) {
            // Fallback for any attribute naming differences
            blockReach  = client.player.isCreative() ? 5.0 : 4.5;
            entityReach = client.player.isCreative() ? 5.0 : 3.0;
        }

        drawText(ctx, client,
                String.format("Reach: %.1f blk  %.1f ent", blockReach, entityReach));
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("Reach: 0.0 blk  0.0 ent") + 4;
    }
}
