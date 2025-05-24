package com.inside.dbmigrator.infrastructure.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SalesTransactionRepository extends JpaRepository<SalesTransaction, Long>, JpaSpecificationExecutor<SalesTransaction> {
    Optional<SalesTransaction> findByLabelIdAndPeriod(Long labelId, LocalDate period);
}
