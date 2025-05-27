package com.inside.dbmigrator.application.internal.batch;

import com.inside.dbmigrator.infrastructure.mysql.Provider;
import com.inside.dbmigrator.infrastructure.mysql.Traffic;
import com.inside.dbmigrator.infrastructure.postgres.RbtSales;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RequiredArgsConstructor
public class TrafficItemProcessor implements ItemProcessor<Traffic, RbtSales> {
    private static final Logger log = LoggerFactory.getLogger(TrafficItemProcessor.class);
    private final Map<Integer, Provider> providerMap;
    private final Map<String, String> codeToOperatorMap;


    @Override
    public RbtSales process(final Traffic traffic) {
        Provider provider = providerMap.get(traffic.getProviderId());
        if (provider == null) return null;

        RbtSales rbtSales = new RbtSales();
        if (!validateTrafficRbtCode(traffic)) {
            rbtSales.setStatus("Invalid RBT Code");
        }
         rbtSales.setPeriod(LocalDate.parse(traffic.getMonth() + "01", DateTimeFormatter.ofPattern("yyyyMMdd")));
        rbtSales.setProviderName(provider.getProviderName());
        rbtSales.setRbtCode(traffic.getRbtCode());
        rbtSales.setRbtHit(traffic.getRbtHit());
        rbtSales.setRbtRevenue(traffic.getRbtRevenue());
        return rbtSales;
    }

    private boolean validateTrafficRbtCode(Traffic traffic) {
        log.info("Validating RBT code: {}", traffic.getRbtCode());
        return codeToOperatorMap.containsKey(traffic.getRbtCode());
    }
}
