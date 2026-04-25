package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
public class ZoomModule extends HudModule {
    public static KeyBinding zoomKey;
    private static boolean zooming=false;
    private static double prevFov=70;
    static{
        zoomKey=KeyBindingHelper.registerKeyBinding(new KeyBinding("key.featherhud.zoom",InputUtil.Type.KEYSYM,GLFW.GLFW_KEY_C,"category.featherhud"));
    }
    public ZoomModule(){super("zoom","Zoom",210,166);}
    @Override public void tick(MinecraftClient client){
        if(!config.enabled){if(zooming){client.options.getFov().setValue((int)prevFov);zooming=false;}return;}
        boolean pressed=zoomKey.isPressed();
        if(pressed&&!zooming){prevFov=client.options.getFov().getValue();client.options.getFov().setValue(15);zooming=true;}
        else if(!pressed&&zooming){client.options.getFov().setValue((int)prevFov);zooming=false;}
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        if(zooming)drawText(ctx,client,"\uD83D\uDD0D Zooming");
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("\uD83D\uDD0D Zooming")+4;}
}
