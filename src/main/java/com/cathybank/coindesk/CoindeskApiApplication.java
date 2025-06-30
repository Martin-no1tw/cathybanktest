package com.cathybank.coindesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.cathybank.coindesk.entity")
@EnableJpaRepositories("com.cathybank.coindesk.repository")
public class CoindeskApiApplication {
        public static void main(String[] args) {
            SpringApplication.run(CoindeskApiApplication.class, args);
        }
}
