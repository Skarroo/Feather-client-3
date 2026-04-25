package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
public class TargetHealthModule extends HudModule {
    public TargetHealthModule(){super("target_health","Target Health",100,50);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        if(client.crosshairTarget==null||client.crosshairTarget.getType()!=HitResult.Type.ENTITY)return;
        EntityHitResult hit=(EntityHitResult)client.crosshairTarget;
        if(!(hit.getEntity() instanceof LivingEntity target))return;
        float hp=target.getHealth(),max=target.getMaxHealth(),pct=hp/max;
        int x=config.x,y=config.y,bw=80;
        if(config.showBackground)ctx.fill(x-2,y-2,x+bw+2,y+28,0x90000000);
        ctx.drawText(client.textRenderer,target.getName().getString(),x,y,textColor(),true);
        ctx.fill(x,y+12,x+bw,y+18,0xFF333333);
        ctx.fill(x,y+12,x+(int)(bw*pct),y+18,0xFF000000|durabilityColor(pct));
        ctx.drawText(client.textRenderer,String.format("%.0f / %.0f HP",hp,max),x,y+20,textColor(),false);
    }
    @Override public int getWidth(MinecraftClient c){return 84;}
    @Override public int getHeight(MinecraftClient c){return 30;}
}
