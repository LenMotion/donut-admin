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