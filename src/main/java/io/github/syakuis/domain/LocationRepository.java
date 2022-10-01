package io.github.syakuis.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM location
        WHERE MBRContains(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', :north, ' ', :east, ',', :south, ' ', :west, ')')), point)
    """)
    List<LocationEntity> findAllByPoint(@Param("north") BigDecimal north, @Param("east") BigDecimal east, @Param("south") BigDecimal south, @Param("west") BigDecimal west);
}
