create table if NOT exists user
(
    userId      int(10) auto_increment COMMENT '用户唯一标识'
        primary key,
    username    varchar(30)  NOT NULL COMMENT '昵称',
    birthday    datetime     NULL COMMENT '出生日期',
    sex         varchar(10)  NULL COMMENT '性别',
    password    varchar(255) NOT NULL COMMENT '登录密码',
    state       varchar(20)  NOT NULL COMMENT '状态',
    phone       varchar(30)  NULL COMMENT '手机号码',
    email       varchar(100) NULL COMMENT '邮箱地址',
    create_time timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
    update_time timestamp    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息修改时间'
);

alter table user auto_increment = 10000;

create table if NOT exists role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(255) NOT NULL
);

create table if NOT exists pd_authority
(
    authority_id          int auto_increment
        primary key,
    authority_name        varchar(32)  NULL,
    authority_description varchar(255) NULL,
    authority_url         varchar(255) NULL
);

create table if NOT exists pd_role_authority
(
    role_authority_id int auto_increment
        primary key,
    role_id           int NOT NULL,
    authority_id      int NOT NULL
);

