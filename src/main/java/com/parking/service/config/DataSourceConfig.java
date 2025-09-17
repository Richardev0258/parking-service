package com.parking.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    @Profile("docker")
    public DataSource dockerDataSource() {
        return DataSourceBuilder.create()
                .url(System.getenv().getOrDefault("JDBC_DATABASE_URL","jdbc:postgresql://localhost:5432/parkingdb"))
                .username(System.getenv().getOrDefault("JDBC_DATABASE_USERNAME","postgres"))
                .password(System.getenv().getOrDefault("JDBC_DATABASE_PASSWORD","postgres"))
                .build();
    }
}
