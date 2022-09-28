package io.github.syakuis.domain;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-26
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "location",
    indexes = @Index(name = "IDX_location_point", columnList = "point")
)
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Point point;

    @Builder
    public LocationEntity(String name, Point point) {
        this.name = name;
        this.point = point;
    }
}
