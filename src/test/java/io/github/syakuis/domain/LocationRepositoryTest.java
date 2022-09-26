package io.github.syakuis.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.geo.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
@DataJpaTest
class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void save() {
        locationRepository.save(LocationEntity.builder().point(new Point(11.11, 222.222)).build());
    }

}