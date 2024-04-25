alter table sys_user drop age;

alter table sys_user add birthday date default null comment '生日' after sex;