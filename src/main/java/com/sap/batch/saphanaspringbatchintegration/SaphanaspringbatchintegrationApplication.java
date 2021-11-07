package com.sap.batch.saphanaspringbatchintegration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@SpringBootApplication(exclude = BatchAutoConfiguration.class)
@EnableBatchProcessing
@EnableBatchIntegration
public class SaphanaspringbatchintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaphanaspringbatchintegrationApplication.class, args);
	}

}
