package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;


@Component
@Scope(value = "prototype")
 class Fire extends JComponent implements ActionListener {
    private int x;                             //центр героя
    private int y;                             //центр героя
    private double degree;                      //полученный угол
    private double radians;                     //полученный угол в радианах
    private Timer tm;                           //таймер
    private Point[] oneBullet;
    private int countToCreate = 0;                //количество точек
    private static ImageIcon imgBullet = new ImageIcon(ClassLoader.getSystemResource("img/bullet.png"));
    private Image image;
    private int quantity;
    private Coin con;
    private GameField gf;
    private String who;
    private SwingWorker<Void, Void> sw;


    void fireSh(GameField gf) {
        this.gf = gf;
        who = "ship";
        commonInit();
        con = gf.getCoin();
        this.x = (int) gf.getShip().getSh().getCenterX();
        this.y = (int) gf.getShip().getSh().getCenterY();
        this.degree = gf.getShip().getDegree();
        radians = Math.toRadians(degree);
        Fire f = this;

        sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                solution();
                return null;
            }

            @Override
            protected void done() {
                image = createBulletImg();

                tm = new Timer(10, f);
                tm.start();
                super.done();
            }
        };
        sw.execute();

//      CompletableFuture<Void> cf=CompletableFuture.supplyAsync(() ->
//        {
//            solution();
//            image=createBulletImg();
//            return null;
//        } ,Executors.newCachedThreadPool());
//        cf.thenRun(() -> {
//            tm=new Timer(10, this);
//            tm.start();
//        });
    }

     void fireEn(GameField gf, EnemyLvl1 enShip) {
        this.gf = gf;
        who = "enemy";
        commonInit();
        this.x = (int) enShip.getShEn().getCenterX();
        this.y = (int) enShip.getShEn().getCenterY();
        this.degree = enShip.getCurrentDegree();
        radians = Math.toRadians(degree);
        Fire f = this;

        sw = new SwingWorker<Void, Void>() {  //< возвращаемый результат, промежуточные данные(используются методом process() -добавляются и хранятся листом)>
            @Override
            protected Void doInBackground() {
                solution();
                return null;
            }

            @Override
            protected void done() {
                super.done();
                image = createBulletImg();
                tm = new Timer(25, f);
                tm.start();
            }
        };
        sw.execute();
    }


    private void commonInit() {
        quantity = 100;
        oneBullet = new Point[quantity];
        setOpaque(false);
        setSize(gf.getFieldW(), gf.getFieldH());
    }


    private Image createBulletImg() {
        BufferedImage imgBuf = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = imgBuf.createGraphics();
        if (who.equals("ship")) {
            gr.rotate(Math.toRadians(degree - 90), 8, 8);
        } else {
//            gr.rotate(radians-Math.PI/2,8,8);
            gr.rotate(Math.toRadians(degree - 90), 8, 8);
        }
        gr.drawImage(imgBullet.getImage(), 0, 0, 16, 16, null);
        gr.dispose();
        return imgBuf;
    }

    private void addNewEnemy(){
        EnemyLvl1 enemy;
        gf.getEnemyList().add(enemy =gf.getFr().getProxyInst().getEnemy());
        enemy.init(gf);
    }


    @Override
    public void actionPerformed(ActionEvent e)                //таймер для пули
    {
        checkHit();
        repaint();
        countToCreate++;
        if (countToCreate == quantity) {
            tm.stop();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (countToCreate < quantity - 1 && image != null) {
            g.setColor(Color.magenta);
            g.drawImage(image, oneBullet[countToCreate].x - 8, oneBullet[countToCreate].y - 8, null);
        } else {
            g.dispose();
        }
    }


    private void checkHit() {
        if (who.equals("ship")) {
            if (con.getBoxCoin().size() != 0 && countToCreate < quantity) {


                for (Ellipse2D ellips : con.getBoxCoin())
                {
                   if (ellips.contains(oneBullet[countToCreate]))
                   {
                       con.setCountCoin(con.getCountCoin() + 1);
                       if (con.getCountCoin() == 8) {
                           gf.setStartPointEnemy(new Point(gf.getFieldWidth() - 250, 650));
                           addNewEnemy();
                       } else if (con.getCountCoin() == 17) {
                           gf.setStartPointEnemy(new Point(gf.getFieldWidth() + 150, 650));
                           addNewEnemy();
                       } else if (con.getCountCoin() == 26) {
                           gf.setStartPointEnemy(new Point(gf.getFieldWidth() * 3 - 250, 150));
                           addNewEnemy();
                           gf.setStartPointEnemy(new Point(gf.getFieldWidth() * 2 + 150, 350));
                           addNewEnemy();
                       }
                       gf.getFr().getProxyInst().getSound().runSound("sound/монета.wav");
                       con.getBoxCoin().remove(ellips);
                       countToCreate = quantity;
                       return;
                   }
                }
            }
            if (gf.getEnemyList() != null && gf.getEnemyList().size() != 0 && countToCreate < quantity) {
                for (int j = 0; j < gf.getEnemyList().size(); j++) {
                    if (gf.getEnemyList().get(j).getShEn().contains(oneBullet[countToCreate])) {
                        gf.getEnemyList().get(j).setHealthCount(gf.getEnemyList().get(j).getHealthCount() - 1);
                        if (gf.getEnemyList().get(j).getHealthCount() == 0) {
                            gf.getEnemyList().remove(j);
                            gf.getShip().setCountDeaths(gf.getShip().getCountDeaths() + 1);
                            gf.getCoin().setNewBorder();
                        }

                        if (gf.getShip().getCountDeaths() == 4) {
                            gf.getFr().actionPerformed(new ActionEvent(gf.getFr(), 0, "победа"));
                        }

                        countToCreate = quantity;
                        return;
                    }
                }
            }
        } else if (countToCreate < quantity) {
            if (gf.getShip().getSh().contains(oneBullet[countToCreate])) {
                gf.getShip().setHealthCount(gf.getShip().getHealthCount() - 1);
                if (gf.getShip().getHealthCount() == 0) {
                    gf.getEnemyList().clear();
                    gf.getShip().resetControl();
                    gf.getFr().actionPerformed(new ActionEvent(gf.getFr(), 0, "fail"));
                }
                countToCreate = quantity;
            }
        }
    }


    private void solution() {                   //вычисление точек

        if (degree > 360) {
            degree = 360;
        }
        if (degree < 0) {
            degree = 0;
        }

        switch ((int) degree) {
            case 0:
                sol2(0, -1);
                break;
            case 180:
                sol2(0, 1);
                break;
            case 90:
                sol2(1, 0);
                break;
            case 270:
                sol2(-1, 0);
                break;
            default:
                sol2(Math.sin(radians), -(Math.cos(radians)));
                break;
        }
    }


    private void sol2(double stepX, double stepY) {                   //создание массива траектории пули
        double step = 10;                                              //длина гипотенузы при вычислении шага x1 и y1
        oneBullet[0] = new Point(x, y);
        boolean check = true;
        do {
            countToCreate++;
            if (countToCreate < 100) {
                oneBullet[countToCreate] = new Point(x + (int) (countToCreate * stepX * step), y + (int) (countToCreate * stepY * step));
            } else {
                check = false;
                countToCreate = 0;
            }
        } while (check);

    }
}
