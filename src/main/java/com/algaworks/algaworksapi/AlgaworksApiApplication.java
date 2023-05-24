package com.algaworks.algaworksapi;

import com.algaworks.algaworksapi.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgaworksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgaworksApiApplication.class, args);
    }

}
