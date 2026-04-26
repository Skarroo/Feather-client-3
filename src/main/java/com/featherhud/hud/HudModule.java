package com.featherhud.hud;
import com.featherhud.config.ConfigManager;
import com.featherhud.config.HudModuleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.List;
public abstract class HudModule {
    protected final String id, displayName;
    protected HudModuleConfig config;
    protected HudModule(String id, String displayName, int defaultX, int defaultY) {
        this.id = id; this.displayName = displayName;
        this.config = ConfigManager.getOrCreate(id, defaultX, defaultY);
    }
    public abstract void render(DrawContext context, MinecraftClient client);
    public void tick(MinecraftClient client) {}
    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public HudModuleConfig getConfig() { return config; }
    public int getWidth(MinecraftClient client) { return 80; }
    public int getHeight(MinecraftClient client) { return 14; }
    protected int textColor() { return 0xFF000000 | config.color; }

    // Feather-style pill background
    protected void fillPill(DrawContext ctx, int x, int y, int w, int h) {
        ctx.fill(x+2, y,   x+w-2, y+h,   0xCC111111);
        ctx.fill(x,   y+1, x+w,   y+h-1, 0xCC111111);
        ctx.fill(x+2, y,   x+w-2, y+1,   0xFF222222);
        ctx.fill(x+2, y+h-1, x+w-2, y+h, 0xFF222222);
    }

    // Draw Feather-style text with pill bg
    protected void drawFeatherText(DrawContext ctx, MinecraftClient mc, String text) {
        int x=config.x, y=config.y;
        int w=mc.textRenderer.getWidth(text)+10;
        int h=mc.textRenderer.fontHeight+6;
        if(config.showBackground) fillPill(ctx,x,y,w,h);
        ctx.drawText(mc.textRenderer, text, x+5, y+3, textColor(), true);
    }

    protected void drawFeatherLines(DrawContext ctx, MinecraftClient mc, List<String> lines) {
        if(lines.isEmpty())return;
        int x=config.x, y=config.y;
        int maxW=lines.stream().mapToInt(l->mc.textRenderer.getWidth(l)).max().orElse(0)+10;
        int h=(mc.textRenderer.fontHeight+2)*lines.size()+4;
        if(config.showBackground) fillPill(ctx,x,y,maxW,h);
        for(int i=0;i<lines.size();i++)
            ctx.drawText(mc.textRenderer,lines.get(i),x+5,y+3+i*(mc.textRenderer.fontHeight+2),textColor(),true);
    }

    // Keep old drawText for compat
    protected void drawText(DrawContext ctx, MinecraftClient mc, String text) { drawFeatherText(ctx,mc,text); }
    protected void drawLines(DrawContext ctx, MinecraftClient mc, List<String> lines) { drawFeatherLines(ctx,mc,lines); }

    protected static int durabilityColor(float pct) {
        if(pct>0.6f)return 0x55FF55; if(pct>0.3f)return 0xFFAA00; return 0xFF5555;
    }
}
