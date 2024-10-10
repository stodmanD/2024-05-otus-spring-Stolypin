package com.example.hw13spring_batch.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Properties;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private static final String JOB_NAME = "migrateBooksJob";

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    //http://localhost:8080/h2-console/

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {

        Long executionId = jobOperator.start(JOB_NAME, new Properties());
        System.out.println(jobOperator.getSummary(executionId));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(JOB_NAME));
    }
}
