package com.gi.ctdn.cloud.storage.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created by vincent on 17-1-9.
 */
@SpringBootApplication()
@ComponentScan(basePackages={"com.gi.ctdn"})
@MapperScan("com.gi.ctdn.cloud.storage.mapper")
public class CloudStorageApp {


    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApp.class, args);
    }
}
