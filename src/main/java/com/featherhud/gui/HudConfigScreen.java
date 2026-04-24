package com.featherhud.gui;

import com.featherhud.FeatherHudClient;
import com.featherhud.config.ConfigManager;
import com.featherhud.config.HudModuleConfig;
import com.featherhud.hud.HudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public class HudConfigScreen extends Screen {

    private final Screen parent;
    private final List<HudModule> modules;

    private HudModule dragging = null;
    private int dragOffsetX, dragOffsetY;

    // Right-click popup state
    private HudModule popupModule = null;
    private int popupX, popupY;

    private static final int TITLE_BAR = 20;

    public HudConfigScreen(Screen parent) {
        super(Text.literal("Feather HUD Config  •  Drag to move  •  Right-click to toggle"));
        this.parent = parent;
        this.modules = FeatherHudClient.hudRenderer.getModules();
    }

    @Override
    protected void init() {
        // Done button
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), btn -> close())
                .dimensions(width / 2 - 50, height - 28, 100, 20)
                .build());

        // Reset All button
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset Positions"), btn -> {
            for (HudModule m : modules) m.getConfig().resetToDefault();
            ConfigManager.save();
        }).dimensions(width / 2 - 160, height - 28, 110, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Dim background
        renderBackground(ctx, mouseX, mouseY, delta);

        // Title
        ctx.drawCenteredTextWithShadow(textRenderer, title, width / 2, 6, 0xFFFFFF);

        // Draw all module outlines
        for (HudModule m : modules) {
            HudModuleConfig cfg = m.getConfig();
            int mw = m.getWidth(client);
            int mh = m.getHeight(client);

            boolean active = cfg.enabled;
            int borderColor = active ? 0xFF55FF55 : 0xFF555555;
            int bgColor     = active ? 0x4000AA00 : 0x40222222;

            ctx.fill(cfg.x - 2, cfg.y - 2, cfg.x + mw + 2, cfg.y + mh + 2, bgColor);
            // Border
            ctx.fill(cfg.x - 2, cfg.y - 2,         cfg.x + mw + 2, cfg.y - 1,         borderColor);
            ctx.fill(cfg.x - 2, cfg.y + mh + 1,    cfg.x + mw + 2, cfg.y + mh + 2,    borderColor);
            ctx.fill(cfg.x - 2, cfg.y - 2,         cfg.x - 1,      cfg.y + mh + 2,    borderColor);
            ctx.fill(cfg.x + mw + 1, cfg.y - 2,    cfg.x + mw + 2, cfg.y + mh + 2,    borderColor);

            // Module name label
            ctx.drawText(textRenderer, m.getDisplayName(), cfg.x, cfg.y, active ? 0xFF55FF55 : 0xFF888888, true);
        }

        // Draw popup if visible
        if (popupModule != null) {
            drawPopup(ctx, popupModule, popupX, popupY);
        }

        super.render(ctx, mouseX, mouseY, delta);

        // Tooltip on hover
        for (HudModule m : modules) {
            HudModuleConfig cfg = m.getConfig();
            int mw = m.getWidth(client);
            int mh = m.getHeight(client);
            if (mouseX >= cfg.x - 2 && mouseX <= cfg.x + mw + 2 &&
                mouseY >= cfg.y - 2 && mouseY <= cfg.y + mh + 2) {
                ctx.drawTooltip(textRenderer,
                        List.of(Text.literal(m.getDisplayName()),
                                Text.literal("§7Enabled: " + (cfg.enabled ? "§aYes" : "§cNo")),
                                Text.literal("§7Pos: " + cfg.x + ", " + cfg.y),
                                Text.literal("§7Right-click to toggle")),
                        mouseX, mouseY);
            }
        }
    }

    private void drawPopup(DrawContext ctx, HudModule m, int px, int py) {
        int pw = 120, ph = 60;
        ctx.fill(px, py, px + pw, py + ph, 0xFF1A1A1A);
        ctx.fill(px, py, px + pw, py + 1,  0xFF55FF55);
        ctx.drawText(textRenderer, "§l" + m.getDisplayName(), px + 4, py + 5, 0xFFFFFF, true);

        boolean enabled = m.getConfig().enabled;
        ctx.drawText(textRenderer, enabled ? "§c● Disable" : "§a● Enable", px + 4, py + 20, 0xFFFFFF, false);
        ctx.drawText(textRenderer, "§7✦ Reset Position", px + 4, py + 34, 0xFFFFFF, false);
        ctx.drawText(textRenderer, "§8[ESC to close]", px + 4, py + 48, 0x888888, false);
    }

    // ── Mouse handling ──────────────────────────────────────────────────────

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int x = (int) mx, y = (int) my;

        // Handle popup click
        if (popupModule != null) {
            HudModuleConfig cfg = popupModule.getConfig();
            // Click on Enable/Disable row (y+20)
            if (x >= popupX + 4 && x <= popupX + 116 && y >= popupY + 17 && y <= popupY + 31) {
                cfg.enabled = !cfg.enabled;
                ConfigManager.save();
                popupModule = null;
                return true;
            }
            // Click on Reset row (y+34)
            if (x >= popupX + 4 && x <= popupX + 116 && y >= popupY + 31 && y <= popupY + 45) {
                cfg.resetToDefault();
                ConfigManager.save();
                popupModule = null;
                return true;
            }
            popupModule = null;
            return true;
        }

        if (button == 0) { // Left click → start drag
            for (int i = modules.size() - 1; i >= 0; i--) {
                HudModule m = modules.get(i);
                HudModuleConfig cfg = m.getConfig();
                int mw = m.getWidth(client);
                int mh = m.getHeight(client);
                if (x >= cfg.x - 2 && x <= cfg.x + mw + 2 &&
                    y >= cfg.y - 2 && y <= cfg.y + mh + 2) {
                    dragging = m;
                    dragOffsetX = x - cfg.x;
                    dragOffsetY = y - cfg.y;
                    return true;
                }
            }
        } else if (button == 1) { // Right click → popup
            for (int i = modules.size() - 1; i >= 0; i--) {
                HudModule m = modules.get(i);
                HudModuleConfig cfg = m.getConfig();
                int mw = m.getWidth(client);
                int mh = m.getHeight(client);
                if (x >= cfg.x - 2 && x <= cfg.x + mw + 2 &&
                    y >= cfg.y - 2 && y <= cfg.y + mh + 2) {
                    popupModule = m;
                    // Keep popup inside screen
                    popupX = Math.min(x, width - 124);
                    popupY = Math.min(y, height - 64);
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (dragging != null && button == 0) {
            HudModuleConfig cfg = dragging.getConfig();
            int newX = (int) mx - dragOffsetX;
            int newY = (int) my - dragOffsetY;
            // Clamp to screen
            newX = Math.max(0, Math.min(width  - dragging.getWidth(client),  newX));
            newY = Math.max(0, Math.min(height - dragging.getHeight(client), newY));
            cfg.x = newX;
            cfg.y = newY;
            return true;
        }
        return super.mouseDragged(mx, my, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        if (dragging != null) {
            ConfigManager.save();
            dragging = null;
            return true;
        }
        return super.mouseReleased(mx, my, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC
            if (popupModule != null) { popupModule = null; return true; }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        ConfigManager.save();
        client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false; // keep rendering the world behind
    }
}
