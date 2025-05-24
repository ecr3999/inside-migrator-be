package com.inside.dbmigrator.application.internal.batch;

import com.inside.dbmigrator.infrastructure.mysql.ProviderRepository;
import com.inside.dbmigrator.infrastructure.mysql.Traffic;
import com.inside.dbmigrator.infrastructure.mysql.TrafficRepository;
import com.inside.dbmigrator.infrastructure.postgres.RbtItemsRepository;
import com.inside.dbmigrator.infrastructure.postgres.RbtSales;
import com.inside.dbmigrator.infrastructure.postgres.RbtSalesRepository;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrateTrafficExecutor {
    private final TrafficRepository trafficRepository;
    private final TrafficSpecification trafficSpecification;
    private final RbtSalesRepository rbtSalesRepository;
    private final ProviderRepository providerRepository;
    private final RbtItemsRepository rbtItemsRepository;
    private final EntityManager entityManager;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    public Job buildMigrateTrafficJob(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        Step deleteStep = deleteOldRbtSalesStep(month);
        Step migrateStep = migrateTrafficStep(startId, endId, month);
        return new JobBuilder("migrateTrafficJob", jobRepository)
                .start(deleteStep)
                .next(migrateStep)
                .build();
    }

    public Step deleteOldRbtSalesStep(@Nullable Integer month) {
        return new StepBuilder("deleteOldRbtSalesStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    if (month != null) {
                        rbtSalesRepository.deleteByPeriod(LocalDate.parse(month + "01", DateTimeFormatter.ofPattern("yyyyMMdd")));
                    } else {
                        rbtSalesRepository.deleteAll();
                        entityManager.createNativeQuery("ALTER TABLE rbt_sales ALTER COLUMN id RESTART WITH 1;").executeUpdate();
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    public Step migrateTrafficStep(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        return new StepBuilder("migrateTrafficStep", jobRepository)
                .<Traffic, RbtSales>chunk(100, transactionManager)
                .reader(trafficItemReader(startId, endId, month))
                .processor(trafficItemProcessor())
                .writer(rbtSalesItemWriter())
                .build();
    }

    public ItemReader<Traffic> trafficItemReader(Integer startId, Integer endId, Integer month) {
        return new RepositoryItemReaderBuilder<Traffic>()
                .name("trafficItemReader")
                .repository(trafficRepository)
                .methodName("findAll")
                .arguments(List.of(trafficSpecification.predicate(startId, endId, month)))
                .sorts(Map.of("id", org.springframework.data.domain.Sort.Direction.ASC))
                .build();
    }

    public ItemProcessor<Traffic, RbtSales> trafficItemProcessor() {
        return new TrafficItemProcessor(providerRepository, rbtItemsRepository);
    }

    public ItemWriter<RbtSales> rbtSalesItemWriter() {
        return rbtSalesRepository::saveAll;
    }
}