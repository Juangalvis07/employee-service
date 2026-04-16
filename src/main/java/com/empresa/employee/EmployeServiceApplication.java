package com.empresa.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.empresa.employee")
@EntityScan(basePackages = "com.empresa.employee.model")
@EnableJpaRepositories(basePackages = "com.empresa.employee.repository")
public class EmployeServiceApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "oracle");
        SpringApplication.run(EmployeServiceApplication.class, args);
    }
}