drop table if exists sys_scheduled;
create table sys_scheduled
(
    cron_id         int auto_increment not null comment '主键' primary key,
    cron_key        varchar(128)       not null comment '定时任务完整类名',
    cron_expression varchar(20)        not null comment 'corn表达式',
    task_explain    varchar(50)        null comment '定时任务描述',
    status          tinyint            not null default 1 comment '状态,1:正常;2:停用'
)