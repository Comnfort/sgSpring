package ru.game.sg.dialog;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Lazy
public class BtnForDialog extends JButton {
    private int width = 180;
    private int hight = 35;


    public BtnForDialog() {
        Font font = new Font(null, 1, 18);
        setFont(font);
        setFocusPainted(false);
        setSize(width, hight);
        setContentAreaFilled(false);
    }
}
