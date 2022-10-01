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

import static org.junit.jupiter.api.Assertions.assertFalse;
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

        log.debug("{} {} {} {}", north, east, south, west);

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(north), BigDecimal.valueOf(east), BigDecimal.valueOf(south), BigDecimal.valueOf(west));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("롯데타워"));
    }

    @Test
    void around_is_empty() {
        // 롯데타워 에서
        final Double meLatitude = 37.512603;
        final Double meLongitude = 127.102228;
        final Double distance = 2.0D;

        Coordinate northEast = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.NORTHEAST.getBearing());
        Coordinate southWest = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.SOUTHWEST.getBearing());

        double north = northEast.getLatitude();
        double east = northEast.getLongitude();
        double south = southWest.getLatitude();
        double west = southWest.getLongitude();

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(north), BigDecimal.valueOf(east), BigDecimal.valueOf(south), BigDecimal.valueOf(west));

        log.debug("{}", location);
        assertFalse(location.stream().map(LocationEntity::getName).toList().contains("올림픽공원역 1번출구 앞 따릉이 대여소"));
        assertFalse(location.stream().map(LocationEntity::getName).toList().contains("올림픽대교남단"));
    }

    @Test
    void rectangle() {
        // 북동
        double northX = 37.526962;
        double eastY = 126.927653;
        // 남서
        double southX = 37.526249;
        double westY = 126.926742;

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(northX), BigDecimal.valueOf(eastY), BigDecimal.valueOf(southX), BigDecimal.valueOf(westY));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }

    @Test
    void rectangle_is_empty() {
        // 북동
        double northX = 37.526962;
        double eastY = 126.927653;
        // 남서
        double southX = 37.526249;
        double westY = 126.926742;

        List<LocationEntity> location = locationRepository.findAllByPoint(
            BigDecimal.valueOf(northX), BigDecimal.valueOf(eastY), BigDecimal.valueOf(southX), BigDecimal.valueOf(westY));

        log.debug("{}", location);

        assertFalse(location.stream().map(LocationEntity::getName).toList().contains("NH투자증권 본사"));
    }
}