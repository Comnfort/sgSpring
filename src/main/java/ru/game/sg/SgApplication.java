package ru.game.sg;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SgApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SgApplication.class, args);
//		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
//		HW hw=(HW) context.getBean("product");
//		hw.setStr("new Hello");
//		System.out.println(hw.getStr());
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SgApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
}
