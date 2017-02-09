package com.gi.ctdn.cloud.storage.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by vincent on 17-1-9.
 */

@SpringBootApplication()
@ComponentScan(basePackages={"com.gi.ctdn"})
public class CloudStorageApp {


    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApp.class, args);
    }
}

