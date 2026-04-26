package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
public class ReachModule extends HudModule {
    public ReachModule(){super("reach","Reach Display",2,190);}
    @Override public void render(DrawContext ctx,MinecraftClient c){
        if(!config.enabled||c.player==null)return;
        double b,e;
        try {
            var blockAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft","player.block_interaction_range"));
            var entityAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft","player.entity_interaction_range"));
            b = blockAttr != null ? c.player.getAttributeValue(blockAttr) : (c.player.isCreative()?5.0:4.5);
            e = entityAttr != null ? c.player.getAttributeValue(entityAttr) : (c.player.isCreative()?5.0:3.0);
        } catch(Exception ex) {
            b = c.player.isCreative()?5.0:4.5;
            e = c.player.isCreative()?5.0:3.0;
        }
        drawText(ctx,c,String.format("\u00a77Reach \u00a7f%.1f\u00a77b \u00a7f%.1f\u00a77e",b,e));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Reach 0.0b 0.0e")+10;}
}
