package com.inside.dbmigrator.infrastructure.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RbtItemsRepository extends JpaRepository<RbtItems, Long>, JpaSpecificationExecutor<RbtItems> {
    @Query(value = "SELECT * FROM rbt_items r WHERE EXISTS (SELECT 1 FROM jsonb_each_text(r.rbt_code) AS kv WHERE kv.value = :rbtCode)", nativeQuery = true)
    Optional<RbtItems> findByRbtItemsRbtCodeValue(@Param("rbtCode") String rbtCode);
}
