package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.stream.Stream;

import static ru.game.sg.spaceGame.Control.*;


@Component
@Lazy
class GameField extends JPanel implements ActionListener {
    private ImageIcon img;
    private Point startPointEnemy;
    private String pathToSound;
    private int currentWindowX=0;         //расположение панели в окне
    private int currentWindowY=0;         //расположение панели в окне
    private  int fieldWidth;            //ширина видимой части панели
    private  int fieldHeight;           //высота видимой части панели
    private  int currentPanelX=0;         //текущее положение видимой части панели
    private  int currentPanelY=0;         //текущее положение видимой части панели
    private final int fieldW;           //ширина игрового поля
    private final int fieldH;           //высота игрового поля
    private final int sizeHero;
    private int timeEnergy = 0;         //шкала прогресс бара
    private JProgressBar pb;
    private Ship ship;
    private int speedFire;              //скорость стрельбы
    private Coin con;
    private ArrayList<EnemyLvl1> enemyList;
    private Image imgCoin;
    private GameField gf=this;
    private int progress;
    private boolean [] newEnemy ={false,false,false};
    private int multiplier;
    private Main fr;

    public String getPathToSound() {
        return pathToSound;
    }

    public void setPathToSound(String pathToSound) {
        this.pathToSound = pathToSound;
    }

    public Point getStartPointEnemy() {
        return startPointEnemy;
    }

    public void setStartPointEnemy(Point startPointEnemy) {
        this.startPointEnemy = startPointEnemy;
    }

    public int getSizeHero() {
        return sizeHero;
    }

    public int getTimeEnergy() {
        return timeEnergy;
    }

    public JProgressBar getPb() {
        return pb;
    }

    public int getSpeedFire() {
        return speedFire;
    }

    public GameField getGf() {
        return gf;
    }


    public Main getFr() {
        return fr;
    }

    public boolean[] getNewEnemy() {
        return newEnemy;
    }

