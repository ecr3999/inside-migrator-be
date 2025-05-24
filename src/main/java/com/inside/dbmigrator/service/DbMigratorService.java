package com.inside.dbmigrator.service;

import com.inside.dbmigrator.application.internal.batch.MigrateRevenueExecutor;
import com.inside.dbmigrator.application.internal.batch.MigrateTrafficExecutor;
import com.inside.dbmigrator.infrastructure.mysql.*;
import com.inside.dbmigrator.infrastructure.postgres.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbMigratorService {
    private final RevenueRepository revenueRepository;
    private final RevenueSpecification revenueSpecification;
    private final TrafficRepository trafficRepository;

    private final SalesTransactionRepository salesTransactionRepository;
    private final LabelsRepository labelsRepository;
    private final TrafficSpecification trafficSpecification;
    private final RbtSalesRepository rbtSalesRepository;
    private final ProviderRepository providerRepository;
    private final RbtItemsRepository rbtItemsRepository;

    private final JobLauncher jobLauncher;
    private final MigrateTrafficExecutor migrateTrafficExecutor;
    private final MigrateRevenueExecutor migrateRevenueExecutor;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void migrateRevenue(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        try {
            Job job = migrateRevenueExecutor.build(startId, endId, month);
            JobParametersBuilder builder = new JobParametersBuilder();
            if (startId != null) builder.addString("startId", startId.toString());
            if (endId != null) builder.addString("endId", endId.toString());
            if (month != null) builder.addString("month", month.toString());
            builder.addLong("timestamp", System.currentTimeMillis());
            JobParameters params = builder.toJobParameters();
            jobLauncher.run(job, params);
        } catch (Exception e) {
            log.error("Failed to launch migrateRevenueJob", e);
            throw new RuntimeException(e);
        }
    }

    public void migrateTraffic(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        try {
            Job job = migrateTrafficExecutor.buildMigrateTrafficJob(startId, endId, month);
            JobParametersBuilder builder = new JobParametersBuilder();
            if (startId != null) builder.addString("startId", startId.toString());
            if (endId != null) builder.addString("endId", endId.toString());
            if (month != null) builder.addString("month", month.toString());
            builder.addLong("timestamp", System.currentTimeMillis());
            JobParameters params = builder.toJobParameters();
            jobLauncher.run(job, params);
        } catch (Exception e) {
            log.error("Failed to launch migrateTrafficJob", e);
            throw new RuntimeException(e);
        }
    }
}

