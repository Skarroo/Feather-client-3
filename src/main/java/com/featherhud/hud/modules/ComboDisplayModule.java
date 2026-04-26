package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
public class ComboDisplayModule extends HudModule {
    private int combo = 0;
    private float prevHealth = -1f;
    private boolean prevAttack = false;
    private long lastHit = 0;

    public ComboDisplayModule(){super("combo","Combo Display",100,96);}

    @Override public void tick(MinecraftClient c){
        if(c.player == null){ combo = 0; prevHealth = -1f; return; }

        float hp = c.player.getHealth();

        // Initialize prev health
        if(prevHealth < 0) prevHealth = hp;

        // If player took damage → break combo
        if(hp < prevHealth - 0.1f){
            combo = 0;
        }
        prevHealth = hp;

        // Reset combo if no hit for 5 seconds
        if(System.currentTimeMillis() - lastHit > 5000 && combo > 0){
            combo = 0;
        }

        // Detect attack key press hitting a living entity
        boolean attacking = c.options.attackKey.isPressed();
        if(attacking && !prevAttack){
            // Check if crosshair is on a living entity
            if(c.crosshairTarget != null &&
               c.crosshairTarget.getType() == HitResult.Type.ENTITY){
                EntityHitResult ehr = (EntityHitResult) c.crosshairTarget;
                if(ehr.getEntity() instanceof LivingEntity target
                   && target != c.player){
                    combo++;
                    lastHit = System.currentTimeMillis();
                }
            }
        }
        prevAttack = attacking;
    }

    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null || combo == 0) return;
        String col = combo >= 10 ? "\u00a7c"
                   : combo >= 5  ? "\u00a7e"
                   : "\u00a7a";
        String txt = "\u00a77Combo " + col + combo + "\u00a7fx";
        drawText(ctx, c, txt);
    }

    @Override public int getWidth(MinecraftClient c){
        return c.textRenderer.getWidth("Combo 999x") + 10;
    }
}
