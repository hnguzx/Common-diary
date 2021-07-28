drop table sys_role_authority;
create table sys_role_authority
(
    id           int auto_increment
        primary key,
    role_id      int                                 null,
    authority_id int                                 null,
    create_time  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);

INSERT INTO sys_role_authority (role_id, authority_id)
VALUES (1, 1);
INSERT INTO sys_role_authority (role_id, authority_id)
VALUES (1, 2);