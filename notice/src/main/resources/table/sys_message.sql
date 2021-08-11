drop table sys_message;
create table sys_message
(
    msg_id    int auto_increment comment '消息ID'
        primary key,
    msg_type  varchar(50)                         not null comment '消息类型',
    sender    varchar(255)                        not null comment '发送用户',
    receiver  varchar(255)                        not null comment '接收用户',
    send_time timestamp default CURRENT_TIMESTAMP not null comment '发送时间',
    content   varchar(255)                        not null comment '文本消息内容'
);