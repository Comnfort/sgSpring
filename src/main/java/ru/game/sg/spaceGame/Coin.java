package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

@Component
@Scope("prototype")
class Coin {
    private ArrayList<Ellipse2D> aL;
    private GameField gf;
    private final String badgeCoin = "\u2622";
    private volatile int countCoin;
    private int countBorder;
    private EnemyLvl1 enemy;

    public int getCountCoin() {
        return countCoin;
    }

    public void setCountCoin(int countCoin) {
        this.countCoin = countCoin;
        setNewBorder();
    }

    public String getBadgeCoin() {
        return badgeCoin;
    }

    public ArrayList<Ellipse2D> getaL() {
        return aL;
    }


    public void init(GameField gf) {
        aL = new ArrayList<>();
        this.gf = gf;
        countCoin = 0;
        countBorder = 0;
        createCoin();
    }


    public void setNewBorder() {
        if (countBorder < 1 && gf.getShip().getCountDeaths() == 1) {
            gf.getShip().setCurrentBord(gf.getShip().getCurrentBord() - gf.getFieldWidth());
            countBorder += 1;
            createCoin();
        }
        if (countBorder < 2 && gf.getShip().getCountDeaths() == 2) {
            gf.getShip().setCurrentBord(gf.getShip().getCurrentBord() - gf.getFieldWidth());
            countBorder += 1;
            createCoin();
        }
    }

    private void addNewEnemy(){
        gf.getEnemyList().add(enemy=gf.getFr().getProxyInst().getEnemy());
        enemy.init(gf);
    }


    public void checkCrossCoin() {
        if (aL.size() != 0) {
            for (int i = 0; i < aL.size(); i++) {
                if (gf.getShip().getSh().contains(aL.get(i).getCenterX(), aL.get(i).getCenterY())) {
                    gf.getFr().getProxyInst().getSound().runSound("sound/монета.wav");
                    countCoin += 1;
                    aL.remove(i);
                    if (countCoin == 8) {
                        gf.setStartPointEnemy(new Point(gf.getFieldWidth() - 250, 650));
                        addNewEnemy();
                    } else if (countCoin == 17) {
                        gf.setStartPointEnemy(new Point(gf.getFieldWidth() + 150, 650));
                        addNewEnemy();
                    } else if (countCoin == 26) {
                        gf.setStartPointEnemy(new Point(gf.getFieldWidth() * 3 - 250, 150));
                        addNewEnemy();
                        gf.setStartPointEnemy(new Point(gf.getFieldWidth() * 2 + 150, 350));
                        addNewEnemy();
                    }
                    return;
                }
            }
        }
    }


    private void createCoin() {
        if (gf.getShip().getCountDeaths() == 0) {

            for (int i = 1; i < 4; i++) {
                int r = (int) (Math.random() * 100 - 50);


                for (int j = 1; j < 4; j++) {
                    aL.add(0, new Ellipse2D.Double(80 * j + (380 * (i - 1)),
                            40 + (20 * i) + r, 28, 28));
                }
            }

        } else if (gf.getShip().getCountDeaths() == 1) {

            for (int i = 1; i < 4; i++) {
                int r = (int) (Math.random() * 100 - 50);

                for (int j = 1; j < 4; j++) {
                    aL.add(0, new Ellipse2D.Double(gf.getFieldWidth() * 2 - (80 * j + (380 * (i - 1))),
                            gf.getFieldHeight() - 150 - (40 + (20 * i) + r), 28, 28));
                }
            }

        } else if (gf.getShip().getCountDeaths() == 2) {

            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    aL.add(0, new Ellipse2D.Double((gf.getFieldWidth() * 3 - 250)
                            + (50 * i), 450 + (50 * j), 28, 28));
                }
            }

        }
    }
}
