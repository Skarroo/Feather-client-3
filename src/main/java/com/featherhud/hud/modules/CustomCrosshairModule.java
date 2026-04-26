package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class CustomCrosshairModule extends HudModule {
    public CustomCrosshairModule(){super("custom_crosshair","Custom Crosshair",0,0);}
    @Override public void render(DrawContext ctx, MinecraftClient c){}
}
