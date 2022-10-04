create table location (
                          id bigint not null auto_increment,
                          latitude decimal(30,14) not null,
                          longitude decimal(31,14) not null,
                          name varchar(255) not null,
                          point GEOMETRY not null,
                          primary key (id)
) engine=InnoDB