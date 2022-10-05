package io.github.syakuis.domain;

import io.github.syakuis.space.Coordinate;
import io.github.syakuis.space.Direction;
import io.github.syakuis.space.GeometryUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@Slf4j
@DataJpaTest
@Sql("classpath:/location-data.sql")
class NativeLocationRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void save() throws Exception {
        double latitude = 32.321321;
        double longitude = 127.321321;

        locationRepository.save(LocationEntity.builder()
            .name("테스트")
            .latitude(latitude)
            .longitude(longitude)
            .build());
    }

    @Test
    void distance() {
        final double meLatitude = 37.513982;
        final double meLongitude = 127.101581;
        final Double distance = 2D;

        Coordinate northEast = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.NORTHEAST.getBearing());
        Coordinate southWest = GeometryUtils.calculate(meLatitude, meLongitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);

        Query query = entityManager.createNativeQuery("SELECT * "
                + "FROM location AS r "
                + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", r.point)", LocationEntity.class)
            .setMaxResults(10);

        List<LocationEntity> location = query.getResultList();

        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("롯데타워"));

        log.debug("{}", location);
    }

    @Test
    void rectangle() {
//        // 북동
//        double north = 37.526984;
//        double east = 126.927212;
//        // 남서
//        double south = 37.526181;
//        double west = 126.927087;

        // 북동
        double north = 37.5276993;
        double east = 126.927146;
        // 남서
        double south = 37.526238;
        double west = 126.927212;

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", north, east, south, west);

        Query query = entityManager.createNativeQuery("SELECT * "
                + "FROM location AS r "
                + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", r.point)", LocationEntity.class)
            .setMaxResults(10);

        List<LocationEntity> location = query.getResultList();

        log.debug("{}", location);

        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
    }
}