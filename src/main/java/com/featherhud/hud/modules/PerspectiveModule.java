package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
public class PerspectiveModule extends HudModule {
    private static final KeyBinding KEY = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("key.featherhud.perspective",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category.featherhud"));
    private boolean wasPressed = false;
    private Perspective savedPerspective = null;

    public PerspectiveModule(){super("perspective","Perspective",0,0);}

    @Override public void tick(MinecraftClient c){
        if(!config.enabled || c.player==null) return;
        boolean pressed = KEY.isPressed();
        if(pressed && !wasPressed){
            if(savedPerspective == null){
                savedPerspective = c.options.getPerspective();
                c.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            } else {
                c.options.setPerspective(savedPerspective);
                savedPerspective = null;
            }
        }
        wasPressed = pressed;
    }

    @Override public void render(DrawContext ctx, MinecraftClient c){}
}
