package com.sap.batch.saphanaspringbatchintegration.config;

import com.sap.batch.saphanaspringbatchintegration.repositories.DataFieldMaxValueIncrementerFactoryImpl;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Types;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomSapHanaSpringBatchConfig implements BatchConfigurer {
    @Autowired
    HikariDataSource sapHanaDatasource;

    @Bean(name = "hanaJobLauncher")
    public JobLauncher createJobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.setTaskExecutor(taskExecutor());
        simpleJobLauncher.afterPropertiesSet();
        return simpleJobLauncher;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    public JobRepository createJobRepository(PlatformTransactionManager platformTransactionManager,
                                             String tablePrefix) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDatabaseType("HDB");
        jobRepositoryFactoryBean.setDataSource(sapHanaDatasource);
        jobRepositoryFactoryBean.setIncrementerFactory(new DataFieldMaxValueIncrementerFactoryImpl(sapHanaDatasource));
        jobRepositoryFactoryBean.setTablePrefix(tablePrefix);
        jobRepositoryFactoryBean.setClobType(Types.NCLOB);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        return createJobRepository(getTransactionManager(), "DEV_BATCH_");
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return new DataSourceTransactionManager(sapHanaDatasource);
    }


    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return createJobLauncher(getJobRepository());
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(sapHanaDatasource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }
}
