package ru.game.sg.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import ru.game.sg.spaceGame.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class DialogSG extends JPanel {

     private Image img;
     @Autowired
     Main frame;
     @Autowired
     BtnForDialog btn1;
     @Autowired
     BtnForDialog btn2;
     @Autowired
     BtnForDialog btn3;



    DialogSG(Main fr, Image img) {
        this.frame = fr;
        this.img = img;
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setLayout(null);
        this.img = createBackgroundImg();
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
