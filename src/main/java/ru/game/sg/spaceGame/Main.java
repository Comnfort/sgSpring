package ru.game.sg.spaceGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.game.sg.dialog.DialogSG;
import ru.game.sg.dialog.Fail;
import ru.game.sg.dialog.TheEnd;
import ru.game.sg.dialog.Welcome;
import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Component
public class Main extends JFrame implements ActionListener {

    private GameField gf;
    private DialogSG welcome;
    private DialogSG fail;
    private DialogSG theEnd;
    private ProxyInst proxyInst;


    @Autowired
    public void setWelcome(@Qualifier("welcome") Welcome welcome) {
        this.welcome = welcome;
    }

    @Autowired
    public void setTheEnd(TheEnd theEnd) {
        this.theEnd = theEnd;
    }

    @Autowired
    public void setFail(Fail fail) {
        this.fail = fail;
    }

    @Autowired
    public void setProxyInst(ProxyInst proxyInst) {
        this.proxyInst = proxyInst;
    }


     ProxyInst getProxyInst() {
        return proxyInst;
    }

    public Main() {
        SwingUtilities.invokeLater(() -> {
            if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 1536) {
                JOptionPane.showMessageDialog(this, "Разрешение экрана должно быть больше  ");
                System.exit(1);
            } else if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() == 1536) {
                setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                        (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
            } else if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() > 1536) {
                setSize(1536, 864);
            }
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setUndecorated(true);
            setResizable(true);
            setLayout(null);
        });
    }

    @PostConstruct
    private void init() {
        add(welcome);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        switch (event) {
            case "Играть":
                setVisible(false);
                gf = proxyInst.getGameField();
                add(gf);
                gf.init();
                remove(welcome);
                setVisible(true);
                break;
            case "Переиграть":
                setVisible(false);
                gf = proxyInst.getGameField();
                add(gf);
                gf.init();
                remove(fail);
                setVisible(true);
                break;
            case "победа":
                setVisible(false);
                add(theEnd);
                remove(gf);
                setVisible(true);
                break;
            case "Выход":
                System.exit(0);
            case "Главное окно":
                setVisible(false);
                add(welcome);
                remove(theEnd);
                setVisible(true);
                break;
            default:
                setVisible(false);
                add(fail);
                remove(gf);
                setVisible(true);
                break;
        }


    }
}



