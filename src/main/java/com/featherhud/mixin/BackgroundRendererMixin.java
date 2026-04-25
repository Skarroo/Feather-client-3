package com.featherhud.mixin;
import com.featherhud.hud.modules.NoFogModule;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method="applyFog",at=@At("HEAD"),cancellable=true)
    private static void noFog(net.minecraft.client.render.Camera camera,BackgroundRenderer.FogType fogType,org.joml.Vector4f color,float viewDistance,boolean thickenFog,float tickDelta,CallbackInfo ci){
        if(NoFogModule.isEnabled())ci.cancel();
    }
}
