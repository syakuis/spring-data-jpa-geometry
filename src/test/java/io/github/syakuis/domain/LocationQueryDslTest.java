package io.github.syakuis.domain;

import io.github.syakuis.configuration.QuerydslConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-10-04
 */
@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@Import(QuerydslConfiguration.class)
@Sql("classpath:/location-data.sql")
class LocationQueryDslTest {
    @Autowired
    private LocationQueryDsl locationQueryDsl;

    @Test
    void findAll() {
        // 북동
        double northX = 37.526962;
        double eastY = 126.927653;
        // 남서
        double southX = 37.526249;
        double westY = 126.926742;

        List<LocationEntity> location = locationQueryDsl.findAll(BigDecimal.valueOf(northX), BigDecimal.valueOf(eastY), BigDecimal.valueOf(southX), BigDecimal.valueOf(westY));
        log.debug("{}", location);
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }
}