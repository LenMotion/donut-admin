
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
