CREATE SPATIAL INDEX IF NOT EXISTS IDX_location_point_1 ON location (point);

insert into location (id, name, latitude, longitude, point) values (1, '파크원', 37.526667, 126.927141, ST_GeomFromText('POINT 126.927141 37.526667'));
insert into location (id, name, latitude, longitude, point) values (2, 'IFC', 37.525243, 126.925694, ST_GeomFromText('POINT 126.925694 37.525243'));
insert into location (id, name, latitude, longitude, point) values (3, '롯데타워', 37.512603, 127.102228, ST_GeomFromText('POINT 127.102228 37.512603'));
insert into location (id, name, latitude, longitude, point) values (4, '올림픽공원역 1번출구 앞 따릉이 대여소', 37.516730, 127.131305, ST_GeomFromText('POINT 127.131305 37.516730'));
insert into location (id, name, latitude, longitude, point) values (5, '올림픽대교남단', 37.525619, 127.117681, ST_GeomFromText('POINT 127.117681 37.525619'));
insert into location (id, name, latitude, longitude, point) values (6, 'NH투자증권 본사', 37.526964, 126.927675, ST_GeomFromText('POINT 126.927675 37.526964'));
insert into location (id, name, latitude, longitude, point) values (7, '여의도동', 37.526702, 126.926791, ST_GeomFromText('POINT 126.926791 37.526702'));
insert into location (id, name, latitude, longitude, point) values (8, '뱅크샐러드', 37.526681, 126.926968, ST_GeomFromText('POINT 126.926968 37.526681'));