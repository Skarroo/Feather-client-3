package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;
public class BlockAboveModule extends HudModule {
    public BlockAboveModule(){super("block_above","Block Indicator",2,170);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null||client.world==null)return;
        BlockPos pos=client.player.getBlockPos().up(2);
        if(!client.world.getBlockState(pos).isAir()){
            String name=client.world.getBlockState(pos).getBlock().getName().getString();
            drawText(ctx,client,"\u25B2 "+name);
        }
    }
    @Override public int getWidth(MinecraftClient c){return 100;}
}