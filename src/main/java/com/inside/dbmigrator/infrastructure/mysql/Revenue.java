package com.inside.dbmigrator.infrastructure.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_revenue")
public class Revenue {
    @Id
    Integer id;
    Integer month;
    @Column(name="provider_id")
    Integer providerId;
    @Column(name = "partner_id")
    Integer partnerId;
    @Column(name = "partner_rev")
    Integer partnerRevenue;
    @Column(name = "status", columnDefinition = "char(1)")
    String status;
}
