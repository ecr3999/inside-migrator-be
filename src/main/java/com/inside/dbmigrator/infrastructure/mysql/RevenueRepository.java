package com.inside.dbmigrator.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer>, JpaSpecificationExecutor<Revenue> {
    // Custom query methods can be defined here if needed

}
