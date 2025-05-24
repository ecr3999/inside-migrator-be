package com.inside.dbmigrator.infrastructure.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rbt_items")
public class RbtItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
    @Column(name = "label_id")
    Long label_id;
    String title;
    String artist;
    @Column(name = "album_name")
    String albumName;
    String keyword;
    @Column(name = "rbt_code", columnDefinition = "jsonb")
    String rbtCode;
    @Column(name = "payment_shares", columnDefinition = "jsonb")
    String paymentShares;
    @Column(name = "uuid", columnDefinition = "char(36)")
    String uuid;
}