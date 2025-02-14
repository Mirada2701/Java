package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Дозволяє CORS для всіх маршрутів
                        .allowedOrigins("http://localhost:5173") // Дозволений фронтенд
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Дозволені методи
                        .allowedHeaders("*") // Дозволені заголовки
                        .allowCredentials(true); // Дозволяє передавати куки
            }
        };
    }
}
