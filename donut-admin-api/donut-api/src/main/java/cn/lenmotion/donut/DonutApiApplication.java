package cn.lenmotion.donut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lenmotion
 */
@EnableAsync
@SpringBootApplication
@MapperScan("cn.lenmotion.donut.**.mapper")
public class DonutApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonutApiApplication.class, args);
    }

}
