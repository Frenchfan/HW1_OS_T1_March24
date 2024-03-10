package com.sumkin.hw1_openschool.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()

                .info(new Info()
                        .title("Домашнее задание 1 - открытая школа T1")
                        .description("В/д 2-х микросервисов")
                        .version("1.0")


                );
    }
}
