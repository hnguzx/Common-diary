drop table sys_role;
create table if not exists sys_role
(
    role_id     int auto_increment                  not null comment '主键'
        primary key,
    role_code   varchar(50)                         null comment '角色编码',
    role_name   varchar(255)                        null comment '角色名',
    role_desc   varchar(255)                        null comment '描述',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);

INSERT INTO sys_role (role_code, role_name, role_desc)
VALUES ('1001', 'ROLE_ADMIN', '超级管理员');
INSERT INTO sys_role (role_code, role_name, role_desc)
VALUES ('2001', 'ROLE_USER', '普通用户');
