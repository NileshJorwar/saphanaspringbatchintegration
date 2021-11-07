package com.sap.batch.saphanaspringbatchintegration.config;

import com.sap.batch.saphanaspringbatchintegration.tasklet.AddTasklet;
import com.sap.batch.saphanaspringbatchintegration.tasklet.SubTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BatchJobConfig {
    final StepBuilderFactory stepBuilderFactory;
    final JobBuilderFactory jobBuilderFactory;

    public BatchJobConfig(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean("sapHanaJob")
    @Primary
    public Job sapHanaJob(TaskletStep addTasklet,
                          TaskletStep subTasklet){
        SimpleJobBuilder simpleJobBuilder=
                this.jobBuilderFactory.get("sap hana spring batch custom job")
                        .incrementer(new RunIdIncrementer())
                        .start(addTasklet)
                        .next(subTasklet);
        return simpleJobBuilder.build();
    }

    @Bean
    public TaskletStep addTasklet(AddTasklet addTasklet){
        return this.stepBuilderFactory.get("addTasklet")
                .tasklet(addTasklet)
                .build();
    }

    @Bean
    public TaskletStep subTasklet(SubTasklet subTasklet){
        return this.stepBuilderFactory.get("subTasklet")
                .tasklet(subTasklet)
                .build();
    }
}
