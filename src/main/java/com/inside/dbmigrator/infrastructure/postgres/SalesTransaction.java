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
@Table(name = "sales_transactions")
public class SalesTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name="updated_at")
    LocalDateTime updatedAt;
    @Column(name="deleted_at")
    LocalDateTime deletedAt;
    @Column(name = "label_id")
    Long labelId;
    LocalDate period;
    @Column(name = "audio_video_amount", columnDefinition = "float8")
    BigDecimal audioVideoAmount;
    @Column(name = "rbt_amount", columnDefinition = "float8")
    BigDecimal rbtAmount;
    @Column(name = "amount_before_tax", columnDefinition = "float8")
    BigDecimal amountBeforeTax;
    @Column(name = "tax", columnDefinition = "float8")
    BigDecimal tax;
    @Column(name = "amount_final", columnDefinition = "float8")
    BigDecimal amountFinal;
    @Deprecated(forRemoval  = true)
    String url;
    @Column(name = "payment_id")
    Long paymentId;
    @Column(name = "calculated_partner")
    Integer calculatedPartner;
    @Column(name = "uuid", columnDefinition = "char(36)")
    String uuid;
    @Column(name = "new_url")
    String newUrl;
    @Column(name = "sales_source")
    String salesSource;
    @Column(name = "sales_source_group")
    String salesSourceGroup;
}
