package com.inside.dbmigrator.infrastructure.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public interface RbtSalesRepository extends JpaRepository<RbtSales, Long>, JpaSpecificationExecutor<RbtSales> {
    Optional<RbtSales> findByPeriodAndProviderNameAndRbtCode(LocalDate period, String providerName, String rbtCode);
    void deleteByPeriod(LocalDate period);
}
