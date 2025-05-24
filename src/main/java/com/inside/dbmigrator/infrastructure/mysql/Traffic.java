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
@Table(name = "t_traffic")
public class Traffic {
    @Id
    Integer id;
    @Column(name="month")
    Integer month;
    @Column(name="provider_id")
    Integer providerId;
    @Column(name = "rbt_code", columnDefinition = "char(20)")
    String rbtCode;
    @Column(name = "rbt_hit")
    Integer rbtHit;
    @Column(name = "rbt_rev")
    Integer rbtRevenue;
    @Column(name = "kurs", columnDefinition = "char(3)")
    String kurs;
    @Column(name = "status", columnDefinition = "char(1)")
    String status;
}
