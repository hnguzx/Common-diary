drop table sys_authority;
create table sys_authority
(
    authority_id   int auto_increment comment '唯一标识'
        primary key,
    authority_name varchar(255)                        null comment '权限名称',
    authority_code varchar(10)                         null comment '权限编码',
    url            varchar(255)                        not null comment '请求url',
    create_time    timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
);

INSERT INTO sys_authority(authority_name, authority_code, url)
VALUES ('管理员权限测试接口', '100001', '/admin/demo');
INSERT INTO sys_authority(authority_name, authority_code, url)
VALUES ('普通用户权限测试接口', '200001', '/user/demo');
INSERT INTO sys_authority(authority_name, authority_code, url)
VALUES ('特殊权限测试接口', '300001', '/private/demo');
