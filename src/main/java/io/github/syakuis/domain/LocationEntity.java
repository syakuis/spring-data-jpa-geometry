package io.github.syakuis.domain;

import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-26
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Point point;

    @Builder
    public LocationEntity(Point point) {
        this.point = point;
    }
}
