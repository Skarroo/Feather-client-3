package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

public class KeystrokesModule extends HudModule {

    // Key box dimensions
    private static final int KEY  = 16;   // key width/height
    private static final int GAP  = 2;    // gap between keys
    private static final int STEP = KEY + GAP;

    // Total widget size
    // 3 columns × STEP - GAP = 52 wide
    // 4 rows (W + ASD + Space + LMB/RMB) = 4 × STEP - GAP = 70 tall
    private static final int WIDGET_W = 3 * STEP - GAP;          // 52
    private static final int WIDGET_H = 4 * STEP - GAP + 4;      // 74

    public KeystrokesModule() {
        super("keystrokes", "Keystrokes", 160, 100);
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;

        int ox = config.x;
        int oy = config.y;

        long handle = client.getWindow().getHandle();
        boolean lmb = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        boolean rmb = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS;

        boolean w     = client.options.forwardKey.isPressed();
        boolean a     = client.options.leftKey.isPressed();
        boolean s     = client.options.backKey.isPressed();
        boolean d     = client.options.rightKey.isPressed();
        boolean space = client.options.jumpKey.isPressed();
        boolean shift = client.options.sneakKey.isPressed();

        if (config.showBackground) {
            ctx.fill(ox - 2, oy - 2, ox + WIDGET_W + 2, oy + WIDGET_H + 2, 0x90000000);
        }

        // Row 0: W (center column = col 1)
        drawKey(ctx, client, "W",  ox + STEP,    oy,            KEY,          KEY, w);
        // Row 1: A S D
        drawKey(ctx, client, "A",  ox,            oy + STEP,     KEY,          KEY, a);
        drawKey(ctx, client, "S",  ox + STEP,     oy + STEP,     KEY,          KEY, s);
        drawKey(ctx, client, "D",  ox + 2 * STEP, oy + STEP,     KEY,          KEY, d);
        // Row 2: Space (full width)
        drawKey(ctx, client, "SPC", ox,           oy + 2 * STEP, WIDGET_W,     KEY, space);
        // Row 3: LMB / RMB
        int halfW = (WIDGET_W - GAP) / 2;
        drawKey(ctx, client, "L",  ox,            oy + 3 * STEP, halfW,        KEY, lmb);
        drawKey(ctx, client, "R",  ox + halfW + GAP, oy + 3 * STEP, WIDGET_W - halfW - GAP, KEY, rmb);
        // Optional: sneak icon
    }

    private void drawKey(DrawContext ctx, MinecraftClient client,
                         String label, int x, int y, int w, int h, boolean pressed) {
        int bg     = pressed ? 0xCCCCCCCC : 0x80222222;
        int border = pressed ? 0xFFFFFFFF : 0x80AAAAAA;
        int text   = pressed ? 0xFF222222 : 0xFFCCCCCC;

        // Fill + border
        ctx.fill(x, y, x + w, y + h, bg);
        ctx.fill(x,         y,         x + w, y + 1,     border);
        ctx.fill(x,         y + h - 1, x + w, y + h,     border);
        ctx.fill(x,         y,         x + 1, y + h,     border);
        ctx.fill(x + w - 1, y,         x + w, y + h,     border);

        // Centered label
        int tw = client.textRenderer.getWidth(label);
        int th = client.textRenderer.fontHeight;
        int tx = x + (w - tw) / 2;
        int ty = y + (h - th) / 2;
        ctx.drawText(client.textRenderer, label, tx, ty, text, false);
    }

    @Override
    public int getWidth(MinecraftClient client)  { return WIDGET_W + 4; }

    @Override
    public int getHeight(MinecraftClient client) { return WIDGET_H + 4; }
}
