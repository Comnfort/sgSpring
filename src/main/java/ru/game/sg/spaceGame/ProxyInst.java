package ru.game.sg.spaceGame;


import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ru.game.sg.testaop.Loggable;

@Component
public abstract class ProxyInst {
    @Lookup
    public abstract Fire getF();

    @Lookup
    public abstract Sound getS();

    @Lookup
    public abstract GameField getGF();

    @Lookup
    public abstract Ship getSh();

    @Lookup
    public abstract Coin getC();

    @Lookup
    public abstract ChargingBar getCh();

    @Lookup
    public abstract EnemyLvl1 getEn();



    @Loggable
    public Fire getFire() {
        Fire fire = getF();
        return fire;
    }

    public Sound getSound() {
        Sound sound = getS();
        System.out.println("Создание звука");
        return sound;
    }

    public GameField getGameField() {
        GameField field = getGF();
        System.out.println("Создание игрового поля");
        return field;
    }

    public Ship getShip() {
        Ship ship = getSh();
        System.out.println("Создание корабля");
        return ship;
    }

    public Coin getCoin() {
        Coin coin = getC();
        System.out.println("Создание монет");
        return coin;
    }

    public ChargingBar getBar() {
        ChargingBar bar = getCh();
        System.out.println("Создание индикатора заряда");
        return bar;
    }

    public EnemyLvl1 getEnemy() {
        EnemyLvl1 enemy = getEn();
        System.out.println("Создание враждебного корабля");
        return enemy;
    }


}
