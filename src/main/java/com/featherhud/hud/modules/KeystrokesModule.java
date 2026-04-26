package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.LinkedList;
public class KeystrokesModule extends HudModule {
    private static final int K = 20, G = 2, ST = K + G;
    private final LinkedList<Long> lClicks = new LinkedList<>();
    private final LinkedList<Long> rClicks = new LinkedList<>();
    private boolean pL = false, pR = false;

    public KeystrokesModule(){super("keystrokes","Keystrokes",160,100);}

    @Override public void tick(MinecraftClient c){
        if(c.player == null) return;
        long now = System.currentTimeMillis();
        lClicks.removeIf(t -> now - t > 1000);
        rClicks.removeIf(t -> now - t > 1000);
        boolean l = c.options.attackKey.isPressed();
        boolean r = c.options.useKey.isPressed();
        if(l && !pL) lClicks.add(now);
        if(r && !pR) rClicks.add(now);
        pL = l; pR = r;
    }

    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player == null) return;
        boolean lmb = c.options.attackKey.isPressed();
        boolean rmb = c.options.useKey.isPressed();
        boolean w   = c.options.forwardKey.isPressed();
        boolean a   = c.options.leftKey.isPressed();
        boolean s   = c.options.backKey.isPressed();
        boolean d   = c.options.rightKey.isPressed();
        boolean spc = c.options.jumpKey.isPressed();

        int ox = config.x, oy = config.y;
        int ww = 3 * ST - G; // total width

        // outer background
        if(config.showBackground)
            ctx.fill(ox-3, oy-3, ox+ww+3, oy+5*ST-G+3, 0xCC111111);

        // Row 0: W (center)
        drawKey(ctx, c, "W",  ox+ST,    oy,       K, K, w,   false);
        // Row 1: A S D
        drawKey(ctx, c, "A",  ox,       oy+ST,    K, K, a,   false);
        drawKey(ctx, c, "S",  ox+ST,    oy+ST,    K, K, s,   false);
        drawKey(ctx, c, "D",  ox+2*ST,  oy+ST,    K, K, d,   false);
        // Row 2: Space (full width)
        drawKey(ctx, c, "",   ox,       oy+2*ST,  ww,K, spc, false);
        // Row 3: LMB / RMB with CPS
        int hw = (ww - G) / 2;
        drawMouseKey(ctx, c, "LMB", lClicks.size(), ox,       oy+3*ST, hw,    K+8, lmb);
        drawMouseKey(ctx, c, "RMB", rClicks.size(), ox+hw+G,  oy+3*ST, ww-hw-G, K+8, rmb);
        // Row 4: space bar line indicator
        int lineY = oy + 4*ST + 4;
        ctx.fill(ox + ww/2-10, lineY, ox + ww/2+10, lineY+1,
                 spc ? 0xFFFFFFFF : 0xFF555555);
    }

    private void drawKey(DrawContext ctx, MinecraftClient mc,
                         String label, int x, int y, int w, int h,
                         boolean pressed, boolean mouse){
        int bg     = pressed ? 0xCC3a3a3a : 0xCC1e1e1e;
        int border = pressed ? 0xFF888888 : 0xFF444444;
        int tc     = pressed ? 0xFFFFFFFF : 0xFF888888;
        // fill
        ctx.fill(x, y, x+w, y+h, bg);
        // border
        ctx.fill(x,   y,   x+w, y+1,   border);
        ctx.fill(x,   y+h-1, x+w, y+h, border);
        ctx.fill(x,   y,   x+1, y+h,   border);
        ctx.fill(x+w-1, y, x+w, y+h,   border);
        if(!label.isEmpty()){
            int tw = mc.textRenderer.getWidth(label);
            int th = mc.textRenderer.fontHeight;
            ctx.drawText(mc.textRenderer, label,
                x+(w-tw)/2, y+(h-th)/2, tc, false);
        }
    }

    private void drawMouseKey(DrawContext ctx, MinecraftClient mc,
                               String label, int cps,
                               int x, int y, int w, int h, boolean pressed){
        drawKey(ctx, mc, "", x, y, w, h, pressed, true);
        int tc = pressed ? 0xFFFFFFFF : 0xFF888888;
        // Label top center
        int lw = mc.textRenderer.getWidth(label);
        ctx.drawText(mc.textRenderer, label, x+(w-lw)/2, y+3, tc, false);
        // CPS middle
        String cpsStr = cps + " CPS";
        int cw = mc.textRenderer.getWidth(cpsStr);
        ctx.drawText(mc.textRenderer, cpsStr, x+(w-cw)/2, y+13, 0xFF666666, false);
        // small line bottom
        ctx.fill(x+w/2-8, y+h-4, x+w/2+8, y+h-3,
                 pressed ? 0xFFFFFFFF : 0xFF444444);
    }

    @Override public int getWidth(MinecraftClient c){ return 3*(K+G)-G+6; }
    @Override public int getHeight(MinecraftClient c){ return 5*(K+G)-G+6; }
}
