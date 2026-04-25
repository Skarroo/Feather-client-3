package com.featherhud.gui;
import com.featherhud.FeatherHudClient;
import com.featherhud.config.ConfigManager;
import com.featherhud.hud.HudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.List;
import java.util.stream.Collectors;
public class HudConfigScreen extends Screen {
    private final Screen parent;
    private final List<HudModule> modules;
    private int panelX,panelY,PW,PH,CW,CH,cols;
    private static final int HH=28,TH=18,FH=24,CG=5,PAD=7;
    private static final int CBG=0xFF141414,CHD=0xFF0C0C0C,CAC=0xFFE53935,CCD=0xFF1E1E1E,CCH=0xFF2A2A2A,CEN=0xFF00C853,CDI=0xFF424242,CTX=0xFFEEEEEE,CMT=0xFF888888,CBR=0xFF2E2E2E;
    private static final String[]TABS={"All","HUD","Visual","PvP"};
    private String activeTab="All",search="";
    private int scrollY=0;
    public HudConfigScreen(Screen parent){super(Text.literal("Feather HUD"));this.parent=parent;this.modules=FeatherHudClient.hudRenderer.getModules();}
    @Override protected void init(){
        PW=(int)(width*0.95f);PH=(int)(height*0.90f);panelX=(width-PW)/2;panelY=(height-PH)/2;
        cols=PW>400?4:3;CW=(PW-PAD*2-CG*(cols-1))/cols;CH=(int)(CW*0.58f);
        int btnY=panelY+PH-FH+4,btnH=FH-8;
        addDrawableChild(ButtonWidget.builder(Text.literal("Positions"),btn->client.setScreen(new HudPositionScreen(this))).dimensions(panelX+4,btnY,80,btnH).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset"),btn->{modules.forEach(m->m.getConfig().resetToDefault());ConfigManager.save();}).dimensions(panelX+88,btnY,55,btnH).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"),btn->close()).dimensions(panelX+PW-58,btnY,54,btnH).build());
    }
    private List<HudModule> filtered(){return modules.stream().filter(m->{if(!activeTab.equals("All")&&!tabFor(m).equals(activeTab))return false;return search.isEmpty()||m.getDisplayName().toLowerCase().contains(search);}).collect(Collectors.toList());}
    private String tabFor(HudModule m){return switch(m.getId()){case "fps","ping","tps","memory","coords","biome","clock","day_counter","player_count","chunk"->"HUD";case "armor_status","potion_effects","compass","toggle_sprint","held_item","target_health"->"Visual";case "cps","keystrokes","speedometer","reach"->"PvP";default->"HUD";};}
    @Override public void render(DrawContext ctx,int mx,int my,float delta){
        ctx.fill(0,0,width,height,0xCC000000);
        ctx.fill(panelX+3,panelY+3,panelX+PW+3,panelY+PH+3,0x33000000);
        ctx.fill(panelX,panelY,panelX+PW,panelY+PH,CBG);
        ctx.fill(panelX,panelY,panelX+PW,panelY+HH,CHD);
        ctx.fill(panelX,panelY,panelX+3,panelY+HH,CAC);
        ctx.fill(panelX,panelY+HH-1,panelX+PW,panelY+HH,CBR);
        ctx.drawText(textRenderer,"MOD MENU",panelX+7,panelY+4,CAC,true);
        ctx.drawText(textRenderer,"| Feather HUD",panelX+7+textRenderer.getWidth("MOD MENU")+4,panelY+4,CMT,false);
        String cnt=filtered().size()+" mods";ctx.drawText(textRenderer,cnt,panelX+PW-textRenderer.getWidth(cnt)-6,panelY+4,CMT,false);
        int tabY=panelY+HH;
        ctx.fill(panelX,tabY,panelX+PW,tabY+TH,0xFF111111);
        ctx.fill(panelX,tabY+TH-1,panelX+PW,tabY+TH,CBR);
        int tx=panelX+PAD;
        for(String tab:TABS){int tw=textRenderer.getWidth(tab)+10;boolean sel=tab.equals(activeTab);if(sel){ctx.fill(tx,tabY+1,tx+tw,tabY+TH-1,0xFF2A2A2A);ctx.fill(tx,tabY+TH-2,tx+tw,tabY+TH,CAC);}ctx.drawText(textRenderer,tab,tx+5,tabY+5,sel?CTX:CMT,false);tx+=tw+2;}
        int gT=tabY+TH,gB=panelY+PH-FH;
        ctx.enableScissor(panelX,gT,panelX+PW,gB);
        List<HudModule>vis=filtered();int sX=panelX+PAD,sY=gT+PAD-scrollY;
        for(int i=0;i<vis.size();i++){
            HudModule m=vis.get(i);int col=i%cols,row=i/cols,cx=sX+col*(CW+CG),cy=sY+row*(CH+CG);
            if(cy+CH<gT||cy>gB)continue;
            boolean hov=mx>=cx&&mx<=cx+CW&&my>=cy&&my<=cy+CH,en=m.getConfig().enabled;
            ctx.fill(cx,cy,cx+CW,cy+CH,hov?CCH:CCD);
            ctx.fill(cx,cy,cx+CW,cy+2,en?CEN:CDI);
            ctx.fill(cx,cy,cx+1,cy+CH,CBR);ctx.fill(cx+CW-1,cy,cx+CW,cy+CH,CBR);ctx.fill(cx,cy+CH-1,cx+CW,cy+CH,CBR);
            String name=m.getDisplayName();while(name.length()>1&&textRenderer.getWidth(name)>CW-8)name=name.substring(0,name.length()-1);
            ctx.drawText(textRenderer,name,cx+4,cy+5,CTX,true);
            String cat=tabFor(m);int bw=textRenderer.getWidth(cat)+6;
            ctx.fill(cx+4,cy+16,cx+4+bw,cy+25,0xFF252525);ctx.drawText(textRenderer,cat,cx+7,cy+17,CMT,false);
            int tby=cy+CH-14;ctx.fill(cx+4,tby,cx+CW-4,tby+10,en?0xFF1B5E20:0xFF1A1A1A);ctx.fill(cx+4,tby,cx+CW-4,tby+1,en?CEN:CDI);
            String lbl=en?"ON":"OFF";int lw=textRenderer.getWidth(lbl);ctx.drawText(textRenderer,lbl,cx+(CW-lw)/2,tby+1,en?CEN:CDI,false);
        }
        ctx.disableScissor();
        int fY=panelY+PH-FH;ctx.fill(panelX,fY,panelX+PW,panelY+PH,CHD);ctx.fill(panelX,fY,panelX+PW,fY+1,CBR);
        super.render(ctx,mx,my,delta);
    }
    @Override public boolean mouseClicked(double mx,double my,int button){
        if(super.mouseClicked(mx,my,button))return true;
        int x=(int)mx,y=(int)my,tabY=panelY+HH,tx=panelX+PAD;
        for(String tab:TABS){int tw=textRenderer.getWidth(tab)+10;if(x>=tx&&x<=tx+tw&&y>=tabY&&y<=tabY+TH){activeTab=tab;scrollY=0;return true;}tx+=tw+2;}
        int gT=tabY+TH,sX=panelX+PAD,sY=gT+PAD-scrollY;
        List<HudModule>vis=filtered();
        for(int i=0;i<vis.size();i++){int col=i%cols,row=i/cols,cx=sX+col*(CW+CG),cy=sY+row*(CH+CG);if(x>=cx&&x<=cx+CW&&y>=cy&&y<=cy+CH){vis.get(i).getConfig().enabled=!vis.get(i).getConfig().enabled;ConfigManager.save();return true;}}
        return false;
    }
    @Override public boolean mouseScrolled(double mx,double my,double h,double v){scrollY=Math.max(0,scrollY-(int)(v*12));return true;}
    @Override public void close(){ConfigManager.save();client.setScreen(parent);}
    @Override public boolean shouldPause(){return false;}
}
