package com.inside.dbmigrator.infrastructure.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelsRepository extends JpaRepository<Labels, Long>, JpaSpecificationExecutor<Labels> {
}
