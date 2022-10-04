package io.github.syakuis.domain;

import lombok.*;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

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
public class LocationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 30, scale = 14)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 31, scale = 14)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Geometry point;

    @Builder
    public LocationEntity(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = BigDecimal.valueOf(latitude);
        this.longitude = BigDecimal.valueOf(longitude);
        try {
            this.point = new WKTReader().read("POINT(" + longitude + " " + latitude + ")");
        } catch (ParseException e) {
            throw new IllegalArgumentException("위도와 경도의 매개변수가 문제가 있습니다.");
        }
    }
}
