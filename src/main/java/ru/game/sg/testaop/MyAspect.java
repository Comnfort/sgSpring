package ru.game.sg.testaop;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MyAspect {
    private int countFire=0;


    @Before("@annotation(Loggable)")
    public void methodPoint() {
        countFire++;
        System.out.println(chekMemory());
        System.out.println(countFire+" выстрел;");
    }

    private static String chekMemory() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory())/1048576+" Мбайт";
    }


}
