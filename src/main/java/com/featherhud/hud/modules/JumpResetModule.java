package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class JumpResetModule extends HudModule {
    private boolean wasOnGround = false;
    private boolean wasInAir    = false;
    private long    landTime    = 0;
    private int     jumpResets  = 0;
    private long    sessionStart= System.currentTimeMillis();

    public JumpResetModule(){super("jump_reset","Jump Reset",100,130);}

    @Override public void tick(MinecraftClient c){
        if(c.player == null) return;
        boolean onGround = c.player.isOnGround();
        // detect landing after being in air
        if(!wasOnGround && onGround){
            landTime = System.currentTimeMillis();
            if(wasInAir) jumpResets++;
        }
        wasInAir    = !onGround;
        wasOnGround = onGround;
    }

    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        boolean onGround = c.player.isOnGround();
        long timeSinceLand = System.currentTimeMillis() - landTime;

        // Flash green briefly on land (first 300ms)
        String groundCol = (timeSinceLand < 300) ? "\u00a7a" : "\u00a77";
        String airCol    = !onGround ? "\u00a7e" : "\u00a77";
        String state     = onGround ? groundCol+"Ground" : airCol+"Air";

        drawText(ctx, c, "\u00a77JR " + state +
            " \u00a77[\u00a7f" + jumpResets + "\u00a77]");
    }

    @Override public int getWidth(MinecraftClient c){
        return c.textRenderer.getWidth("JR Ground [999]")+10;
    }
}
