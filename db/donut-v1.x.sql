-- 1.x 所有版本的sql整合到此文件

-- v1.1.0

DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `id`           bigint       NOT NULL COMMENT '表格id',
    `table_name`   varchar(100) NOT NULL COMMENT '表名',
    `module_name`  varchar(255)                                                  DEFAULT NULL COMMENT '模块名称',
    `menu_id`      bigint                                                        DEFAULT NULL COMMENT '所属模块',
    `feature_name` varchar(255)                                                  DEFAULT NULL COMMENT '功能名',
    `author`       varchar(50)                                                   DEFAULT NULL COMMENT '作者',
    `package_name` varchar(255)                                                  DEFAULT NULL COMMENT '包名',
    `super_class`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '继承上级类',
    `class_name`   varchar(255)                                                  DEFAULT NULL COMMENT '类名',
    `status_api`   tinyint(1)                                                    DEFAULT '1' COMMENT '是否开启状态接口',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    `deleted`      tinyint(1)                                                    DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='代码生成table信息';


DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `id`                bigint       NOT NULL COMMENT 'id',
    `table_id`          bigint       NOT NULL COMMENT '归属表id',
    `column_name`       varchar(255) NOT NULL COMMENT '列名',
    `column_remark`     varchar(255)                                                  DEFAULT NULL COMMENT '备注',
    `column_type`       varchar(55)                                                   DEFAULT NULL COMMENT '类型',
    `upper_field_name`  varchar(255)                                                  DEFAULT NULL COMMENT '首字母大写的Java列名',
    `field_name`        varchar(255)                                                  DEFAULT NULL COMMENT 'Java列名',
    `java_type_class`   varchar(255)                                                  DEFAULT NULL COMMENT 'java类型的class路径',
    `java_type`         varchar(50)                                                   DEFAULT NULL COMMENT 'java类型',
    `id_field`          tinyint(1)                                                    DEFAULT '0' COMMENT '是否主键',
    `search_field`      tinyint(1)                                                    DEFAULT '0' COMMENT '是否查询列',
    `search_field_type` varchar(50)                                                   DEFAULT NULL COMMENT '查询列类型',
    `edit_field`        tinyint(1)                                                    DEFAULT '0' COMMENT '是否编辑列',
    `edit_field_type`   varchar(50)                                                   DEFAULT NULL COMMENT '编辑列类型',
    `table_field`       tinyint(1)                                                    DEFAULT '0' COMMENT '是否列表列',
    `ignore_field`      tinyint(1)                                                    DEFAULT '0' COMMENT '是否忽略',
    `sort_index`        int                                                           DEFAULT '0' COMMENT '序号',
    `dict_key`          varchar(255)                                                  DEFAULT NULL COMMENT '字典key',
    `create_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    `deleted`           tinyint(1)                                                    DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='代码生成table列信息';

INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1765619430731698177, '生成管理', 'Gen', 0, '0', '/gen', NULL, NULL, 'LAYOUT', 1, 0, 0, '0', NULL, 'ant-design:laptop-outlined', 5, NULL, 0, '1', '2024-03-07 14:04:11', '1', '2024-03-07 14:04:30', NULL, 0);
INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1767064995604787202, '代码生成', 'GenCode', 1765619430731698177, '1', 'code', NULL, NULL, '/gen/code/index', 1, 0, 0, '0', 'gen:code:list', '', 1, NULL, 0, '1', '2024-03-11 13:48:20', '', NULL, '', 0);


-- v1.1.1
-- 密码输入错误锁定
INSERT INTO sys_config (id, config_name, config_key, config_value, system_config, create_by, create_time, update_by, update_time, remark) VALUES (1771085140132188161, '账号密码连续输入错误次数', 'ACCOUNT_LOCK_COUNT', '3', 1, '1', '2024-03-22 16:02:57', '', null, null);
INSERT INTO sys_config (id, config_name, config_key, config_value, system_config, create_by, create_time, update_by, update_time, remark) VALUES (1771085227071721474, '账号锁定时长（分钟）', 'ACCOUNT_LOCK_TIME', '10', 1, '1', '2024-03-22 16:03:18', '', null, null);


-- v1.1.2
alter table sys_login_log add token_value varchar(255) default null comment 'token值' after login_time;

INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1772633885608275970, '在线用户', 'OnlineUserManagement', 1747515992408047617, '1', 'onlineUser', NULL, NULL, '/monitor/onlineUser/index', 1, 0, 0, '0', 'monitor:onlineUser:list', NULL, 3, NULL, 0, '1', '2024-03-26 22:37:07', '1', '2024-03-26 22:50:59', NULL, 0);
INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1772637557499129858, '踢人下线', NULL, 1772633885608275970, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'monitor:onlineUser:kickOut', '', 1, NULL, 0, '1', '2024-03-26 22:51:42', '', NULL, '', 0);


-- v1.1.3
INSERT INTO `sys_file_storage` (`id`, `url`, `size`, `filename`, `original_filename`, `base_path`, `path`, `ext`, `content_type`, `platform`, `th_url`, `th_filename`, `th_size`, `th_content_type`, `object_id`, `object_type`, `metadata`, `user_metadata`, `th_metadata`, `th_user_metadata`, `attr`, `file_acl`, `th_file_acl`, `create_time`, `deleted`) VALUES ('bd402cdfad364e78b18f68c056dbeece', 'http://localhost:9000/open/logo.png', 7694, 'logo.png', '甜甜圈 (1).png', '', 'open/', 'png', 'image/png', 'minio-1', NULL, NULL, NULL, NULL, NULL, NULL, '{}', '{}', '{}', '{}', '{}', NULL, NULL, '2024-04-24 11:06:32', 0);

UPDATE `sys_user` set `avatar` = 'http://localhost:9000/open/logo.png' where `id` = 1;

-- v1.1.4

ALTER TABLE sys_config MODIFY COLUMN config_value LONGTEXT comment '参数值';

update sys_config set config_value = 'Donut Admin' where id = 6;

INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `system_config`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1783005313353056258, '系统标题', 'SYSTEM_TITLE', '甜甜圈通用管理系统', 1, '1', '2024-04-24 13:29:28', '', NULL, NULL);
INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `system_config`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1783005477874630657, '系统描述', 'SYSTEM_DESCRIPTION', '一款基于SpringBoot3、JDK17、SaToken、MybatisPlus的开源管理系统。', 1, '1', '2024-04-24 13:30:07', '', NULL, NULL);
INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `system_config`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1783005566110203905, '系统logo', 'SYSTEM_LOGO', 'data:image/png;base64,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', 1, '1', '2024-04-24 13:30:28', '', NULL, NULL);


-- v1.1.5
alter table sys_user drop age;

alter table sys_user add birthday date default null comment '生日' after sex;


-- v1.2.0
CREATE TABLE `gen_datasource`
(
    `id`               bigint       NOT NULL COMMENT 'id',
    `type`             varchar(10)  not null comment '数据源类型 字典gen_datasource_type',
    `name`             varchar(50)  not null comment '名称',
    `host`             varchar(50)  not null COmment 'host',
    `port`             int(11)      not null comment '端口',
    `schema_name`      varchar(100) not null comment '库',
    `username`         varchar(50)  not null comment '用户名',
    `password`         varchar(500) not null comment '密码',
    `status`           tinyint                                                DEFAULT NULL COMMENT '状态 字典sys_base_status',
    `check_connection` tinyint(1)                                             DEFAULT 0 COMMENT '连接测试是否通过',
    `create_time`      timestamp    NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `update_time`      timestamp    NOT NULL                                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `deleted`          tinyint(1)                                             DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC COMMENT ='数据源';

INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_key`, `status`, `create_by`, `create_time`,
                             `update_by`, `update_time`, `remark`)
