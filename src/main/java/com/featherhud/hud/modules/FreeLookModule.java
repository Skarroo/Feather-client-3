package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
public class FreeLookModule extends HudModule {
    public static final KeyBinding KEY = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("key.featherhud.freelook",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.featherhud"));
    public static boolean isActive = false;
    private static float savedYaw   = 0f;
    private static float savedPitch = 0f;

    public FreeLookModule(){super("freelook","Free Look",0,0);}

    @Override public void tick(MinecraftClient c){
        if(!config.enabled || c.player == null){ isActive=false; return; }
        boolean pressed = KEY.isPressed();
        if(pressed && !isActive){
            savedYaw   = c.player.getYaw();
            savedPitch = c.player.getPitch();
            isActive   = true;
        } else if(!pressed && isActive){
            c.player.setYaw(savedYaw);
            c.player.setPitch(savedPitch);
            isActive = false;
        }
    }

    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled || c.player==null || !isActive) return;
        int sw=ctx.getScaledWindowWidth();
        ctx.drawCenteredTextWithShadow(c.textRenderer,
            "\u00a7eFreeLook \u00a77(hold V)", sw/2, 30, 0xFFFFFFFF);
    }
}
