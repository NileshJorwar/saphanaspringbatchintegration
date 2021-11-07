package com.sap.batch.saphanaspringbatchintegration.config;

import com.sap.batch.saphanaspringbatchintegration.repositories.ISequenceGeneration;
import com.sap.batch.saphanaspringbatchintegration.repositories.SequenceGenerationImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DbConfig {

    @Bean
    @Primary
    @ConfigurationProperties("sap.hana.datasource.hikari")
    public HikariDataSource sapHanaDatasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    @Bean
    public ISequenceGeneration sequenceGeneration() {
        return new SequenceGenerationImpl(sapHanaDatasource());
    }
}
