package ru.game.sg.spaceGame;


import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.game.sg.dialog.Fail;
import ru.game.sg.testaop.Loggable;

@Component
public abstract class ProxyInst {
    @Lookup
     abstract Fire getF();

    @Lookup
     abstract Sound getS();

    @Lookup
     abstract GameField getGF();

    @Lookup
     abstract Ship getSh();

    @Lookup
     abstract Coin getC();

    @Lookup
     abstract ChargingBar getCh();

    @Lookup
     abstract EnemyLvl1 getEn();



    @Loggable
     Fire getFire() {
        return getF();
    }

     Sound getSound() {
        Sound sound = getS();
        System.out.println("Создание звука");
        return sound;
    }

     GameField getGameField() {
        GameField field = getGF();
        System.out.println("Создание игрового поля");
        return field;
    }

     Ship getShip() {
        Ship ship = getSh();
        System.out.println("Создание корабля");
        return ship;
    }

     Coin getCoin() {
        Coin coin = getC();
        System.out.println("Создание монет");
        return coin;
    }

     ChargingBar getBar() {
        ChargingBar bar = getCh();
        System.out.println("Создание индикатора заряда");
        return bar;
    }

     EnemyLvl1 getEnemy() {
        EnemyLvl1 enemy = getEn();
        System.out.println("Создание враждебного корабля");
        return enemy;
    }

}
