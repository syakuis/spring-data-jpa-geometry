package io.github.syakuis.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.spatial.locationtech.jts.JTSGeometryExpressions;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.syakuis.domain.QGeometryEntity.geometryEntity;
import static io.github.syakuis.domain.QPointEntity.pointEntity;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-10-02
 */
@Repository
public class PointQueryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public PointQueryDsl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        super(PointEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    public List<PointEntity> findAll(double latitude, double longitude, double distance) {
            return jpaQueryFactory.selectFrom(pointEntity)
                .where(
                    JTSGeometryExpressions.asJTSGeometry(pointEntity.point)
                        .distanceSphere(JTSGeometryExpressions.fromText(String.format("POINT(%f %f)", latitude, longitude))).gt(2000)
                ).fetch();
    }


}
