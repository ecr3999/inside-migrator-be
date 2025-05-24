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
@Table(name = "m_provider")
public class Provider {
    @Id
    Integer id;
    @Column(name="provider_name", columnDefinition = "char(25)")
    String providerName;
    @Column(name="type", columnDefinition = "char(1)")
    Integer type;
}
