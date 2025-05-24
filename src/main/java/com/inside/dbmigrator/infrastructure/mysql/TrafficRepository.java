package com.inside.dbmigrator.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Integer>, JpaSpecificationExecutor<Traffic> {
    // Custom query methods can be defined here if needed
     void deleteAllByMonth(int month);

}
