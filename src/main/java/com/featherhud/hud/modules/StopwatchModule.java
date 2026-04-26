package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
public class StopwatchModule extends HudModule {
    private long startTime=0,elapsed=0;
    private boolean running=false;
    private static final KeyBinding KEY=KeyBindingHelper.registerKeyBinding(new KeyBinding("key.featherhud.stopwatch",InputUtil.Type.KEYSYM,GLFW.GLFW_KEY_UNKNOWN,"category.featherhud"));
    public StopwatchModule(){super("stopwatch","Stopwatch",210,62);}
    @Override public void tick(MinecraftClient client){
        if(KEY.wasPressed()){if(!running){startTime=System.currentTimeMillis()-elapsed;running=true;}else{elapsed=System.currentTimeMillis()-startTime;running=false;}}
        if(running)elapsed=System.currentTimeMillis()-startTime;
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        long s=elapsed/1000,ms=(elapsed%1000)/10;
        drawText(ctx,client,String.format("%s%02d:%02d.%02d",running?"\u00a7a":"\u00a7e",s/60,s%60,ms));
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("99:99.99")+4;}
}