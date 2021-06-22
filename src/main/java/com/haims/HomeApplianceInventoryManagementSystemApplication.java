package com.haims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.haims.dao")
@SpringBootApplication
public class HomeApplianceInventoryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeApplianceInventoryManagementSystemApplication.class, args);
    }

}
