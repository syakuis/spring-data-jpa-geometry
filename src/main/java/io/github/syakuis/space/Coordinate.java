package io.github.syakuis.space;

import lombok.Getter;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-09-28
 */
@Getter
public class Coordinate {

    private final Double latitude;
    private final Double longitude;

    public Coordinate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
