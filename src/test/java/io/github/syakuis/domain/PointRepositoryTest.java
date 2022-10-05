package io.github.syakuis.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@Slf4j
@DataJpaTest
@Sql("classpath:/point-data.sql")
class PointRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PointRepository pointRepository;

    @Test
    void coordinate() {
        final double latitude = 37.513982;
        final double longitude = 127.101581;
        final int distance = 2000;

        List<PointEntity> geometry = pointRepository.findAllByPoint(latitude, longitude, distance, Sort.by(Sort.Direction.DESC, "name"));

        assertTrue(geometry.stream().map(PointEntity::getName).toList().contains("롯데타워"));
        assertTrue(geometry.stream().map(PointEntity::getName).toList().contains("올림픽대교남단"));

        log.debug("{}", geometry);
    }
}