create table file_info
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)  null,
    last_modified_date datetime(6)  null,
    content_type       varchar(255) not null,
    file_name          varchar(255) not null,
    save_file_name     varchar(255) not null
);

create table member
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)  null,
    last_modified_date datetime(6)  null,
    email              varchar(255) not null,
    name               varchar(255) not null,
    password           varchar(255) null,
    role               varchar(255) not null,
    file_id            bigint       null,
    constraint UK_9esvgikrmti1v7ci6v453imdc
        unique (name),
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email),
    constraint FKnpx6j62r9blumj15270f6m8a4
        foreign key (file_id) references file_info (id)
);

create table video
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)  null,
    last_modified_date datetime(6)  null,
    description        longtext     null,
    title              varchar(200) not null,
    views              bigint       null,
    image_file_id      bigint       null,
    member_id          bigint       null,
    video_file_id      bigint       null,
    constraint FK2hpda8sgo91ermv5p1g5ajho
        foreign key (image_file_id) references file_info (id),
    constraint FKscdto09d6rnlyfg8vn7gl7b98
        foreign key (video_file_id) references file_info (id),
    constraint FKt1qraed5ypqn1u5s3jiq0xu1i
        foreign key (member_id) references member (id)
);

create table comment
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)  null,
    last_modified_date datetime(6)  null,
    text               varchar(200) not null,
    member_id          bigint       null,
    video_id           bigint       null,
    constraint FKbsuh08kv1lyh8v6ivufrollr6
        foreign key (video_id) references video (id),
    constraint FKmrrrpi513ssu63i2783jyiv9m
        foreign key (member_id) references member (id)
);

