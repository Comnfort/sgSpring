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
    private Fire f;
    private Image imgEnemy;
    private int x1, y1, stepEnemy, healthCount = 1;
    private double currentDegree;
    private double needDegree;
    private Ellipse2D shEn;
    private GameField gf;
    private int sizeHero;
    private double x, y;
    private int timeFire = 1;
    private boolean distance;
    private double stepRotate = 0.4;
    private boolean energyFire = false;
    private boolean targetShip = false;

    public double getCurrentDegree() {
        return currentDegree;
    }

    public int getHealthCount() {
        return healthCount;
    }

    public void setHealthCount(int healthCount) {
        this.healthCount = healthCount;
    }

    public Image getImgEnemy() {
        return imgEnemy;
    }

    public Ellipse2D getShEn() {
        return shEn;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }


    public void  init(GameField gf) {
        x1 = gf.getStartPointEnemy().x;
        y1 = gf.getStartPointEnemy().y;
        imgEnemy = new ImageIcon(ClassLoader.getSystemResource("img/shipEn.png")).getImage();
        this.gf = gf;
        sizeHero = gf.getShip().getSizeHero();
        stepEnemy = gf.getShip().getStepHero();
        shEn = new Ellipse2D.Double(x1, y1, sizeHero, sizeHero);
        currentDegree = 270;
        imgEnemy = createEnemyImg();
        x = shEn.getCenterX();
        y = shEn.getCenterY();
    }


    public void reDrawEnemy(double x, double y) {                //задаю направление
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


    public void fire() {
        ++timeFire;
        if (timeFire % 80 == 0) {
            energyFire = true;
        } else {
            energyFire = false;
        }
        if (energyFire && !distance && targetShip) {
            gf.getFr().getProxyInst().getSound().runSound("sound/выстрел.wav");
            f = gf.getFr().getProxyInst().getFire();
            gf.add(f);
            f.fireEn(gf, this);
            energyFire = false;
            targetShip = false;
        }
    }


    public Image createEnemyImg() {
        imgEnemy = new ImageIcon(ClassLoader.getSystemResource("img/shipEn.png")).getImage();
        BufferedImage imgBuf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = imgBuf.createGraphics();
        gr.rotate(Math.toRadians(currentDegree), 50, 50);
        gr.drawImage(imgEnemy, 10, 5, 80, 90, null);
        gr.dispose();
        return imgBuf;
    }


    private void solutionDegree() {
        if (currentDegree == needDegree) {
            targetShip = true;
            return;
        }
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