VALUES (1783788938260725762, '0', '数据源类型', 'gen_datasource_type', '0', '1', '2024-04-26 17:23:19', '', NULL,
        '代码生成');

INSERT INTO `sys_dict_data` (`id`, `dict_key`, `dict_label`, `dict_value`, `dict_sort`, `list_class`, `status`,
                             `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (1783789039880323073, 'gen_datasource_type', 'mysql', 'mysql', 1, 'blue', '0', '1', '2024-04-26 17:23:43', '',
        NULL, NULL);

INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`,
                        `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`,
                        `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`,
                        `remark`, `deleted`)
VALUES (1783794396425834496, '数据源', 'GenDatasource', 1765619430731698177, '1', 'datasource', NULL, NULL,
        '/gen/datasource/index', 1, 0, 0, '0', 'generator:genDatasource:list', NULL, 1, NULL, 0, '1', now(),
        '1', NULL, NULL, 0),
       (1783794396425834497, '保存信息', NULL, 1783794396425834496, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'generator:genDatasource:save', '', 0, NULL, 0, '1', now(), '', NULL, '', 0),
       (1783794396425834498, '删除信息', NULL, 1783794396425834496, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'generator:genDatasource:remove', '', 1, NULL, 0, '1', now(), '', NULL, '', 0),
       (1783794396425834499, '更新状态', NULL, 1783794396425834496, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'generator:genDatasource:status', '', 2, NULL, 0, '1', now(), '', NULL, '', 0),
       (1784044183687434242, '测试连接', NULL, 1783794396425834496, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'generator:genDatasource:checkConnection', '', 0, NULL, 0, '1', now(), '', NULL, '', 0);

alter table gen_table
    add datasource_id bigint comment '数据源id' after id;


-- v1.2.1
INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1784866340659212290, '文档地址', NULL, 0, '1', 'https://donut-doc.lenmotion.cn/', NULL, NULL, 'Frame', 0, 0, 0, '0', NULL, 'ant-design:book-outlined', 6, NULL, 1, '1', '2024-04-29 16:44:31', '', NULL, '', 0);
INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`, `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`, `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `deleted`) VALUES (1784875438691790850, '更新状态', NULL, 7, '2', '', NULL, NULL, '', 0, 0, 0, '0', 'system:menu:status', '', 3, NULL, 0, '1', '2024-04-29 17:20:41', '', NULL, '', 0);

