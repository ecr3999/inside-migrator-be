package com.inside.dbmigrator.application.internal.batch;

import com.inside.dbmigrator.infrastructure.mysql.*;
import com.inside.dbmigrator.infrastructure.postgres.*;
import com.inside.dbmigrator.service.RevenueSpecification;
import com.inside.dbmigrator.service.TrafficSpecification;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrateRevenueExecutor {
    private final RevenueItemProcessor revenueItemProcessor;
    private final RevenueSpecification revenueSpecification;
    private final RevenueRepository revenueRepository;
    private final LabelsRepository labelsRepository;
    private final SalesTransactionRepository salesTransactionRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    public Job build(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        Step migrateStep = migrateRevenueStep(startId, endId, month);
        return new JobBuilder("migrateRevenueJob", jobRepository)
                .start(migrateStep)
                .build();
    }

    public Step migrateRevenueStep(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        return new StepBuilder("migrateRevenueStep", jobRepository)
                .<Revenue, SalesTransaction>chunk(100, transactionManager)
                .reader(revenueItemReader(startId, endId, month))
                .processor(revenueItemProcessor())
                .writer(salesTransactionItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    public ItemReader<Revenue> revenueItemReader(Integer startId, Integer endId, Integer month) {
        return new RepositoryItemReaderBuilder<Revenue>()
                .name("revenueItemReader")
                .repository(revenueRepository)
                .methodName("findAll")
                .arguments(List.of(revenueSpecification.predicate(startId, endId, month)))
                .sorts(Map.of("id", org.springframework.data.domain.Sort.Direction.ASC))
                .build();
    }

    public ItemProcessor<Revenue, SalesTransaction> revenueItemProcessor() {
        return new RevenueItemProcessor(labelsRepository, salesTransactionRepository);
    }


    public TaskExecutor taskExecutor(){
        var taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }


    public ItemWriter<SalesTransaction> salesTransactionItemWriter() {
        return salesTransactionRepository::saveAll;
    }
}

