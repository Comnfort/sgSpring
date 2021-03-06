package ru.game.sg.dialog;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.game.sg.spaceGame.Main;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Lazy
public final class TheEnd extends DialogSG {
    private static final Image IMG = new ImageIcon(ClassLoader.getSystemResource("img/victory.jpg")).getImage();

     TheEnd(Main fr) {
        super(fr, IMG);
    }

    @PostConstruct
    private void init() {
        int x = 1100;
        btn1.setLocation(x, 550);
        btn1.setText("Главное окно");
        btn1.setForeground(Color.lightGray);
        btn1.addActionListener(frame);

        btn2.setText("Статистика");
        btn2.setLocation(x, 630);
        btn2.setEnabled(false);

        btn3.setText("Выход");
        btn3.setLocation(x, 710);
        btn3.setForeground(Color.lightGray);
        btn3.addActionListener(frame);


        add(btn1);
        add(btn2);
        add(btn3);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font(null, Font.BOLD, 100);
        g.setColor(new Color(255, 255, 224));
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        String winMessage = "Победа!";
        int w = metrics.stringWidth(winMessage);
        g.drawString(winMessage, frame.getWidth() / 2 - w / 2, 180);
    }

}
