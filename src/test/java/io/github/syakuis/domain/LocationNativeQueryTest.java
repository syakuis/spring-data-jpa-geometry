package io.github.syakuis.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
class LocationNativeQueryTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        double latitude = 32.321321;
        double longitude = 127.321321;

        entityManager.persist(LocationEntity.builder()
            .name("테스트")
            .latitude(latitude)
            .longitude(longitude)
            .build());
    }

    @Test
    void 거리구하기_ST_Distance_Sphere() {
        final double latitude = 37.513982;
        final double longitude = 127.101581;
        final double distance = 2D;

        String pointFormat = String.format("POINT(%f %f)", longitude, latitude);

        Query query = entityManager.createNativeQuery("SELECT *, "
                + "ST_Distance_Sphere(point, ST_GeomFromText('" + pointFormat + "')) as distance "
                + "FROM location "
                + "WHERE ST_Distance_Sphere(point, ST_GeomFromText('" + pointFormat + "')) <= " + (distance * 1000)
            , LocationEntity.class);

        List<LocationEntity> location = query.getResultList();

        log.debug("{}", location);

        assertEquals(2, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("롯데타워"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("올림픽대교남단"));
    }

    @Test
    void 구역구하기_폴리곤() {
        String arg = String.format("POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f))",
            126.926754, 37.526721, 126.927171, 37.526977, 126.927501, 37.526649, 126.927034, 37.526420, 126.926754, 37.526721);

        Query query = entityManager.createNativeQuery("SELECT r.* " +
            "FROM location r " +
            "where ST_WITHIN(r.point, ST_GEOMFROMTEXT('" + arg + "'))", LocationEntity.class);

        List<LocationEntity> location = query.getResultList();

        log.debug("{}", location);

        assertEquals(3, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("여의도동"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }

    @Test
    void 구역구하기_mbr() {
        String arg = String.format("LINESTRING(%f %f, %f %f)", 126.926754, 37.526721, 126.927501, 37.526649);

        Query query = entityManager.createNativeQuery("SELECT * " +
            "FROM location " +
            "WHERE MBRContains(ST_LINESTRINGFROMTEXT('" + arg + "'), point)", LocationEntity.class);

        List<LocationEntity> location = query.getResultList();

        log.debug("{}", location);

        assertEquals(3, location.size());
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("파크원"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("여의도동"));
        assertTrue(location.stream().map(LocationEntity::getName).toList().contains("뱅크샐러드"));
    }
}