package ru.game.sg.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.game.sg.spaceGame.Main;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;


@Component("welcome")
@Lazy
public final class Welcome extends DialogSG {

    private static final Image IMG = new ImageIcon(ClassLoader.getSystemResource("img/p1.jpg")).getImage();


     Welcome(Main fr) {
        super(fr, IMG);
    }


    @PostConstruct
    private void init() {
        int x = 350;
        btn1.setLocation(x, 250);
        btn1.setText("Играть");
        btn1.setForeground(Color.lightGray);
        btn1.addActionListener(frame);

        btn2.setText("Настройки");
        btn2.setLocation(x, 330);
        btn2.setEnabled(false);

        btn3.setText("Выход");
        btn3.setLocation(x, 410);
        btn3.setForeground(Color.lightGray);
        btn3.addActionListener(frame);


        add(btn1);
        add(btn2);
        add(btn3);
    }

}
