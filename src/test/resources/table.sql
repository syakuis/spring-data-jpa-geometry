create table location (
                          id bigint not null auto_increment,
                          latitude decimal(30,14) not null,
                          longitude decimal(31,14) not null,
                          name varchar(255) not null,
                          point GEOMETRY not null,
                          primary key (id)
) engine=InnoDB

CREATE SPATIAL INDEX IDX_location_point_1 ON location (point);

-- SELECT r.*, ST_Distance_Sphere(r.point, ST_GeomFromText('POINT(37.513982 127.101581)')) as distance
-- FROM test AS r
-- WHERE ST_Distance_Sphere(r.point, ST_GeomFromText('POINT(37.513982 127.101581)')) <= 2000;
--
-- SELECT r.*, ST_Distance_Sphere(r.point, ST_GeomFromText('POINT(127.101581 37.513982)')) as distance
-- FROM test AS r
-- WHERE ST_Distance_Sphere(r.point, ST_GeomFromText('POINT(127.101581 37.513982)')) <= 2000;
--
-- SELECT r.*, ST_Distance(r.point, ST_GeomFromText('POINT(127.101581 37.513982)')) as distance
-- FROM test AS r;
--
-- SELECT r.*
-- FROM test AS r
-- where ST_WITHIN(r.point, ST_GEOMFROMTEXT('POLYGON((126.926788 37.536702, 126.927184 37.526935, 126.927502 37.526634, 126.927106 37.526392, 126.926788 37.536702))'));
--
-- SELECT r.*
-- FROM test AS r
-- where MBRContains(ST_LINESTRINGFROMTEXT('LINESTRING(126.926774 37.526700, 126.927507 37.526626)'), r.point);