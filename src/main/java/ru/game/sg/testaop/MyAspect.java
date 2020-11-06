package ru.game.sg.testaop;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MyAspect {
    private int countFire=0;


    @Before("@annotation(ru.game.sg.testaop.Loggable)")
    public void methodPoint() {
        countFire++;

        System.out.println(countFire+" выстрел;");
    }


}
