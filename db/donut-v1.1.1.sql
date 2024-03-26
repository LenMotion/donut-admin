-- 密码输入错误锁定
INSERT INTO sys_config (id, config_name, config_key, config_value, system_config, create_by, create_time, update_by, update_time, remark) VALUES (1771085140132188161, '账号密码连续输入错误次数', 'ACCOUNT_LOCK_COUNT', '3', 1, '1', '2024-03-22 16:02:57', '', null, null);
INSERT INTO sys_config (id, config_name, config_key, config_value, system_config, create_by, create_time, update_by, update_time, remark) VALUES (1771085227071721474, '账号锁定时长（分钟）', 'ACCOUNT_LOCK_TIME', '10', 1, '1', '2024-03-22 16:03:18', '', null, null);
