package com.sap.batch.saphanaspringbatchintegration;

import com.sap.batch.saphanaspringbatchintegration.loader.DbLoaderImplementation;
import com.sap.batch.saphanaspringbatchintegration.loader.IDbLoader;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = BatchAutoConfiguration.class)
@EnableBatchProcessing
@EnableBatchIntegration
public class SaphanaspringbatchintegrationApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(SaphanaspringbatchintegrationApplication.class, args);
        IDbLoader dbLoader = applicationContext.getBean(DbLoaderImplementation.class);
        dbLoader.readyDatabase();
    }

}
