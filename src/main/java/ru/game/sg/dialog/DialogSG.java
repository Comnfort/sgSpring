package ru.game.sg.dialog;

import ru.game.sg.spaceGame.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class DialogSG extends JPanel {

     private Image img;
     Main frame;
     BtnForDialog btn1;
     BtnForDialog btn2;
     BtnForDialog btn3;


    public DialogSG(Main fr, Image img) {
        this.frame = fr;
        this.img = img;
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setLayout(null);
        this.img = createBackgroundImg();
        btn1 = new BtnForDialog();
        btn2 = new BtnForDialog();
        btn3 = new BtnForDialog();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null)
            g.drawImage(img, 0, 0, null);
    }

    private Image createBackgroundImg() {
        BufferedImage imgBuf = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = imgBuf.createGraphics();
        gr.drawImage(img, 0, 0, imgBuf.getWidth(), imgBuf.getHeight(), null);
        gr.dispose();
        return imgBuf;
    }

}
