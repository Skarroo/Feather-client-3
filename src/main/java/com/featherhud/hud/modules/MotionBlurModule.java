package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class MotionBlurModule extends HudModule {
    public static float getStrength(){
        var cfg = com.featherhud.config.ConfigManager.configs.get("motion_blur");
        return (cfg != null && cfg.enabled) ? 0.6f : 0f;
    }
    public MotionBlurModule(){super("motion_blur","Motion Blur",0,0);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        // Visual indicator only - actual blur done via mixin
        int sw = ctx.getScaledWindowWidth();
        int sh = ctx.getScaledWindowHeight();
        // subtle vignette edges when moving fast
        float speed = 0f;
        if(c.player != null){
            double vx = c.player.getVelocity().x;
            double vz = c.player.getVelocity().z;
            speed = (float)Math.sqrt(vx*vx+vz*vz);
        }
        if(speed > 0.1f){
            int alpha = (int)Math.min(60, speed * 80);
            int col = (alpha << 24);
            // vignette on edges
            for(int i=0;i<12;i++){
                int a2 = (int)(alpha * (12-i)/12f);
                int c2 = (a2<<24);
                ctx.fill(i, 0, i+1, sh, c2);
                ctx.fill(sw-i-1, 0, sw-i, sh, c2);
                ctx.fill(0, i, sw, i+1, c2);
                ctx.fill(0, sh-i-1, sw, sh-i, c2);
            }
        }
    }
}