    public int getProgress() {
        return progress;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public Coin getCon() {
        return con;
    }

    public int getCurrentWindowX() {
        return currentWindowX;
    }

    public int getCurrentWindowY() {
        return currentWindowY;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getCurrentPanelX() {
        return currentPanelX;
    }

    public int getCurrentPanelY() {
        return currentPanelY;
    }

    public int getFieldW() {
        return fieldW;
    }

    public int getFieldH() {
        return fieldH;
    }

    public ArrayList<EnemyLvl1> getEnemyList() {
        return enemyList;
    }

    public Ship getShip() {
        return ship;
    }

    GameField(Main fr) {
        this.fr=fr;
        setLayout(null);
        setOpaque(false);
        setFocusable(true);
        img = new ImageIcon(ClassLoader.getSystemResource("img/st.jpg"));
        multiplier = 3;                                     //увеличение окна, множитель
        fieldWidth=fr.getWidth();
        fieldHeight=fr.getHeight();
        fieldW = fieldWidth * multiplier;
        fieldH = fieldHeight;
        ship = new Ship(this);
        progress=ship.getX1();
        sizeHero = ship.getSizeHero();
        setBounds(currentWindowX, currentWindowY, fieldW, fieldH);
        con = new Coin(this);
        speedFire = 20;
        pb = new ChargingBar(this);
        add(pb);
        pathToSound="sound/Вступление.wav";
        new Sound(this);
        imgCoin=new ImageIcon(ClassLoader.getSystemResource("img/imgCoin.png")).getImage();
        enemyList=new ArrayList<>();
        createListener();
        Timer timer = new Timer(30, this);
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i=e.getClickCount();
               if (i==2){System.exit(0);}
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int a =e.getX();
                int b=e.getY();
                if (ship.getSh().contains(a,b)){
                    ship.getImgHero().setImage(new ImageIcon(ClassLoader.getSystemResource("img/shipCircle.png")).getImage());
                }else {
                    ship.getImgHero().setImage(new ImageIcon(ClassLoader.getSystemResource("img/ship.png")).getImage());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int a =e.getX();
                int b=e.getY();
                if (ship.getSh().contains(a,b)){
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }else {setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
            }
        });
    }


    public void addEnemy(){

        if ( progress<currentPanelX+ ship.getX1()){ progress=currentPanelX+ ship.getX1();}
        if (!newEnemy[0] &&progress>=fieldWidth-150){
            startPointEnemy=new Point(fieldWidth-300,150);
            enemyList.add(new EnemyLvl1(this));
            newEnemy[0]=true;
        }else if (!newEnemy[1] &&progress>=(fieldWidth*2)-200){
            startPointEnemy=new Point((fieldWidth*2)-600,250);
            enemyList.add(new EnemyLvl1(this));
            newEnemy[1]=true;
        }
        else if (!newEnemy[2] &&progress>=(fieldWidth*3)-200){
            startPointEnemy=new Point((fieldWidth*3)-650,500);
            enemyList.add(new EnemyLvl1(this));
            newEnemy[2]=true;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeEnergy < speedFire) { timeEnergy++;   pb.setValue(timeEnergy); }
        ship.controlHero();
        ship.setCourse();
        con.checkCrossCoin();
//        addEnemy();

        if (enemyList!=null&enemyList.size()!=0)
        {
            for (int i = 0; i < enemyList.size(); i++) {
                enemyList.get(i).reDrawEnemy(ship.getSh().getCenterX(),ship.getSh().getCenterY());
                enemyList.get(i).fire();
            }
        }
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, fieldW, fieldH, null);                   //отрисовка фона
        g.drawImage(ship.getImgBuf(), currentPanelX + ship.getX1()-5, currentPanelY + ship.getY1()-5,80,80,null);              //отрисовка корабля
        if (enemyList!=null&enemyList.size()!=0) {
            for (int i = 0; i < enemyList.size(); i++)
            {
                g.drawImage(enemyList.get(i).getImgEnemy(), enemyList.get(i).getX1() - 5,
                        enemyList.get(i).getY1() - 5, 80, 80, null);

            }
        }
        g.setFont(new Font(null, 1, 34));
        g.setColor(Color.RED);
        g.drawString(ship.getHEALTH() + "   " + ship.getHealthCount(), currentPanelX + 320, currentPanelY +fieldHeight - 20);
        g.setColor(Color.YELLOW);
        g.drawString(con.getBadgeCoin() + "   " + con.getCountCoin(), currentPanelX + 170, currentPanelY + fieldHeight - 20);
            for (Ellipse2D shape : con.getaL()) {
                g.drawImage(imgCoin,shape.getBounds().x, shape.getBounds().y,28,28,null);
           }
        }


    public void setLoc(int x, int y,int step) {
        if (currentWindowX != x ) {
            currentWindowX = x;
            currentPanelX-=step;
        }
        if (currentWindowY != y){
            currentWindowY = y;
            currentPanelY-=step;
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
                        pathToSound="sound/выстрел.wav";
                        new Sound(gf);
                        add(new Fire(gf));
                        timeEnergy = 0;
                        pb.setValue(timeEnergy);
                    }
                }
                if (t == KeyEvent.VK_RIGHT) {
                    RIGHT.setPressed(true);
                }
                if (t == KeyEvent.VK_LEFT) {
                    LEFT.setPressed(true);
                }
                if (t == KeyEvent.VK_UP) {
                    UP.setPressed(true);
                }
                if (t == KeyEvent.VK_DOWN) {
                    DOWN.setPressed(true);
                }
                if (t == KeyEvent.VK_R) {
                    RIGHT_ROTATE.setPressed(true);
                }
                if (t == KeyEvent.VK_E) {
                    LEFT_ROTATE.setPressed(true);
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
                    UP.setPressed(false);
                }
                if (t == KeyEvent.VK_DOWN) {
                    DOWN.setPressed(false);
                }
                if (t == KeyEvent.VK_LEFT) {
                    LEFT.setPressed(false);
                }
                if (t == KeyEvent.VK_RIGHT) {
                    RIGHT.setPressed(false);
                }
                if (t == KeyEvent.VK_R) {
                    RIGHT_ROTATE.setPressed(false);
                }
                if (t == KeyEvent.VK_E) {
                    LEFT_ROTATE.setPressed(false);
                }
                if (t == KeyEvent.VK_SHIFT) {
                    ship.setStepHero(5);
                }
            }
        });
    }
}


