package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mapper")
@SpringBootApplication
public class TutorSystem {

    public static void main(String[] args) {
        SpringApplication.run(TutorSystem.class, args);
    }

}

