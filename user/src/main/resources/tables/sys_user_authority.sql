drop table sys_user_authority;
create table sys_user_authority
(
    id           int auto_increment
        primary key,
    user_id      int                                 null,
    authority_id int                                 null,
    create_time  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);