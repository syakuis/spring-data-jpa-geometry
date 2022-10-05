package io.github.syakuis.domain;

import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-26
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "geo_point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 30, scale = 14)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 31, scale = 14)
    private BigDecimal longitude;

    @Column(nullable = false, columnDefinition = "POINT")
    private Point point;

    @Builder
    public PointEntity(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = BigDecimal.valueOf(latitude);
        this.longitude = BigDecimal.valueOf(longitude);
        try {
            this.point = (Point) new WKTReader().read("POINT(" + longitude + " " + latitude + ")");
        } catch (ParseException e) {
            throw new IllegalArgumentException("위도와 경도의 매개변수가 문제가 있습니다.");
        }
    }
}
