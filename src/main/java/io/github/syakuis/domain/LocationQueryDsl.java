package io.github.syakuis.domain;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.spatial.locationtech.jts.JTSGeometryExpressions;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTConstants;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static io.github.syakuis.domain.QLocationEntity.locationEntity;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-10-02
 */
@Repository
public class LocationQueryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public LocationQueryDsl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        super(LocationEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    public List<LocationEntity> findAll(BigDecimal northX, BigDecimal eastY, BigDecimal southX, BigDecimal westY) {
        try {
            return jpaQueryFactory.selectFrom(locationEntity)
                .where(
                JTSGeometryExpressions.asJTSGeometry(locationEntity.point)
                    .contains(
                        new WKTReader().read(String.format("LINESTRING(%s %s, %s %s)", northX, eastY, southX, westY))))
                .fetch();
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }


}
