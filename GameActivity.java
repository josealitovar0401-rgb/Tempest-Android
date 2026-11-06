package com.zapia.tempest;

import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.content.*;
import java.util.*;

public class GameActivity extends Activity {
 @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().getDecorView().setSystemUiVisibility(5894|1024|512);setContentView(new GameView(this));}
 static class Mob { float x,y,hp; boolean dead=false; Mob(float a,float b,float c){x=a;y=b;hp=c;} }
 class GameView extends View {
  Canvas c; Paint p=new Paint(3); float px=0,py=0, joyX=0,joyY=0; boolean joy=false; long last=System.currentTimeMillis(); int hp=100,level=1,wood=0,stone=0,exp=0,kills=0; ArrayList<Mob> mobs=new ArrayList<>(); String toast="Explora el bosque y derrota monstruos"; long toastTime=0; SharedPreferences save;
  int grass=Color.rgb(82,153,91), dark=Color.rgb(44,104,70);
  GameView(Context ctx){super(ctx);save=getSharedPreferences("save",0);px=save.getFloat("x",0);py=save.getFloat("y",0);wood=save.getInt("wood",0);stone=save.getInt("stone",0);level=save.getInt("level",1);exp=save.getInt("exp",0);hp=save.getInt("hp",100);for(int i=0;i<8;i++)mobs.add(new Mob((i%4-1.5f)*260, (i/4==0?-250:280),2));setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  void fill(int color){p.setColor(color);p.setStyle(Paint.Style.FILL);} void text(String s,float x,float y,int size,int color){fill(color);p.setTextSize(size);p.setTypeface(Typeface.create("sans-serif-medium",Typeface.BOLD));c.drawText(s,x,y,p);} void box(float x,float y,float w,float h,int color,float r){fill(color);c.drawRoundRect(x,y,x+w,y+h,r,r,p);}
  protected void onDraw(Canvas cv){super.onDraw(cv);c=cv;float W=getWidth(),H=getHeight(); if(W==0)return; c.save();c.scale(W/900f,H/500f);c.drawColor(grass);
   // meadows, paths and forest canopy
   fill(Color.rgb(105,176,101));for(int i=0;i<14;i++)for(int j=0;j<8;j++){float x=i*90-((px*.15f)%90),y=j*75-((py*.15f)%75);c.drawOval(x,y,x+48,y+17,p);}
   fill(Color.rgb(194,177,123));Path path=new Path();path.moveTo(450,500);path.lineTo(405,250);path.lineTo(490,150);path.lineTo(450,-10);path.lineTo(545,-10);path.lineTo(540,170);path.lineTo(470,260);path.lineTo(535,500);path.close();c.drawPath(path,p);
   for(int i=0;i<12;i++){float wx=((i*173+40-px*.45f)%1150+1150)%1150-125, wy=((i*113+90-py*.45f)%700+700)%700-100;tree(wx,wy);}
   // village buildings
   building(610-px*.18f,255-py*.18f,"TEMPest"); building(690-px*.18f,325-py*.18f,"Taller"); building(555-px*.18f,335-py*.18f,"Granja");
   for(Mob m:mobs)if(!m.dead){float sx=450+(m.x-px)*.8f,sy=250+(m.y-py)*.8f;if(sx>-30&&sx<930&&sy>-30&&sy<530){mob(sx,sy);}}
   // hero centered
   hero(450,250);
   // HUD
   box(18,14,250,85,0xCC182536,14);text("RIMURU  ·  NIVEL "+level,32,40,19,Color.WHITE);box(32,52,210,13,0xFF3A4651,7);box(32,52,210*hp/100f,13,0xFFE94D61,7);text("HP "+hp+"   EXP "+exp+"/5",32,83,15,Color.WHITE);
   box(684,14,198,66,0xCC182536,12);text("Tempest",700,40,20,Color.WHITE);text("Madera "+wood+"  Piedra "+stone,700,64,14,0xFFE0E8DA);
   box(320,16,260,43,0xAA182536,20);text("Misión: elimina 3 slimes  ("+kills+"/3)",337,44,15,Color.WHITE);
   // joystick and action controls
   fill(0x668FA3AD);c.drawCircle(105,405,68,p);fill(0x99EAF4F4);c.drawCircle(105+joyX*35,405+joyY*35,30,p);text("MOVER",67,488,13,Color.WHITE);
   box(758,385,105,88,0xAA26354A,24);fill(0xFF60BDEB);c.drawCircle(810,421,34,p);text("MAGIA",779,466,14,Color.WHITE);box(669,414,63,57,0xAA26354A,18);text("+",689,453,35,Color.WHITE);
   if(System.currentTimeMillis()-toastTime<3000){box(287,68,326,35,0xCC1D2D3B,18);text(toast,300,91,14,Color.WHITE);}
   c.restore();
  }
  void tree(float x,float y){fill(0xFF704C37);c.drawRoundRect(x-5,y,x+5,y+22,3,3,p);fill(0xFF27754B);c.drawCircle(x,y-7,24,p);fill(0xFF3C9858);c.drawCircle(x-8,y-13,15,p);}
  void building(float x,float y,String name){fill(0xFFB98652);c.drawRect(x-27,y-25,x+27,y+20,p);fill(0xFF753F40);Path r=new Path();r.moveTo(x-34,y-24);r.lineTo(x,y-53);r.lineTo(x+34,y-24);r.close();c.drawPath(r,p);fill(0xFF54372D);c.drawRect(x-7,y-1,x+7,y+20,p);text(name,x-29,y+37,11,Color.WHITE);}
  void hero(float x,float y){fill(0x55303030);c.drawOval(x-17,y+16,x+18,y+25,p);fill(0xFF23354E);c.drawRoundRect(x-13,y-8,x+13,y+22,7,7,p);fill(0xFF63CDE8);c.drawCircle(x,y-17,16,p);fill(0xFF154C80);c.drawOval(x-17,y-34,x+17,y-12,p);fill(Color.WHITE);c.drawCircle(x-5,y-18,2,p);c.drawCircle(x+5,y-18,2,p);}
  void mob(float x,float y){fill(0x55403040);c.drawOval(x-20,y+12,x+20,y+18,p);fill(0xFF6DD7D0);c.drawOval(x-19,y-12,x+19,y+15,p);fill(0xFFB5FFFF);c.drawCircle(x-6,y-2,3,p);c.drawCircle(x+6,y-2,3,p);fill(0xFF34566B);c.drawCircle(x,y-1,1.5f,p);}
  void say(String s){toast=s;toastTime=System.currentTimeMillis();}
  void attack(){Mob target=null;float d=100;for(Mob m:mobs)if(!m.dead){float a=(m.x-px)*(m.x-px)+(m.y-py)*(m.y-py);if(a<d*d){d=(float)Math.sqrt(a);target=m;}}if(target!=null){target.hp-=1;if(target.hp<=0){target.dead=true;kills++;exp++;if(exp>=5){level++;exp=0;hp=100;say("¡Subiste al nivel "+level+"!");}else say("¡Slime derrotado! +1 EXP");}else say("¡Impacto mágico!");}else say("No hay monstruos cerca");save();invalidate();}
  void save(){save.edit().putFloat("x",px).putFloat("y",py).putInt("wood",wood).putInt("stone",stone).putInt("level",level).putInt("exp",exp).putInt("hp",hp).apply();}
  public boolean onTouchEvent(android.view.MotionEvent e){float x=e.getX()*900/getWidth(),y=e.getY()*500/getHeight();int a=e.getActionMasked(),i=e.getActionIndex();float tx=e.getX(i)*900/getWidth(),ty=e.getY(i)*500/getHeight();if(a==MotionEvent.ACTION_DOWN||a==MotionEvent.ACTION_POINTER_DOWN){if(tx<210&&ty>300){joy=true;moveJoy(tx,ty);}else if(tx>740&&ty>350){attack();}else if(tx>650&&ty>400){if(wood<20){wood+=2;say("Recolectaste madera");}else {stone+=2;say("Recolectaste piedra");}save();invalidate();}return true;}if(a==MotionEvent.ACTION_MOVE&&joy){moveJoy(x,y);return true;}if(a==MotionEvent.ACTION_UP||a==MotionEvent.ACTION_CANCEL){joy=false;joyX=joyY=0;return true;}return true;}
  void moveJoy(float x,float y){joyX=Math.max(-1,Math.min(1,(x-105)/50));joyY=Math.max(-1,Math.min(1,(y-405)/50));px+=joyX*5;py+=joyY*5;long now=System.currentTimeMillis();if(now-last>400){for(Mob m:mobs)if(!m.dead&&Math.hypot(m.x-px,m.y-py)<34){hp-=5;if(hp<=0){hp=100;px=py=0;say("Te recuperaste en Tempest");}else say("¡Un slime te golpeó!");last=now;save();break;}last=now;}invalidate();}
 }
}
