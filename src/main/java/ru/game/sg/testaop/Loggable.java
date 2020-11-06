package ru.game.sg.testaop;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})//помечаем классы и методы и поля
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Loggable {
}
