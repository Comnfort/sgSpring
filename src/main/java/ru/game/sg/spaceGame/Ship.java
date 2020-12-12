package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static ru.game.sg.spaceGame.Control.*;


@Component
@Scope("prototype")
class Ship {
    private static ImageIcon imgHero= new ImageIcon(ClassLoader.getSystemResource("img/ship.png"));
    private Image imgBuf;
    private Ellipse2D sh;                                //фигура героя
    private int x1, y1;                                   //центр героя
    private final int sizeHero = 70;                       //размер героя
    private int stepHero = 5;                             //шаг перемещения героя
    private double degree;                               //текущий угол поворота
    private int healthCount = 5;
    private int currentBord;
    private GameField gf;
    private int countDeaths = 0;

    int getCountDeaths() {
        return countDeaths;
    }

     void setCountDeaths(int countDeaths) {
        this.countDeaths = countDeaths;
    }

     int getCurrentBord() {
        return currentBord;
    }

     void setCurrentBord(int currentBord) {
        this.currentBord = currentBord;
    }

     int getHealthCount() {
        return healthCount;
    }

     void setHealthCount(int healthCount) {
        this.healthCount = healthCount;
    }

     String getHEALTH() {
        return  "❤";
    }

     Image getImgBuf() {
        return imgBuf;
    }

     int getSizeHero() {
        return sizeHero;
    }

     Ellipse2D getSh() {
        return sh;
    }

     int getX1() {
        return x1;
    }

     int getY1() {
        return y1;
    }

     double getDegree() {
        return degree;
    }

     void setStepHero(int stepHero) {
        this.stepHero = stepHero;
    }

     ImageIcon getImgHero() {
        return imgHero;
    }



   public void init(GameField gf) {
        this.gf = gf;
        currentBord = gf.getFieldW();
        degree = 0;
        x1 = 450;
        y1 = 300;
        sh = new Ellipse2D.Double(x1 + gf.getCurrentPanelX(), y1 + gf.getCurrentPanelY(), sizeHero, sizeHero);
        imgBuf = createHeroImg();
    }



    private Image createHeroImg() {
        BufferedImage defaultBuf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = defaultBuf.createGraphics();
        gr.rotate(Math.toRadians(degree), 50, 50);
        gr.setColor(Color.GREEN);
        gr.drawImage(imgHero.getImage(), 10, 5, 80, 90, null);
        gr.dispose();
        return defaultBuf;
    }


    private boolean checkCollision(int x, int y) {
        if (gf == null || gf.getEnemyList().size() == 0) return false;
        for (int i = 0; i < gf.getEnemyList().size(); i++) {
            double dist = Point2D.distance(x + (sizeHero >> 1), y + (sizeHero >> 1),
                    gf.getEnemyList().get(i).getShEn().getCenterX(), gf.getEnemyList().get(i).getShEn().getCenterY());
            if (dist < 70) return true;
        }
        return false;
    }


     void setCourse() {                //задаю направление
        if (RIGHT_ROTATE.isPressed()) {
            degree += 5;
            if (degree > 360) {
                degree = 5;
            }
            imgBuf = createHeroImg();
        } else if (LEFT_ROTATE.isPressed()) {
            degree -= 5;
            if (degree < 0) {
                degree = 355;
            }
            imgBuf = createHeroImg();
        } else {
            imgBuf = createHeroImg();
        }
        sh = new Ellipse2D.Double(gf.getCurrentPanelX() + x1, gf.getCurrentPanelY() + y1, sizeHero, sizeHero);
    }


    private int checkBorderW(int step) {                      //границы перемещения корабля, текущее окно
        int a = (int) (sh.getX() + step);
        if (checkCollision(a, (int) sh.getY())) return x1;
        if (a >= gf.getCurrentPanelX() + gf.getFieldWidth() - sizeHero - 10) {
            setWindowLocW(-stepHero);
            return gf.getFieldWidth() - sizeHero - 10;
        }
        if (a <= gf.getCurrentPanelX() + 10) {
            setWindowLocW(stepHero);
            return 10;
        }
        return x1 + step;
    }


    private int checkBorderH(int step) {                       //границы перемещения корабля, текущее окно
        int b = (int) (sh.getY() + step);
        if (checkCollision((int) sh.getX(), b)) return y1;
        if (b >= gf.getCurrentPanelY() + gf.getFieldHeight() - sizeHero - 10 - 40) {
            setWindowLocH(-stepHero);
            return gf.getFieldHeight() - sizeHero - 10 - 40;
        }
        if (b <= gf.getCurrentPanelY() + 10) {
            setWindowLocH(stepHero);
            return 10;
        }
        return y1 + step;
    }


    private void setWindowLocW(int step) {                      //границы перемещения корабля, глобал
        if (gf.getCurrentWindowX() + step < 0 & gf.getCurrentWindowX() + step > -gf.getFieldW() + currentBord) {
            gf.setLoc(gf.getCurrentWindowX() + step, gf.getCurrentWindowY(), step);
        }
    }


    private void setWindowLocH(int step) {                    //границы перемещения корабля, глобал
        if (gf.getCurrentWindowY() + step < 0 & gf.getCurrentWindowY() + step > -gf.getFieldH() + gf.getFieldHeight()) {
            gf.setLoc(gf.getCurrentWindowX(), gf.getCurrentWindowY() + step, step);
        }
    }


     void controlHero() {                      //Управление героем
        if (UP.isPressed() && RIGHT.isPressed()) {
            degree = 45;
            y1 = checkBorderH(-stepHero);
            x1 = checkBorderW(stepHero);
            return;
        }
        if (UP.isPressed() && LEFT.isPressed()) {
            degree = 315;
            y1 = checkBorderH(-stepHero);
            x1 = checkBorderW(-stepHero);
            return;
        }
        if (DOWN.isPressed() && RIGHT.isPressed()) {
            degree = 135;
            y1 = checkBorderH(stepHero);
            x1 = checkBorderW(stepHero);
            return;
        }
        if (DOWN.isPressed() && LEFT.isPressed()) {
            degree = 225;
            y1 = checkBorderH(stepHero);
            x1 = checkBorderW(-stepHero);
            return;
        }

        if (LEFT.isPressed()) {
            degree = 270;
            x1 = checkBorderW(-stepHero);
            return;
        }
        if (UP.isPressed()) {
            degree = 0;
            y1 = checkBorderH(-stepHero);
            return;
        }
        if (RIGHT.isPressed()) {
            degree = 90;
            x1 = checkBorderW(stepHero);
            return;
        }
        if (DOWN.isPressed()) {
            degree = 180;
            y1 = checkBorderH(stepHero);
        }
    }

     void resetControl(){
        for (Control value : values()) {
            value.setPressed(false);
        }

    }
}
