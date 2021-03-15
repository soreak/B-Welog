package com.soreak;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.soreak.dao")
public class WelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WelogApplication.class, args);
    }

}
