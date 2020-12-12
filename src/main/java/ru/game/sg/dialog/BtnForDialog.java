package ru.game.sg.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
 class BtnForDialog extends JButton {

    @Autowired
     BtnForDialog() {
        Font font = new Font(null, Font.BOLD, 18);
        setFont(font);
        setFocusPainted(false);
        int width = 180;
        int height = 35;
        setSize(width, height);
        setContentAreaFilled(false);
    }
}
