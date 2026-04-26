package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import java.util.*;
public class PotionEffectsModule extends HudModule {
    public PotionEffectsModule(){super("potion_effects","Potion Effects",210,60);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        Collection<StatusEffectInstance>efs=client.player.getStatusEffects();
        if(efs.isEmpty())return;
        List<String>lines=new ArrayList<>();
        for(StatusEffectInstance e:efs){
            String name=e.getEffectType().value().getName().getString();
            String lv=e.getAmplifier()>0?" "+(e.getAmplifier()+1):"";
            int tk=e.getDuration();
            String dur=tk==Integer.MAX_VALUE?"\u221e":String.format("%d:%02d",tk/20/60,(tk/20)%60);
            boolean good=!e.getEffectType().value().isBeneficial()?false:true;
            lines.add((good?"\u00a7a":"\u00a7c")+name+lv+" \u00a77"+dur);
        }
        drawLines(ctx,client,lines);
    }
    @Override public int getWidth(MinecraftClient c){return 120;}
}
