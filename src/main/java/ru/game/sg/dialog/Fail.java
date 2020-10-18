package ru.game.sg.dialog;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.game.sg.spaceGame.Main;

import java.awt.*;

@Component
@Lazy
public class Fail extends AbstractDialog
{
    String failMessage;

    public Fail(Main fr) {
        super(fr, fr.getImgDialog());
        init();
        failMessage="Поражение";
    }

    private void init()
    {
        int x=150;
        btn2.setText("Переиграть");
        btn2.setLocation(x,600);
        btn2.setForeground(Color.lightGray);
        btn2.addActionListener(frame);

        btn3.setText("Выход");
        btn3.setLocation(x,680);
        btn3.setForeground(Color.lightGray);
        btn3.addActionListener(frame);


        add(btn2);
        add(btn3);
        frame.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font=new Font(null,1,75);
        g.setColor(new Color(139,0,0));
        g.setFont(font);
        FontMetrics metrics=g.getFontMetrics(font);
        int w=metrics.stringWidth(failMessage);
        g.drawString(failMessage,frame.getWidth()/2-w/2,150);
    }
}