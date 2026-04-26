package com.featherhud.hud.modules;
import com.featherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
public class KeystrokesModule extends HudModule {
    private static final int K=18,G=2,ST=K+G;
    public KeystrokesModule(){super("keystrokes","Keystrokes",160,100);}
    @Override public void render(DrawContext ctx,MinecraftClient client){
        if(!config.enabled||client.player==null)return;
        boolean lmb=client.options.attackKey.isPressed(),rmb=client.options.useKey.isPressed();
        boolean w=client.options.forwardKey.isPressed(),a=client.options.leftKey.isPressed();
        boolean s=client.options.backKey.isPressed(),d=client.options.rightKey.isPressed();
        boolean sp=client.options.jumpKey.isPressed();
        int ox=config.x,oy=config.y,ww=3*ST-G;
        if(config.showBackground){ctx.fill(ox-3,oy-3,ox+ww+3,oy+4*ST-G+3,0xCC111111);}
        dk(ctx,client,"W",ox+ST,oy,K,K,w);
        dk(ctx,client,"A",ox,oy+ST,K,K,a);
        dk(ctx,client,"S",ox+ST,oy+ST,K,K,s);
        dk(ctx,client,"D",ox+2*ST,oy+ST,K,K,d);
        dk(ctx,client,"___",ox,oy+2*ST,ww,K,sp);
        int hw=(ww-G)/2;
        dk(ctx,client,"L",ox,oy+3*ST,hw,K,lmb);
        dk(ctx,client,"R",ox+hw+G,oy+3*ST,ww-hw-G,K,rmb);
    }
    private void dk(DrawContext ctx,MinecraftClient mc,String l,int x,int y,int w,int h,boolean p){
        int bg=p?0xCCE53935:0xCC1A1A1A,border=p?0xFFFF6659:0xFF333333,tc=p?0xFFFFFFFF:0xFFAAAAAA;
        ctx.fill(x,y,x+w,y+h,bg);
        ctx.fill(x,y,x+w,y+1,border);ctx.fill(x,y+h-1,x+w,y+h,border);
        ctx.fill(x,y,x+1,y+h,border);ctx.fill(x+w-1,y,x+w,y+h,border);
        if(!l.equals("___")){int tw=mc.textRenderer.getWidth(l),th=mc.textRenderer.fontHeight;ctx.drawText(mc.textRenderer,l,x+(w-tw)/2,y+(h-th)/2,tc,false);}
    }
    @Override public int getWidth(MinecraftClient c){return 3*(K+G)-G+6;}
    @Override public int getHeight(MinecraftClient c){return 4*(K+G)-G+6;}
}
