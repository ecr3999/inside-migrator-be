package com.inside.dbmigrator.application.internal.batch;

import com.inside.dbmigrator.infrastructure.mysql.Revenue;
import com.inside.dbmigrator.infrastructure.postgres.Labels;
import com.inside.dbmigrator.infrastructure.postgres.LabelsRepository;
import com.inside.dbmigrator.infrastructure.postgres.SalesTransaction;
import com.inside.dbmigrator.infrastructure.postgres.SalesTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RevenueItemProcessor implements ItemProcessor<Revenue, SalesTransaction> {
    private final LabelsRepository labelsRepository;
    private final SalesTransactionRepository salesTransactionRepository;

    @Override
    public SalesTransaction process(final Revenue revenue) {
        Long labelId = Long.valueOf(revenue.getPartnerId());
        LocalDate period = LocalDate.parse(revenue.getMonth() + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        Optional<SalesTransaction> salesTransactionOptional = salesTransactionRepository.findByLabelIdAndPeriod(labelId, period);
        SalesTransaction salesTransaction = salesTransactionOptional.orElseGet(SalesTransaction::new);
        salesTransaction.setCreatedAt(LocalDateTime.now());
        salesTransaction.setUpdatedAt(LocalDateTime.now());
        salesTransaction.setDeletedAt(null);
        salesTransaction.setLabelId(labelId);
        salesTransaction.setPeriod(period);
        salesTransaction.setAudioVideoAmount(BigDecimal.valueOf(0.0));
        salesTransaction.setRbtAmount(BigDecimal.valueOf(revenue.getPartnerRevenue()));
        salesTransaction.setAmountBeforeTax(salesTransaction.getRbtAmount());

        // Find the label by id and get taxType
        Labels label = labelsRepository.findById(labelId).orElse(null);
        String taxType = label != null ? label.getTaxType() : null;
        if ("FOUR_PERCENT".equals(taxType)) {
            salesTransaction.setTax(salesTransaction.getRbtAmount().multiply(BigDecimal.valueOf(0.04)));
        } else if ("TWO_PERCENT".equals(taxType)) {
            salesTransaction.setTax(salesTransaction.getRbtAmount().multiply(BigDecimal.valueOf(0.02)));
        } else {
            salesTransaction.setTax(BigDecimal.valueOf(0));
        }

        salesTransaction.setAmountFinal(salesTransaction.getAmountBeforeTax().subtract(salesTransaction.getTax()));
        salesTransaction.setPaymentId(null);
        salesTransaction.setCalculatedPartner(null);
        salesTransaction.setUuid(String.valueOf(UUID.randomUUID()));
        // salesTransaction.setNewUrl(null); //To be set later

        return salesTransaction;
    }
}
