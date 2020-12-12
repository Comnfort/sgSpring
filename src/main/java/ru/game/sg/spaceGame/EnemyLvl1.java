package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


@Component
@Scope("prototype")
class EnemyLvl1 {

    private static Image defaultImg=new ImageIcon(ClassLoader.getSystemResource("img/shipEn.png")).getImage();

    private Image imgEnemy;
    private int x1, y1, healthCount = 1;
    private double currentDegree;
    private double needDegree;
    private Ellipse2D shEn;
    private GameField gf;
    private double x, y;
    private int timeFire = 1;
    private boolean distance;
    private boolean targetShip = false;

    double getCurrentDegree() {
        return currentDegree;
    }

     int getHealthCount() {
        return healthCount;
    }

     void setHealthCount(int healthCount) {
        this.healthCount = healthCount;
    }

     Image getImgEnemy() {
        return imgEnemy;
    }

     Ellipse2D getShEn() {
        return shEn;
    }

     int getX1() {
        return x1;
    }

     int getY1() {
        return y1;
    }


    public void  init(GameField gf) {
        x1 = gf.getStartPointEnemy().x;
        y1 = gf.getStartPointEnemy().y;
        this.gf = gf;
        int sizeHero = gf.getShip().getSizeHero();
        shEn = new Ellipse2D.Double(x1, y1, sizeHero, sizeHero);
        currentDegree = 270;
        imgEnemy = createEnemyImg();
        x = shEn.getCenterX();
        y = shEn.getCenterY();
    }


     void reDrawEnemy(double x, double y) {                //задаю направление
        if (checkDistance()) {
            double tempDeg = Math.toDegrees(-Math.PI / 2 + Math.atan2(this.y - y, this.x - x));
            needDegree = tempDeg > 0 ? tempDeg : tempDeg + 360;
            //подгоняем угол  к игроку
            //сравниваем угол текущий с необходимым
            solutionDegree();
            imgEnemy = createEnemyImg();
        }
    }


    private boolean checkDistance() {
        distance = Point2D.distance(shEn.getCenterX(), shEn.getCenterY(),
                gf.getShip().getSh().getCenterX(), gf.getShip().getSh().getCenterY()) > 1000;
        if (distance) {
            targetShip = false;
            return false;
        }
        return true;
    }


     void fire() {
        ++timeFire;
         boolean energyFire = timeFire % 80 == 0;
        if (energyFire && !distance && targetShip) {
            gf.getFr().getProxyInst().getSound().runSound("sound/выстрел.wav");
            Fire f = gf.getFr().getProxyInst().getFire();
            gf.add(f);
            f.fireEn(gf, this);
            targetShip = false;
        }
    }


     private Image createEnemyImg() {
        BufferedImage imgBuf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = imgBuf.createGraphics();
        gr.rotate(Math.toRadians(currentDegree), 50, 50);
        gr.drawImage(defaultImg, 10, 5, 80, 90, null);
        gr.dispose();
        return imgBuf;
    }


    private void solutionDegree() {
        if (currentDegree == needDegree) {
            targetShip = true;
            return;
        }
        double stepRotate = 0.4;
        if (currentDegree > needDegree) {
            if (currentDegree - needDegree >= 180) {
                currentDegree = currentDegree + stepRotate > 360 ? currentDegree + stepRotate - 360 : currentDegree + stepRotate;
                if (Math.abs(currentDegree - needDegree) < 1) {
                    currentDegree = needDegree;
                }
            } else {
                currentDegree = currentDegree - stepRotate < 0 ? 360 + (currentDegree - stepRotate) : currentDegree - stepRotate;
                if (Math.abs(currentDegree - needDegree) < 1) {
                    currentDegree = needDegree;
                    targetShip = true;
                }
            }

        } else if (currentDegree < needDegree) {
            if (needDegree - currentDegree >= 180) {
                currentDegree = currentDegree - stepRotate < 0 ? 360 + (currentDegree - stepRotate) : currentDegree - stepRotate;
                if (Math.abs(currentDegree - needDegree) < 1) {
                    currentDegree = needDegree;
                }
            } else {
                currentDegree = currentDegree + stepRotate > 360 ? currentDegree + stepRotate - 360 : currentDegree + stepRotate;
                if (Math.abs(currentDegree - needDegree) < 1) {
                    currentDegree = needDegree;
                    targetShip = true;
                }
            }
        }
    }
}
