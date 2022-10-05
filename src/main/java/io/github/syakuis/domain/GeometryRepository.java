package io.github.syakuis.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
public interface GeometryRepository extends CrudRepository<PointEntity, Long> {

    @Query(value = """
        SELECT g FROM GeometryEntity g
        WHERE ST_Distance(g.point, ST_GeomFromText(CONCAT('POINT(', :latitude, ' ', :longitude, ')'))) <= :distance
    """)
    List<PointEntity> findAllByPoint(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") int distance, Sort sort);
}
