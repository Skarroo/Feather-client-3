package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.List;
import java.util.ArrayList;
public class EntityCounterModule extends HudModule {
    private int hostile=0,passive=0,players=0;
    public EntityCounterModule(){super("entity_counter","Entity Counter",210,92);}
    @Override public void tick(MinecraftClient client){
        if(client.world==null||client.player==null)return;
        hostile=0;passive=0;players=0;
        client.world.getEntities().forEach(e->{
            if(e==client.player)return;
            if(e instanceof HostileEntity)hostile++;
            else if(e instanceof PlayerEntity)players++;
            else if(e instanceof PassiveEntity)passive++;
        });
    }
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        List<String> lines=new ArrayList<>();
        lines.add("Hostile: \u00a7c"+hostile);
        lines.add("Passive: \u00a7a"+passive);
        lines.add("Players: \u00a7b"+players);
        drawLines(ctx,client,lines);
    }
    @Override public int getWidth(MinecraftClient c){return c.textRenderer.getWidth("Hostile: 999")+4;}
    @Override public int getHeight(MinecraftClient c){return 34;}
}
