package com.example.useCenterManageback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan("com.example.useCenterManageback.mapper")
public class UseCenterManageBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseCenterManageBackApplication.class, args);
    }

}
