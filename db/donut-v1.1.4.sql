
ALTER TABLE sys_config MODIFY COLUMN config_value LONGTEXT comment '参数值';

update sys_config set config_value = 'Donut Admin' where id = 6;
