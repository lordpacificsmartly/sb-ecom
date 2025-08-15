package com.ecommerce.project;

import com.ecommerce.project.config.AppConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbEcomApplication {

    public static void main(String[] args) {

        SpringApplication.run(SbEcomApplication.class, args);

    }

    @PostConstruct
    public void printDefaultImage() {
        System.out.println("🖼️ Default product image: " + AppConstants.PRODUCT_IMAGE);
    }

}
