package com.ponomarenko.amazonsdk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("local")
public class LocalDatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.local-datasource")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }
}
