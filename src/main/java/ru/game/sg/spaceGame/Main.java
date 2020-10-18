package ru.game.sg.spaceGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.game.sg.dialog.Fail;
import ru.game.sg.dialog.TheEnd;
import ru.game.sg.dialog.Welcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Component
public class Main extends JFrame implements ActionListener {

    private GameField gf;
    private Welcome welcome;
    private Fail fail;

    private TheEnd theEnd;
    private Image imgDialog;

    public Image getImgDialog() {
        return imgDialog;
    }

    public void setImgDialog(Image imgDialog) {
        this.imgDialog = imgDialog;
    }

    public void setTheEnd(TheEnd theEnd) {
        this.theEnd = theEnd;
    }

    public void setFail(Fail fail) {
        this.fail = fail;
    }

    @Autowired
    private Main() {

        if(Toolkit.getDefaultToolkit().getScreenSize().getWidth()<1536){
            JOptionPane.showMessageDialog(this,"Разрешение экрана должно быть больше  ");System.exit(1);}
        else  if(Toolkit.getDefaultToolkit().getScreenSize().getWidth()==1536){setSize( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());}
        else  if(Toolkit.getDefaultToolkit().getScreenSize().getWidth()>1536){setSize(1536,864);}
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(true);
        setLayout(null);
        imgDialog=new ImageIcon(ClassLoader.getSystemResource("img/p1.jpg")).getImage();
        add(welcome=new Welcome(this));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { new Main();
            } catch (Exception e) {e.printStackTrace();}});
    }

    public void eventHandler(){
        setVisible(false);
        add(fail);
        remove(gf);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if (event == "Играть") {
            setVisible(false);
            add(gf = new GameField(this));
            remove(welcome);
            setVisible(true);
        } else if (event == "Переиграть") {
            setVisible(false);
            add(gf = new GameField(this));
            remove(fail);
            setVisible(true);
        } else if (event == "победа") {
            setVisible(false);
            add(theEnd);
            remove(gf);
            setVisible(true);
        } else if (event == "Выход") {
            System.exit(0);
        }else if (event=="Главное окно"){
            setVisible(false);
            imgDialog=new ImageIcon(ClassLoader.getSystemResource("img/p1.jpg")).getImage();
            add(welcome=new Welcome(this));
            remove(theEnd);
            setVisible(true);
        }else {
            setVisible(false);
            add(fail);
            remove(gf);
            setVisible(true);
        }


    }
}



