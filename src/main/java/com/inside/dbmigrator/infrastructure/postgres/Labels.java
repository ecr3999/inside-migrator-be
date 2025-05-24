package com.inside.dbmigrator.infrastructure.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "labels")
public class Labels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name="updated_at")
    LocalDateTime updatedAt;
    @Column(name="deleted_at")
    LocalDateTime deletedAt;
    @Column(name = "name")
    String name;
    String type;
    @Column(name = "royalty_percentage", columnDefinition = "float8")
    BigDecimal royaltyPercentage;
    @Column(name = "special_royalty_percentage")
    Integer specialRoyaltyPercentage;
    @Column(name = "special_royalty_start")
    LocalDate specialRoyaltyStart;
    @Column(name = "special_royalty_end")
    LocalDate specialRoyaltyEnd;
    @Column(name = "tax_type")
    String taxType;
    String address;
    String pic;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "fuga_external_id")
    Long fugaExternalId;
    @Column(name = "uuid", columnDefinition = "char(36)")
    String uuid;
    @Column(name = "data_status")
    String dataStatus;
    @Column(name = "label_status")
    String labelStatus;
}
