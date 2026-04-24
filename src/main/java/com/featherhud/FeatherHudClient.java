package com.featherhud;

import com.featherhud.config.ConfigManager;
import com.featherhud.gui.HudConfigScreen;
import com.featherhud.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FeatherHudClient implements ClientModInitializer {

    public static final String MOD_ID = "featherhud";
    public static KeyBinding openConfigKey;
    public static HudRenderer hudRenderer;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        hudRenderer = new HudRenderer();

        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherhud.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.featherhud"
        ));

        HudRenderCallback.EVENT.register(hudRenderer::render);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new HudConfigScreen(null));
                }
            }
            hudRenderer.tick(client);
        });
    }
}
