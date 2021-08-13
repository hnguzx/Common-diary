drop table sys_user;
create table sys_user
(
    user_id                 int auto_increment comment '用户唯一标识'
        primary key,
    username                varchar(255) not null comment '昵称',
    birthday                timestamp    null comment '出生日期',
    sex                     varchar(10)  null comment '性别',
    password                varchar(255) not null comment '登录密码',
    phone                   varchar(30)  null comment '手机号码',
    email                   varchar(100) null comment '邮箱地址',
    role                    varchar(100) null comment '用户角色',
    create_time             timestamp         default CURRENT_TIMESTAMP not null comment '用户创建时间',
    update_time             timestamp         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '用户信息修改时间',
    account_non_expired     bit(1)       NULL DEFAULT NULL COMMENT '账号未到期',
    account_non_locked      bit(1)       NULL DEFAULT NULL COMMENT '账号未锁定',
    credentials_non_expired bit(1)       NULL DEFAULT NULL COMMENT '密码未过期',
    enabled                 bit(1)       NULL DEFAULT NULL COMMENT '账号已启用'
);

alter table sys_user
    auto_increment = 10000;