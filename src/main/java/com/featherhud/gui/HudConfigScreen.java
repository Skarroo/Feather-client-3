package com.featherhud.gui;
import com.featherhud.FeatherHudClient;
import com.featherhud.config.ConfigManager;
import com.featherhud.hud.HudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.List;
import java.util.stream.Collectors;
public class HudConfigScreen extends Screen {
    private final Screen parent;
    private final List<HudModule> modules;
    private int panelX,panelY;
    private static final int PW=720,PH=460,HH=44,TH=30,FH=36,CW=156,CH=90,CG=8,PAD=12;
    private static final int CBG=0xFF141414,CHD=0xFF0C0C0C,CAC=0xFFE53935,CCD=0xFF1E1E1E,CCH=0xFF2A2A2A,CEN=0xFF00C853,CDI=0xFF424242,CTX=0xFFEEEEEE,CMT=0xFF888888,CBR=0xFF2E2E2E,CTS=0xFF2A2A2A;
    private static final String[] TABS={"All","HUD","Visual","PvP"};
    private String activeTab="All",search="";
    private int scrollY=0;
    private TextFieldWidget searchBox;
    public HudConfigScreen(Screen parent){super(Text.literal("Feather HUD"));this.parent=parent;this.modules=FeatherHudClient.hudRenderer.getModules();}
    @Override protected void init(){
        panelX=(width-PW)/2;panelY=(height-PH)/2;
        searchBox=new TextFieldWidget(textRenderer,panelX+PW-168,panelY+12,155,20,Text.empty());
        searchBox.setMaxLength(30);searchBox.setSuggestion("Search...");searchBox.setChangedListener(t->{search=t.toLowerCase();scrollY=0;});
        addDrawableChild(searchBox);
        addDrawableChild(ButtonWidget.builder(Text.literal("Edit Positions"),btn->client.setScreen(new HudPositionScreen(this))).dimensions(panelX+10,panelY+PH-FH+8,120,20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset All"),btn->{modules.forEach(m->m.getConfig().resetToDefault());ConfigManager.save();}).dimensions(panelX+138,panelY+PH-FH+8,80,20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"),btn->close()).dimensions(panelX+PW-78,panelY+PH-FH+8,68,20).build());
    }
    private List<HudModule> filtered(){return modules.stream().filter(m->{if(!activeTab.equals("All")&&!tabFor(m).equals(activeTab))return false;return search.isEmpty()||m.getDisplayName().toLowerCase().contains(search);}).collect(Collectors.toList());}
    private String tabFor(HudModule m){return switch(m.getId()){case "fps","ping","tps","memory","coords","biome","clock","day_counter","player_count","chunk"->"HUD";case "armor_status","potion_effects","compass","toggle_sprint","held_item","target_health"->"Visual";case "cps","keystrokes","speedometer","reach"->"PvP";default->"HUD";};}
    @Override public void render(DrawContext ctx,int mx,int my,float delta){
        ctx.fill(0,0,width,height,0xBB000000);
        ctx.fill(panelX+4,panelY+4,panelX+PW+4,panelY+PH+4,0x44000000);
        ctx.fill(panelX,panelY,panelX+PW,panelY+PH,CBG);
        ctx.fill(panelX,panelY,panelX+PW,panelY+HH,CHD);
        ctx.fill(panelX,panelY,panelX+4,panelY+HH,CAC);
        ctx.fill(panelX,panelY+HH-1,panelX+PW,panelY+HH,CBR);
        ctx.drawText(textRenderer,"MOD MENU",panelX+12,panelY+8,CAC,true);
        ctx.drawText(textRenderer,"Feather HUD  |  Press H to open",panelX+12,panelY+22,CMT,false);
        int tabY=panelY+HH;
        ctx.fill(panelX,tabY,panelX+PW,tabY+TH,0xFF111111);
        ctx.fill(panelX,tabY+TH-1,panelX+PW,tabY+TH,CBR);
        int tx=panelX+PAD;
        for(String tab:TABS){int tw=textRenderer.getWidth(tab)+18;boolean sel=tab.equals(activeTab);if(sel){ctx.fill(tx,tabY+3,tx+tw,tabY+TH-1,CTS);ctx.fill(tx,tabY+TH-2,tx+tw,tabY+TH,CAC);}ctx.drawText(textRenderer,tab,tx+9,tabY+10,sel?CTX:CMT,false);tx+=tw+3;}
        List<HudModule> vis=filtered();
        ctx.drawText(textRenderer,vis.size()+" modules",panelX+PW-185,tabY+10,CMT,false);
        int gT=tabY+TH,gB=panelY+PH-FH;
        ctx.enableScissor(panelX,gT,panelX+PW,gB);
        int cols=(PW-PAD*2+CG)/(CW+CG),sX=panelX+PAD,sY=gT+PAD-scrollY;
        for(int i=0;i<vis.size();i++){
            HudModule m=vis.get(i);int col=i%cols,row=i/cols,cx=sX+col*(CW+CG),cy=sY+row*(CH+CG);
            if(cy+CH<gT||cy>gB)continue;
            boolean hov=mx>=cx&&mx<=cx+CW&&my>=cy&&my<=cy+CH,en=m.getConfig().enabled;
            ctx.fill(cx,cy,cx+CW,cy+CH,hov?CCH:CCD);
            ctx.fill(cx,cy,cx+CW,cy+3,en?CEN:CDI);
            ctx.fill(cx,cy,cx+1,cy+CH,CBR);ctx.fill(cx+CW-1,cy,cx+CW,cy+CH,CBR);ctx.fill(cx,cy+CH-1,cx+CW,cy+CH,CBR);
            String name=m.getDisplayName();while(name.length()>1&&textRenderer.getWidth(name)>CW-22)name=name.substring(0,name.length()-1);
            ctx.drawText(textRenderer,name,cx+8,cy+10,CTX,true);
            String cat=tabFor(m);int cpw=textRenderer.getWidth(cat)+8;
            ctx.fill(cx+8,cy+26,cx+8+cpw,cy+37,0xFF2D2D2D);ctx.drawText(textRenderer,cat,cx+12,cy+28,CMT,false);
            int bY=cy+CH-22,bW=CW-16;
            ctx.fill(cx+8,bY,cx+8+bW,bY+14,en?0xFF1B5E20:0xFF1F1F1F);
            ctx.fill(cx+8,bY,cx+8+bW,bY+1,en?CEN:CDI);
            String lbl=en?"ENABLED":"DISABLED";int lw=textRenderer.getWidth(lbl);
            ctx.drawText(textRenderer,lbl,cx+8+(bW-lw)/2,bY+3,en?CEN:CDI,false);
        }
        ctx.disableScissor();
        int fY=panelY+PH-FH;ctx.fill(panelX,fY,panelX+PW,panelY+PH,CHD);ctx.fill(panelX,fY,panelX+PW,fY+1,CBR);
        super.render(ctx,mx,my,delta);
    }
    @Override public boolean mouseClicked(double mx,double my,int button){
        if(super.mouseClicked(mx,my,button))return true;
        int x=(int)mx,y=(int)my,tabY=panelY+HH,tx=panelX+PAD;
        for(String tab:TABS){int tw=textRenderer.getWidth(tab)+18;if(x>=tx&&x<=tx+tw&&y>=tabY&&y<=tabY+TH){activeTab=tab;scrollY=0;return true;}tx+=tw+3;}
        int gT=tabY+TH,cols=(PW-PAD*2+CG)/(CW+CG),sX=panelX+PAD,sY=gT+PAD-scrollY;
        List<HudModule> vis=filtered();
        for(int i=0;i<vis.size();i++){HudModule m=vis.get(i);int col=i%cols,row=i/cols,cx=sX+col*(CW+CG),cy=sY+row*(CH+CG);if(x>=cx&&x<=cx+CW&&y>=cy&&y<=cy+CH){m.getConfig().enabled=!m.getConfig().enabled;ConfigManager.save();return true;}}
        return false;
    }
    @Override public boolean mouseScrolled(double mx,double my,double h,double v){scrollY=Math.max(0,scrollY-(int)(v*15));return true;}
    @Override public void close(){ConfigManager.save();client.setScreen(parent);}
    @Override public boolean shouldPause(){return false;}
}
