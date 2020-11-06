package ru.game.sg.spaceGame;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
class ChargingBar extends JProgressBar {


    public void  init(GameField gf) {
        setOrientation(gf.getTimeEnergy());
        setMinimum(0);
        setMaximum(gf.getSpeedFire());
        setLayout(new BorderLayout(1, 1));
        JLabel progressLabel = new JLabel("<html>Energy &#9889");
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setVerticalAlignment(SwingConstants.CENTER);
        progressLabel.setFont(new Font("serif", Font.BOLD, 12));
        setForeground(Color.GREEN);
        setBounds(gf.getCurrentPanelX() + 20, gf.getCurrentPanelY() + gf.getFieldHeight() - 45, 100, 25);
        setBackground(Color.black);
        setOpaque(false);
        add(progressLabel, SwingConstants.CENTER);
    }
}
