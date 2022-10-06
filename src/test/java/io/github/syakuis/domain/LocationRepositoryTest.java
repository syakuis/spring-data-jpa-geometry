package io.github.syakuis.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@Slf4j
@DataJpaTest
@Sql("classpath:/location-data.sql")
class LocationRepositoryTest {
    @Autowired
    private LocationRepository spatialRepository;

    @Test
    void 거리구하기() {
        final double latitude = 37.513982;
        final double longitude = 127.101581;
        final int distance = (int) 2D * 1000;

        List<LocationEntity> location = spatialRepository.findAllByDistance(latitude, longitude, distance);

        log.debug("{}", location);

        assertEquals(2, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("롯데타워"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("올림픽대교남단"));
    }

    @Test
    void 구역구하기_폴리곤() {
        List<LocationEntity> location = spatialRepository.findAllByAreaPolygon(126.926754, 37.526721, 126.927171, 37.526977, 126.927501, 37.526649, 126.927034, 37.526420);

        log.debug("{}", location);

        assertEquals(3, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("여의도동"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }

    @Test
    void 구역구하기_mbr() {
        List<LocationEntity> location = spatialRepository.findAllByAreaMbr(126.926754, 37.526721, 126.927501, 37.526649);

        log.debug("{}", location);

        assertEquals(3, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("여의도동"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }

}