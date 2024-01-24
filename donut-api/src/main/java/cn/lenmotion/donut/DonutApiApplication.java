package cn.lenmotion.donut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lenmotion
 */
@EnableAsync
@SpringBootApplication
public class DonutApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonutApiApplication.class, args);
    }

}
