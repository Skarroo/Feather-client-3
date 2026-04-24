package com.featherhud.hud.modules;

import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;

public class CpsModule extends HudModule {

    private final LinkedList<Long> leftClicks  = new LinkedList<>();
    private final LinkedList<Long> rightClicks = new LinkedList<>();

    private boolean prevLeft  = false;
    private boolean prevRight = false;

    public CpsModule() {
        super("cps", "CPS Counter", 2, 74);
    }

    @Override
    public void tick(MinecraftClient client) {
        if (client.player == null || client.getWindow() == null) return;

        long now = System.currentTimeMillis();
        leftClicks.removeIf(t -> now - t > 1000);
        rightClicks.removeIf(t -> now - t > 1000);

        long handle = client.getWindow().getHandle();
        boolean left  = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        boolean right = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS;

        if (left && !prevLeft)   leftClicks.add(now);
        if (right && !prevRight) rightClicks.add(now);

        prevLeft  = left;
        prevRight = right;
    }

    @Override
    public void render(DrawContext ctx, MinecraftClient client) {
        if (!config.enabled || client.player == null) return;
        String text = String.format("CPS: %d L  %d R", leftClicks.size(), rightClicks.size());
        drawText(ctx, client, text);
    }

    @Override
    public int getWidth(MinecraftClient client) {
        return client.textRenderer.getWidth("CPS: 20 L  20 R") + 4;
    }
}
