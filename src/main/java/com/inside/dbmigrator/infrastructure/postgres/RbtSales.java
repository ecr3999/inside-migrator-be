package com.inside.dbmigrator.infrastructure.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rbt_sales")
public class RbtSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name="period")
    LocalDate period;
    @Column(name="provider_name")
    String providerName;
    @Column(name = "rbt_code")
    String rbtCode;
    @Column(name = "rbt_hit")
    Integer rbtHit;
    @Column(name = "rbt_rev")
    Integer rbtRevenue;
    @Column(name = "status")
    String status;
}
