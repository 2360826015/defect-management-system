package com.liuwohe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liuwohe.repository")
public class DefectManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DefectManagementSystemApplication.class, args);
    }

}
