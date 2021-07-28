drop table sys_user_role;
create table sys_user_role
(
    id          int auto_increment
        primary key,
    user_id     int                                 null,
    role_id     int                                 null,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);

INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1);