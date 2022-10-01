package io.github.syakuis.domain;

import io.github.syakuis.space.Coordinate;
import io.github.syakuis.space.Direction;
import io.github.syakuis.space.GeometryUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@Slf4j
@DataJpaTest
@Sql("classpath:/location.sql")
class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void save() {
        BigDecimal latitude = new BigDecimal("32.321321");
        BigDecimal longitude = new BigDecimal("127.321321");

        locationRepository.save(LocationEntity.builder()
            .name("테스트")
            .longitude(longitude)
            .latitude(latitude)
            .build());
    }

    @Test
    void around() {
        final Double meLatitude = 37.513982;
        final Double meLongitude = 127.101581;
        final Double distance = 2D;

        Coordinate northEast = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.NORTHEAST.getBearing());
        Coordinate southWest = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.SOUTHWEST.getBearing());

        double north = northEast.getLatitude();
        double east = northEast.getLongitude();
        double south = southWest.getLatitude();
        double west = southWest.getLongitude();

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(north), BigDecimal.valueOf(east), BigDecimal.valueOf(south), BigDecimal.valueOf(west));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("롯데타워"));
    }

    @Test
    void rectangle() {
        // 북동
        double north = 37.526984;
        double east = 126.927212;
        // 남서
        double south = 37.526181;
        double west = 126.927087;

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(north), BigDecimal.valueOf(east), BigDecimal.valueOf(south), BigDecimal.valueOf(west));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
    }
}