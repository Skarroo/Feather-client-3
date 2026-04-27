package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.text.Text;
import java.util.*;
public class ScoreboardModule extends HudModule {
    public ScoreboardModule(){super("scoreboard","Scoreboard",220,50);}
    @Override public void render(DrawContext ctx, MinecraftClient c){
        if(!config.enabled||c.player==null||c.world==null)return;
        Scoreboard sb=c.world.getScoreboard();
        ScoreboardObjective obj=sb.getObjectiveForSlot(
            net.minecraft.scoreboard.ScoreboardDisplaySlot.SIDEBAR);
        if(obj==null)return;
        List<ScoreboardEntry> entries=sb.getScoreboardEntries(obj)
            .stream()
            .sorted(Comparator.comparingInt(ScoreboardEntry::value).reversed())
            .limit(15)
            .toList();
        if(entries.isEmpty())return;
        int x=config.x,y=config.y;
        int maxW=c.textRenderer.getWidth(obj.getDisplayName().getString());
        for(ScoreboardEntry e:entries){
            int w=c.textRenderer.getWidth(e.owner())+
                  c.textRenderer.getWidth(String.valueOf(e.value()))+20;
            if(w>maxW)maxW=w;
        }
        int totalH=(entries.size()+1)*(c.textRenderer.fontHeight+2)+6;
        ctx.fill(x-2,y-2,x+maxW+2,y+totalH,0xCC111111);
        ctx.fill(x-2,y-2,x+maxW+2,y+c.textRenderer.fontHeight+4,0xCC1a1a1a);
        // Title
        int tw=c.textRenderer.getWidth(obj.getDisplayName().getString());
        ctx.drawText(c.textRenderer,obj.getDisplayName(),
            x+(maxW-tw)/2,y+2,0xFFFFFF55,true);
        int ey=y+c.textRenderer.fontHeight+4;
        for(ScoreboardEntry e:entries){
            ctx.drawText(c.textRenderer,e.owner(),x+2,ey,0xFFFFFFFF,false);
            String score=String.valueOf(e.value());
            int sw2=c.textRenderer.getWidth(score);
            ctx.drawText(c.textRenderer,score,x+maxW-sw2-2,ey,0xFFFF5555,false);
            ey+=c.textRenderer.fontHeight+2;
        }
    }
    @Override public int getWidth(MinecraftClient c){return 120;}
    @Override public int getHeight(MinecraftClient c){return 200;}
}
