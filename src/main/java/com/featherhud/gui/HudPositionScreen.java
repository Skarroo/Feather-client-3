package com.featherhud.gui;
import com.featherhud.FeatherHudClient;
import com.featherhud.config.ConfigManager;
import com.featherhud.config.HudModuleConfig;
import com.featherhud.hud.HudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.List;
public class HudPositionScreen extends Screen {
    private final Screen parent;
    private final List<HudModule> modules;
    private HudModule dragging=null;
    private int dragOffX,dragOffY,touchX,touchY;
    private boolean didDrag=false;
    private HudModule popupModule=null;
    private int popupX,popupY;
    public HudPositionScreen(Screen parent){super(Text.literal("Edit Positions"));this.parent=parent;this.modules=FeatherHudClient.hudRenderer.getModules();}
    @Override protected void init(){
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"),btn->close()).dimensions(width/2-40,height-22,80,16).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset"),btn->{modules.forEach(m->m.getConfig().resetToDefault());ConfigManager.save();}).dimensions(width/2-130,height-22,55,16).build());
    }
    @Override public void render(DrawContext ctx,int mx,int my,float delta){
        ctx.fill(0,0,width,height,0xAA000000);
        ctx.drawCenteredTextWithShadow(textRenderer,"Drag=move  Tap=toggle",width/2,3,0xFFAAAAAA);
        for(HudModule m:modules){HudModuleConfig c=m.getConfig();int mw=m.getWidth(client),mh=m.getHeight(client);boolean on=c.enabled;int border=on?0xFF00C853:0xFF666666,bg=on?0x2800AA00:0x28222222;ctx.fill(c.x-2,c.y-2,c.x+mw+2,c.y+mh+2,bg);ctx.fill(c.x-2,c.y-2,c.x+mw+2,c.y-1,border);ctx.fill(c.x-2,c.y+mh+1,c.x+mw+2,c.y+mh+2,border);ctx.fill(c.x-2,c.y-2,c.x-1,c.y+mh+2,border);ctx.fill(c.x+mw+1,c.y-2,c.x+mw+2,c.y+mh+2,border);ctx.drawText(textRenderer,m.getDisplayName(),c.x,c.y,on?0xFF00C853:0xFF888888,true);}
        if(popupModule!=null){int pw=110,ph=46;ctx.fill(popupX,popupY,popupX+pw,popupY+ph,0xFF1A1A1A);ctx.fill(popupX,popupY,popupX+pw,popupY+2,0xFF00C853);ctx.drawText(textRenderer,popupModule.getDisplayName(),popupX+4,popupY+5,0xFFFFFFFF,true);boolean en=popupModule.getConfig().enabled;ctx.drawText(textRenderer,en?"Disable":"Enable",popupX+4,popupY+18,en?0xFFFF5555:0xFF55FF55,false);ctx.drawText(textRenderer,"Reset pos",popupX+4,popupY+30,0xFF888888,false);}
        super.render(ctx,mx,my,delta);
    }
    @Override public boolean mouseClicked(double mx,double my,int button){
        int x=(int)mx,y=(int)my;
        if(popupModule!=null){HudModuleConfig c=popupModule.getConfig();if(y>=popupY+15&&y<=popupY+28){c.enabled=!c.enabled;ConfigManager.save();popupModule=null;return true;}if(y>=popupY+27&&y<=popupY+42){c.resetToDefault();ConfigManager.save();popupModule=null;return true;}popupModule=null;return true;}
        touchX=x;touchY=y;didDrag=false;
        for(int i=modules.size()-1;i>=0;i--){HudModule m=modules.get(i);HudModuleConfig c=m.getConfig();int mw=m.getWidth(client),mh=m.getHeight(client);if(x>=c.x-2&&x<=c.x+mw+2&&y>=c.y-2&&y<=c.y+mh+2){dragging=m;dragOffX=x-c.x;dragOffY=y-c.y;return true;}}
        return super.mouseClicked(mx,my,button);
    }
    @Override public boolean mouseDragged(double mx,double my,int button,double dx,double dy){if(dragging!=null){if(Math.abs((int)mx-touchX)>4||Math.abs((int)my-touchY)>4)didDrag=true;if(didDrag){HudModuleConfig c=dragging.getConfig();c.x=Math.max(0,Math.min(width-dragging.getWidth(client),(int)mx-dragOffX));c.y=Math.max(0,Math.min(height-dragging.getHeight(client),(int)my-dragOffY));}return true;}return super.mouseDragged(mx,my,button,dx,dy);}
    @Override public boolean mouseReleased(double mx,double my,int button){if(dragging!=null){if(!didDrag){popupModule=dragging;popupX=Math.min((int)mx,width-114);popupY=Math.min((int)my,height-50);}ConfigManager.save();dragging=null;didDrag=false;return true;}return super.mouseReleased(mx,my,button);}
    @Override public void close(){ConfigManager.save();client.setScreen(parent);}
    @Override public boolean shouldPause(){return false;}
}
