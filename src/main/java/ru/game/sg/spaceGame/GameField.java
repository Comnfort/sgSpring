package ru.game.sg.spaceGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import static ru.game.sg.spaceGame.Control.*;


@Component
@Scope(value = "prototype")
public class GameField extends JPanel implements ActionListener {

    private static ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("img/st.jpg"));
    private Point startPointEnemy;
    private int currentWindowX = 0;         //расположение панели в окне
    private int currentWindowY = 0;         //расположение панели в окне
    private int fieldWidth;            //ширина видимой части панели
    private int fieldHeight;           //высота видимой части панели
    private int currentPanelX = 0;         //текущее положение видимой части панели
    private int currentPanelY = 0;         //текущее положение видимой части панели
    private final int fieldW;           //ширина игрового поля
    private final int fieldH;           //высота игрового поля
    private int timeEnergy = 0;         //шкала прогресс бара
    private JProgressBar pb;
    private Ship ship;
    private int speedFire;              //скорость стрельбы
    private Coin coin;
    private ArrayList<EnemyLvl1> enemyList;
    private static Image imgCoin = new ImageIcon(ClassLoader.getSystemResource("img/imgCoin.png")).getImage();
    private GameField gf = this;
    private Main fr;




     Point getStartPointEnemy() {
        return startPointEnemy;
    }

     void setStartPointEnemy(Point startPointEnemy) {
        this.startPointEnemy = startPointEnemy;
    }

     int getTimeEnergy() {
        return timeEnergy;
    }

     int getSpeedFire() {
        return speedFire;
    }

     Main getFr() {
        return fr;
    }

     Coin getCoin() {
        return coin;
    }

     int getCurrentWindowX() {
        return currentWindowX;
    }

     int getCurrentWindowY() {
        return currentWindowY;
    }

     int getFieldWidth() {
        return fieldWidth;
    }

     int getFieldHeight() {
        return fieldHeight;
    }

     int getCurrentPanelX() {
        return currentPanelX;
    }

     int getCurrentPanelY() {
        return currentPanelY;
    }

     int getFieldW() {
        return fieldW;
    }

     int getFieldH() {
        return fieldH;
    }

     ArrayList<EnemyLvl1> getEnemyList() {
        return enemyList;
    }

     Ship getShip() {
        return ship;
    }



    @Autowired
    GameField(Main fr) {
        this.fr = fr;
        setLayout(null);
        setOpaque(false);
        setFocusable(true);
        int multiplier = 3;                                     //увеличение окна, множитель
        fieldWidth = fr.getWidth();
        fieldHeight = fr.getHeight();
        fieldW = fieldWidth * multiplier;
        fieldH = fieldHeight;
    }



    public void init() {
        ship = getFr().getProxyInst().getShip();
        ship.init(this);
        setBounds(currentWindowX, currentWindowY, fieldW, fieldH);
        coin = getFr().getProxyInst().getCoin();
        coin.init(this);
        speedFire = 20;
        pb=getFr().getProxyInst().getBar();
        ((ChargingBar) pb).init(this);
        add(pb);
        fr.getProxyInst().getSound().runSound("sound/Вступление.wav");
        enemyList = new ArrayList<>();
        createListener();
        Timer timer = new Timer(30, this);
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = e.getClickCount();
                if (i == 2) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int a = e.getX();
                int b = e.getY();
                if (ship.getSh().contains(a, b)) {
                    ship.getImgHero().setImage(new ImageIcon(ClassLoader.getSystemResource("img/shipCircle.png")).getImage());
                } else {
                    ship.getImgHero().setImage(new ImageIcon(ClassLoader.getSystemResource("img/ship.png")).getImage());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int a = e.getX();
                int b = e.getY();
                if (ship.getSh().contains(a, b)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                if (timeEnergy < speedFire) {
                    timeEnergy++;
                    pb.setValue(timeEnergy);
                }
                ship.controlHero();
                ship.setCourse();


                return null;
            }

            @Override
            protected void done() {
                super.done();
                coin.checkCrossCoin();
                if (enemyList != null && enemyList.size() != 0) {
                    for (EnemyLvl1 enemyLvl1 : enemyList) {
                        enemyLvl1.reDrawEnemy(ship.getSh().getCenterX(), ship.getSh().getCenterY());
                        enemyLvl1.fire();
                    }
                }
                repaint();
            }
        };
        sw.execute();


    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, fieldW, fieldH, null);                   //отрисовка фона
        g.drawImage(ship.getImgBuf(), currentPanelX + ship.getX1() - 5, currentPanelY + ship.getY1() - 5, 80, 80, null);              //отрисовка корабля
        if (enemyList != null && enemyList.size() != 0) {
            for (EnemyLvl1 enemyLvl1 : enemyList) {
                g.drawImage(enemyLvl1.getImgEnemy(), enemyLvl1.getX1() - 5,
                        enemyLvl1.getY1() - 5, 80, 80, null);

            }
        }
        g.setFont(new Font(null, Font.BOLD, 34));
        g.setColor(Color.RED);
        g.drawString(ship.getHEALTH() + "   " + ship.getHealthCount(), currentPanelX + 320, currentPanelY + fieldHeight - 20);
        g.setColor(Color.YELLOW);
        g.drawString(coin.getBadgeCoin() + "   " + coin.getCountCoin(), currentPanelX + 170, currentPanelY + fieldHeight - 20);
        for (Ellipse2D shape : coin.getBoxCoin()) {
            g.drawImage(imgCoin, shape.getBounds().x, shape.getBounds().y, 28, 28, null);
        }
    }


     void setLoc(int x, int y, int step) {
        if (currentWindowX != x) {
            currentWindowX = x;
            currentPanelX -= step;
        }
        if (currentWindowY != y) {
            currentWindowY = y;
            currentPanelY -= step;
        }
        setLocation(x, y);
        pb.setLocation(currentPanelX + 20, currentPanelY + fieldHeight - 45);
    }


    private void createListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int t = e.getKeyCode();

                if (t == KeyEvent.VK_SPACE) {
                    if (timeEnergy == speedFire) {
                        fr.getProxyInst().getSound().runSound("sound/выстрел.wav");
                        Fire f = fr.getProxyInst().getFire();
                        add(f);
                        f.fireSh(gf);
                        timeEnergy = 0;
                        pb.setValue(timeEnergy);
                    }
                }
                if (t == KeyEvent.VK_RIGHT) {
                    RIGHT.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_LEFT) {
                    LEFT.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_UP) {
                    UP.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_DOWN) {
                    DOWN.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_R) {
                    RIGHT_ROTATE.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_E) {
                    LEFT_ROTATE.setPressed(Boolean.TRUE);
                }
                if (t == KeyEvent.VK_SHIFT) {
                    ship.setStepHero(35);
                }
                if (t == KeyEvent.VK_P) {
                    img = new ImageIcon(ClassLoader.getSystemResource("img/st.jpg"));
                }
                if (t == KeyEvent.VK_O) {
                    img = new ImageIcon(ClassLoader.getSystemResource("img/p1.jpg"));
                }
                if (t == KeyEvent.VK_I) {
                    img = new ImageIcon(ClassLoader.getSystemResource("img/fail.jpg"));
                }
                if (t == KeyEvent.VK_U) {
                    img = new ImageIcon(ClassLoader.getSystemResource("img/victory.jpg"));
                }
            }


            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int t = e.getKeyCode();
                if (t == KeyEvent.VK_UP) {
                    UP.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_DOWN) {
                    DOWN.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_LEFT) {
                    LEFT.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_RIGHT) {
                    RIGHT.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_R) {
                    RIGHT_ROTATE.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_E) {
                    LEFT_ROTATE.setPressed(Boolean.FALSE);
                }
                if (t == KeyEvent.VK_SHIFT) {
                    ship.setStepHero(5);
                }
            }
        });
    }
}


