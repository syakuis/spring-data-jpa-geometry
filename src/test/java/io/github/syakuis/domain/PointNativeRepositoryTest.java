package io.github.syakuis.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@Slf4j
@DataJpaTest
@Sql("classpath:/point-data.sql")
class PointNativeRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        double latitude = 32.321321;
        double longitude = 127.321321;

        entityManager.persist(PointEntity.builder()
            .name("테스트")
            .latitude(latitude)
            .longitude(longitude)
            .build());
    }

    @Test
    void coordinate() {
        final double latitude = 37.513982;
        final double longitude = 127.101581;
        final double distance = 2D;

        String pointFormat = String.format("POINT(%f %f)", latitude, longitude);

        Query query = entityManager.createNativeQuery("SELECT r.*, "
                + "ST_Distance_Sphere(r.point, ST_GeomFromText('\" + pointFormat + \"')) as distance "
                + "FROM geo_point AS r "
                + "WHERE ST_Distance_Sphere(r.point, ST_GeomFromText('" + pointFormat + "')) <= " + (distance * 1000)
                , PointEntity.class);

        List<PointEntity> geometry = query.getResultList();

        assertTrue(geometry.stream().map(PointEntity::getName).toList().contains("롯데타워"));
        assertTrue(geometry.stream().map(PointEntity::getName).toList().contains("올림픽대교남단"));

        log.debug("{}", geometry);
    }
}