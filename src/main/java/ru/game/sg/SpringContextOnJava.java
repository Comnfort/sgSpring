package ru.game.sg;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
//@ComponentScan("ru.game.sg.testaop")
@ComponentScan("ru.game.sg")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringContextOnJava {


}
