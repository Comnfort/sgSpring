package ru.game.sg;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SgApplication {

    public static void main(String[] args) {
        //контекст на основе аннотаций(каталог для сканирования или класс конфигурации)
//        AnnotationConfigApplicationContext applicationContext =new AnnotationConfigApplicationContext(SpringContextOnJava.class);

//		SpringApplication.run(SpringContextOnJava.class, args);


        //контекст на основе xml
//		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("configSG.xml");
//		HW hw=(HW) context.getBean("product");
//		hw.setStr("new Hello");
//		System.out.println(hw.getStr());


        //контекст для оконного приложения
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringContextOnJava.class);
        builder.headless(false);
        ConfigurableApplicationContext context2 = builder.run(args);

    }
}
