package com.featherhud.hud;

import com.featherhud.config.ConfigManager;
import com.featherhud.config.HudModuleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public abstract class HudModule {

    protected final String id;
    protected final String displayName;
    protected HudModuleConfig config;

    protected HudModule(String id, String displayName, int defaultX, int defaultY) {
        this.id = id;
        this.displayName = displayName;
        this.config = ConfigManager.getOrCreate(id, defaultX, defaultY);
    }

    // ── Core interface ──────────────────────────────────────────────────────

    /** Called every HUD render frame. Guard with {@code if (!config.enabled) return;}. */
    public abstract void render(DrawContext context, MinecraftClient client);

    /** Called every game tick. Override for stateful tracking (speed, CPS, TPS…). */
    public void tick(MinecraftClient client) {}

    // ── Config accessors ────────────────────────────────────────────────────

    public String getId()            { return id; }
    public String getDisplayName()   { return displayName; }
    public HudModuleConfig getConfig() { return config; }

    // ── Size hints (used by the drag config screen) ─────────────────────────

    /** Approximate pixel width including padding. Override for accuracy. */
    public int getWidth(MinecraftClient client)  { return 70; }

    /** Approximate pixel height including padding. Override for accuracy. */
    public int getHeight(MinecraftClient client) { return 13; }

    // ── Drawing helpers ─────────────────────────────────────────────────────

    protected int textColor() {
        return 0xFF000000 | config.color;
    }

    /** Draw a single-line text element with optional translucent background. */
    protected void drawText(DrawContext ctx, MinecraftClient client, String text) {
        drawText(ctx, client, text, config.x, config.y);
    }

    protected void drawText(DrawContext ctx, MinecraftClient client, String text, int x, int y) {
        if (config.showBackground) {
            int w = client.textRenderer.getWidth(text);
            ctx.fill(x - 2, y - 2, x + w + 2, y + 9, 0x90000000);
        }
        ctx.drawText(client.textRenderer, text, x, y, textColor(), true);
    }

    /** Draw multiple lines of text, all with a shared background. */
    protected void drawLines(DrawContext ctx, MinecraftClient client, List<String> lines) {
        if (lines.isEmpty()) return;
        int x = config.x;
        int y = config.y;
        if (config.showBackground) {
            int maxW = lines.stream().mapToInt(l -> client.textRenderer.getWidth(l)).max().orElse(0);
            int totalH = lines.size() * 10 - 1;
            ctx.fill(x - 2, y - 2, x + maxW + 2, y + totalH + 2, 0x90000000);
        }
        for (int i = 0; i < lines.size(); i++) {
            ctx.drawText(client.textRenderer, lines.get(i), x, y + i * 10, textColor(), true);
        }
    }

    /** Fill a rectangle with a border (1 px each side). */
    protected void drawBorderedRect(DrawContext ctx, int x, int y, int w, int h,
                                    int fillColor, int borderColor) {
        ctx.fill(x, y, x + w, y + h, fillColor);
        ctx.fill(x, y, x + w, y + 1, borderColor);
        ctx.fill(x, y + h - 1, x + w, y + h, borderColor);
        ctx.fill(x, y, x + 1, y + h, borderColor);
        ctx.fill(x + w - 1, y, x + w, y + h, borderColor);
    }

    /** Color from 0 = red → 1 = green (used for durability). */
    protected static int durabilityColor(float pct) {
        if (pct > 0.6f) return 0x55FF55;
        if (pct > 0.3f) return 0xFFAA00;
        return 0xFF5555;
    }
}
