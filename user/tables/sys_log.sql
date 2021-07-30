create table if not exists cd_sys_log
(
    log_id         int(10) auto_increment primary key,
    user_name      varchar(30)  not null,
    user_operation varchar(10)  not null,
    request_method varchar(10)  not null,
    request_params varchar(255) not null,
    request_ip     varchar(20)  not null,
    create_time    datetime     null
);

alter table cd_sys_log auto_increment = 10000;