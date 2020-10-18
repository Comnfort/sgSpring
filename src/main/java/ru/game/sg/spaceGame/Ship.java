package ru.game.sg.spaceGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import static ru.game.sg.spaceGame.Control.*;


@Component("ship")
@Lazy
class Ship {
   private ImageIcon imgHero;
   private Image imgBuf;
   private Ellipse2D sh;                                //фигура героя
   private int x1,y1;                                   //центр героя
   private final int sizeHero=70;                       //размер героя
   private int stepHero =5;                             //шаг перемещения героя
   private double degree;                               //текущий угол поворота
   private final String HEALTH="❤";
   private int healthCount=5;
   private int currentBord;
   private GameField gf;
   private double dist;
   private int countDeaths=0;

    public int getCountDeaths() {
        return countDeaths;
    }

    public void setCountDeaths(int countDeaths) {
        this.countDeaths = countDeaths;
    }

    public int getStepHero() {
       return stepHero;
   }

   public int getCurrentBord() {
       return currentBord;
   }

   public void setCurrentBord(int currentBord) {
       this.currentBord = currentBord;
   }

   public int getHealthCount() {
       return healthCount;
   }

   public void setHealthCount(int healthCount) {
       this.healthCount = healthCount;
   }

   public String getHEALTH() {
       return HEALTH;
   }

   public Image getImgBuf() {
       return imgBuf;
   }

   public int getSizeHero() {
       return sizeHero;
   }

   public Ellipse2D getSh() {
       return sh;
   }

   public int getX1() {
       return x1;
   }

   public int getY1() {
       return y1;
   }

   public double getDegree() {
       return degree;
   }

   public void setStepHero(int stepHero) {
       this.stepHero = stepHero;
   }

   public ImageIcon getImgHero() {
        return imgHero;
    }

    Ship(GameField gf) {
       this.gf=gf;
       imgHero=new ImageIcon(ClassLoader.getSystemResource("img/ship.png"));
       currentBord =gf.getFieldW();
       degree=0;
       x1=450;y1=300;
       sh=new Ellipse2D.Double(x1+ gf.getCurrentPanelX(),y1+ gf.getCurrentPanelY(),sizeHero,sizeHero);
       imgBuf =createHeroImg();
   }


   private Image createHeroImg(){
       BufferedImage imgBuf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
       Graphics2D gr=imgBuf.createGraphics();
       gr.rotate(Math.toRadians(degree),50,50);
       gr.setColor(Color.GREEN);
       gr.drawImage(imgHero.getImage(),10,5,80,90,null);
       gr.dispose();
       return  imgBuf;
   }


   private boolean checkCollision(int x,int y)
   {
       if (gf==null||gf.getEnemyList().size()==0)return false;
       for (int i = 0; i < gf.getEnemyList().size(); i++) {
           dist = Point2D.distance(x+sizeHero/2,y+sizeHero/2,
                   gf.getEnemyList().get(i).getShEn().getCenterX(),gf.getEnemyList().get(i).getShEn().getCenterY());
           if (dist<70)return true;
       }
       return false;
   }


   public void setCourse(){                //задаю направление
       if (RIGHT_ROTATE.isPressed()){degree+=5;
           if (degree>360){degree=5;}
           imgBuf =createHeroImg();}
       else if (LEFT_ROTATE.isPressed()){degree-=5;
           if (degree<0){degree=355;}
           imgBuf =createHeroImg();}
       else {imgBuf =createHeroImg();}
       sh=new Ellipse2D.Double(gf.getCurrentPanelX() +x1, gf.getCurrentPanelY() +y1,sizeHero,sizeHero);
   }


   private int checkBorderW(int step){                      //границы перемещения корабля, текущее окно
       int a=(int) (sh.getX()+step);
       if (checkCollision(a,(int) sh.getY())) return x1;
       if (a >= gf.getCurrentPanelX()+gf.getFieldWidth()-sizeHero-10){setWindowLocW(-stepHero);
       return gf.getFieldWidth()-sizeHero-10;}
       if (a<=gf.getCurrentPanelX()+10){setWindowLocW(stepHero);    return 10;}
       return x1+step;
   }


   private int checkBorderH(int step){                       //границы перемещения корабля, текущее окно
       int b= (int) (sh.getY()+step);
       if (checkCollision((int) sh.getX(),b)) return y1;
       if (b >= gf.getCurrentPanelY()+gf.getFieldHeight()-sizeHero-10-40)
       {setWindowLocH(-stepHero);  return gf.getFieldHeight()-sizeHero-10-40;}
       if (b<=gf.getCurrentPanelY()+10){setWindowLocH(stepHero);   return 10;}
       return y1+step;
   }


   private void setWindowLocW(int step){                      //границы перемещения корабля, глобал
       if (gf.getCurrentWindowX()+step<0&gf.getCurrentWindowX()+step>-gf.getFieldW()+ currentBord){
           gf.setLoc(gf.getCurrentWindowX()+step ,gf.getCurrentWindowY(),step);
       }
   }


   private void setWindowLocH(int step){                    //границы перемещения корабля, глобал
       if (gf.getCurrentWindowY()+step<0&gf.getCurrentWindowY()+step>-gf.getFieldH()+gf.getFieldHeight()){
           gf.setLoc(gf.getCurrentWindowX() ,gf.getCurrentWindowY()+step,step);
       }
   }


   public void controlHero(){                      //Управление героем
       if (UP.isPressed() && RIGHT.isPressed()){degree=45;  y1= checkBorderH(-stepHero); x1=checkBorderW(stepHero);   return; }
       if (UP.isPressed() && LEFT.isPressed()){degree=315; y1= checkBorderH(-stepHero); x1=checkBorderW(-stepHero);  return;}
       if (DOWN.isPressed() && RIGHT.isPressed()){degree=135; y1= checkBorderH(stepHero);  x1=checkBorderW(stepHero);   return;}
       if (DOWN.isPressed() && LEFT.isPressed()){degree=225; y1= checkBorderH(stepHero);  x1=checkBorderW(-stepHero);  return;}

       if (LEFT.isPressed()) {degree=270; x1=checkBorderW(-stepHero); return;}
       if (UP.isPressed()) {degree=0;   y1= checkBorderH(- stepHero);  return;}
       if (RIGHT.isPressed()) {degree=90;  x1=checkBorderW(stepHero);  return;}
       if (DOWN.isPressed()) {degree=180; y1= checkBorderH(stepHero);}
   }
}
