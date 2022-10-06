package io.github.syakuis.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-27
 */
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    @Query(nativeQuery = true, value = """
        SELECT r.* FROM location r
        WHERE ST_Distance_Sphere(r.point, ST_GEOMFROMTEXT(CONCAT('POINT(', :longitude, ' ', :latitude, ')')))  <= :distance
    """)
    List<LocationEntity> findAllByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") int distance);

    @Query(nativeQuery = true, value = """
        SELECT r.* FROM location r
        WHERE ST_WITHIN(r.point, ST_GEOMFROMTEXT(
            CONCAT('POLYGON((', :x1, ' ', :y1, ' ,', :x2, ' ', :y2, ', ', :x3, ' ', :y3, ', ', :x4, ' ', :y4, ', ', :x1, ' ', :y1, '))')
        ))
    """)
    List<LocationEntity> findAllByAreaPolygon(@Param("x1") double x1, @Param("y1") double y1, @Param("x2") double x2, @Param("y2") double y2, @Param("x3") double x3, @Param("y3") double y3, @Param("x4") double x4, @Param("y4") double y4);

    @Query(nativeQuery = true, value = """
        SELECT r.* FROM location r
        WHERE MBRContains(ST_LINESTRINGFROMTEXT(
        CONCAT('LINESTRING(', :x1, ' ', :y1, ', ', :x2, ' ', :y2, ')')
        ), r.point)
    """)
    List<LocationEntity> findAllByAreaMbr(@Param("x1") double x1, @Param("y1") double y1, @Param("x2") double x2, @Param("y2") double y2);
}
