package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import java.util.*;
public class PotionEffectsModule extends HudModule {
    public PotionEffectsModule(){super("potion_effects","Potion Effects",210,60);}

    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        Collection<StatusEffectInstance> effects = c.player.getStatusEffects();
        if(effects.isEmpty()) return;

        int x = config.x, y = config.y;
        int rowH = 26;
        int barW = 100;

        for(StatusEffectInstance eff : effects){
            String name = eff.getEffectType().value().getName().getString();
            int amp = eff.getAmplifier();
            String lvl = amp > 0 ? " " + toRoman(amp+1) : "";
            int ticks = eff.getDuration();
            boolean infinite = ticks == Integer.MAX_VALUE;
            float pct = infinite ? 1f : Math.min(1f, ticks / 1200f); // 60s max bar

            // format time
            String time;
            if(infinite) time = "\u221e";
            else {
                int sec = ticks / 20;
                time = sec >= 60
                    ? String.format("%d:%02d", sec/60, sec%60)
                    : sec + "s";
            }

            boolean good = eff.getEffectType().value().isBeneficial();
            int nameColor = good ? 0xFF55FF55 : 0xFFFF5555;
            int barColor  = good ? 0xFF00C853 : 0xFFE53935;
            int barBg     = 0xFF222222;

            // pill background
            if(config.showBackground)
                ctx.fill(x-2, y-1, x+barW+2, y+rowH-1, 0xCC111111);

            // effect name + level
            ctx.drawText(c.textRenderer, name+lvl, x+2, y+2, nameColor, true);

            // time text right aligned
            int tw = c.textRenderer.getWidth(time);
            ctx.drawText(c.textRenderer, time, x+barW-tw-2, y+2, 0xFFAAAAAA, false);

            // timer bar
            int barY = y + 13;
            ctx.fill(x+2, barY, x+barW-2, barY+4, barBg);
            ctx.fill(x+2, barY, x+2+(int)((barW-4)*pct), barY+4, barColor);

            y += rowH;
        }
    }

    private static String toRoman(int n){
        return switch(n){case 2->"II";case 3->"III";case 4->"IV";case 5->"V";default->String.valueOf(n);};
    }

    @Override public int getWidth(MinecraftClient c){ return 104; }
    @Override public int getHeight(MinecraftClient c){
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.player == null) return 26;
        return Math.max(1, mc.player.getStatusEffects().size()) * 26;
    }
}
