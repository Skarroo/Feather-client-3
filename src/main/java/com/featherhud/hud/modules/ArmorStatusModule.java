package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import java.util.*;
public class ArmorStatusModule extends HudModule {
    private static final int[]SLOTS={3,2,1,0};
    public ArmorStatusModule(){super("armor_status","Armor Status",2,170);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        List<ItemStack>ap=Arrays.stream(SLOTS).mapToObj(i->client.player.getInventory().getArmorStack(i)).toList();
        if(ap.stream().noneMatch(s->!s.isEmpty()))return;
        int x=config.x,y=config.y,gap=2,sw=18,tw=SLOTS.length*(sw+gap)-gap;
        if(config.showBackground){ctx.fill(x-2,y-2,x+tw+2,y+sw+8,0xCC111111);}
        for(int i=0;i<ap.size();i++){
            ItemStack s=ap.get(i);int ix=x+i*(sw+gap),iy=y;
            if(s.isEmpty()){ctx.fill(ix,iy,ix+16,iy+16,0x30FFFFFF);continue;}
            ctx.drawItem(s,ix,iy);
            if(s.isDamageable()&&s.isDamaged()){
                float p=(float)(s.getMaxDamage()-s.getDamage())/s.getMaxDamage();
                int bw=(int)(16*p),c2=durabilityColor(p);
                ctx.fill(ix,iy+17,ix+16,iy+19,0xFF000000);
                ctx.fill(ix,iy+17,ix+bw,iy+19,0xFF000000|c2);
            }
        }
    }
    @Override public int getWidth(MinecraftClient c){return SLOTS.length*20-2+4;}
    @Override public int getHeight(MinecraftClient c){return 24;}
}
