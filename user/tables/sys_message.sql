CREATE TABLE `sys_message`
(
    `msg_id`    int(10)      NOT NULL COMMENT '消息ID' primary key,
    `msg_type`  int(10)      NOT NULL COMMENT '消息类型',
    `subject`  int(10)       COMMENT '消息主题',
    `sender`    varchar(255)      NOT NULL COMMENT '发送用户',
    `receiver`  varchar(255)     NOT NULL COMMENT '接收用户',
    `cc`  varchar(255)     NOT NULL COMMENT '抄送用户',
    `bcc`  varchar(255)     NOT NULL COMMENT '密送用户',
    `send_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    `content`   varchar(255) NOT NULL COMMENT '消息内容（图片，音频，文件2.0处理）'
);