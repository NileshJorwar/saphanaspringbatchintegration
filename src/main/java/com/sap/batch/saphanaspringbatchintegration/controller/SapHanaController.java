package com.sap.batch.saphanaspringbatchintegration.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/saphana/springbatch")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SapHanaController {
    final JobLauncher jobLauncher;
    final Job sapHanaJob;

    public SapHanaController(
            @Qualifier("hanaJobLauncher") JobLauncher jobLauncher,
            @Qualifier("sapHanaJob") Job sapHanaJob) {
        this.jobLauncher = jobLauncher;
        this.sapHanaJob = sapHanaJob;
    }

    @PostMapping(value = "/start-sap-hana-batch-job")
    public ResponseEntity<?> startBatchJob(@RequestBody boolean startJob) {
        try {
            jobLauncher.run(sapHanaJob, null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Running Custom SAP Hana Spring Batch Job..");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
