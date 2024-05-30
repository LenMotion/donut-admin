create table sys_export_log
(
    id          bigint                 not null comment 'id'
        primary key,
    tenant_id   bigint                 not null comment '租户ID',
    user_id     bigint                 not null comment '用户id',
    name        varchar(255) comment '文件名称',
    url         varchar(255) comment '文件地址',
    status      char(2)     default 0 comment '状态 0正在导出 1导出成功 2导出失败',
    error_msg   longtext comment '错误日志',
    exec_time   bigint comment '执行时间',
    create_by   varchar(64) default '' null comment '创建者',
    create_time datetime               null comment '创建时间',
    update_by   varchar(64) default '' null comment '更新者',
    update_time datetime               null comment '更新时间',
    remark      varchar(255)           null comment '备注',
    deleted     tinyint(1)  default 0  null comment '删除标志（0存在 1删除）'
)
    comment '导出记录';

INSERT INTO sys_dict_type (id, dict_type, dict_name, dict_key, status, create_by, create_time, update_by, update_time, remark) VALUES (1795324921464594433, '0', '导出执行状态', 'sys_export_exec_status', '0', '1', '2024-05-28 13:23:11', '', null, null);
INSERT INTO sys_dict_data (id, dict_key, dict_label, dict_value, dict_sort, list_class, status, create_by, create_time, update_by, update_time, remark) VALUES (1795325030159982593, 'sys_export_exec_status', '执行中', '0', 0, 'green', '0', '1', '2024-05-28 13:23:37', '', null, null);
INSERT INTO sys_dict_data (id, dict_key, dict_label, dict_value, dict_sort, list_class, status, create_by, create_time, update_by, update_time, remark) VALUES (1795325084786597890, 'sys_export_exec_status', '执行成功', '1', 1, 'blue', '0', '1', '2024-05-28 13:23:50', '', null, null);
INSERT INTO sys_dict_data (id, dict_key, dict_label, dict_value, dict_sort, list_class, status, create_by, create_time, update_by, update_time, remark) VALUES (1795325166508417025, 'sys_export_exec_status', '执行失败', '2', 2, 'red', '0', '1', '2024-05-28 13:24:10', '', null, null);
