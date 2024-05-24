create table sys_tenant
(
    id          bigint                  not null comment 'id'
        primary key,
    name        varchar(100)            not null comment '租户名称',
    description varchar(255)            null comment '租户描述',
    group_name  varchar(100)            null comment '所属集团',
    status      char(2)     default '0' null comment '状态（0正常 1停用）',
    user_id     bigint                  null comment '超管id',
    role_id     bigint                  null comment '超管角色id',
    create_by   varchar(64) default ''  null comment '创建者',
    create_time datetime                null comment '创建时间',
    update_by   varchar(64) default ''  null comment '更新者',
    update_time datetime                null comment '更新时间',
    remark      varchar(255)            null comment '备注',
    deleted     tinyint(1)  default 0   null comment '删除标志（0存在 1删除）'
)
    comment '租户表';

alter table sys_config
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_file_storage
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_login_log
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_notice
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_notice_read
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_notice_send_relation
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_operation_log
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_role
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_post
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_role_menu
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_user
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_user_dept
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_user_role
    add tenant_id bigint default 1 comment '租户id' after id;
alter table sys_dept
    add tenant_id bigint default 1 comment '租户id' after id;

INSERT INTO sys_menu (id, title, name, parent_id, menu_type, path, current_active_menu, redirect, component,
                            ignore_keep_alive, hide_menu, hide_children_in_menu, status, perms, icon, order_no, query,
                            frame, create_by, create_time, update_by, update_time, remark, deleted)
VALUES (1793472257501917186, '租户管理', 'Tenant', 0, '0', '/tenant', null, null, 'LAYOUT', 0, 0, 0, '0', null,
        'ant-design:code-sandbox-outlined', 6, null, 0, '1', '2024-05-23 10:41:22', '', null, '', 0);

INSERT INTO `sys_menu` (`id`, `title`, `name`, `parent_id`, `menu_type`, `path`, `current_active_menu`, `redirect`,
                        `component`, `ignore_keep_alive`, `hide_menu`, `hide_children_in_menu`, `status`, `perms`,
                        `icon`, `order_no`, `query`, `frame`, `create_by`, `create_time`, `update_by`, `update_time`,
                        `remark`, `deleted`)
VALUES (1793474214046076928, '租户列表', 'SysTenantManage', 1793472257501917186, '1', 'sysTenant', NULL, NULL,
        '/sys/modules/tenant/index.vue', 1, 0, 0, '0', 'system:sysTenant:list', NULL, 1, NULL, 0, '1', now(), '1', NULL,
        NULL,
        0),
       (1793474214046076929, '保存信息', NULL, 1793474214046076928, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'system:sysTenant:save', '', 0, NULL, 0, '1', now(), '', NULL, '', 0),
       (1793474214046076930, '删除信息', NULL, 1793474214046076928, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'system:sysTenant:remove', '', 1, NULL, 0, '1', now(), '', NULL, '', 0),
       (1793474214046076931, '更新状态', NULL, 1793474214046076928, '2', '', NULL, NULL, '', 1, 0, 0, '0',
        'system:sysTenant:status', '', 2, NULL, 0, '1', now(), '', NULL, '', 0);


INSERT INTO sys_dept (id, tenant_id, parent_id, level, ancestors, dept_name, short_name, dept_code, order_num, leader, phone, email, status, create_by, create_time, update_by, update_time, remark, deleted) VALUES (1793843405932711936, 1793843403441422338, 0, 1, '1793843405932711936', '测试租户', '测试租户', '001', 1, null, null, null, '0', '1', '2024-05-24 11:16:11', '', null, null, 0);

INSERT INTO sys_post (id, tenant_id, post_type, dept_id, post_code, post_name, order_no, status, create_by, create_time, update_by, update_time, remark) VALUES (1793843405542768641, 1793843403441422338, '0', 0, 'SUPER', '超级管理员', 1, '0', '1', '2024-05-24 11:16:11', '', null, null);
INSERT INTO sys_role (id, tenant_id, role_name, role_key, role_sort, data_scope, status, create_by, create_time, update_by, update_time, remark, deleted) VALUES (1793843403722440706, 1793843403441422338, '超级管理员', 'SUPER', 1, '1', '0', '1', '2024-05-24 11:16:10', '', null, null, '0');
INSERT INTO sys_role (id, tenant_id, role_name, role_key, role_sort, data_scope, status, create_by, create_time, update_by, update_time, remark, deleted) VALUES (1793898435981438978, 1793843403441422338, '测试角色', 'NORMAL', 2, '1', '1', '1793843405999947778', '2024-05-24 14:54:51', '', null, null, '0');
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843403793743874, 1793843403441422338, 1793843403722440706, 1, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843403827298306, 1793843403441422338, 1793843403722440706, 2, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843403856658433, 1793843403441422338, 1793843403722440706, 15, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843403886018562, 1793843403441422338, 1793843403722440706, 4, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404087345154, 1793843403441422338, 1793843403722440706, 16, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404611633154, 1793843403441422338, 1793843403722440706, 17, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404628410369, 1793843403441422338, 1793843403722440706, 18, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404661964802, 1793843403441422338, 1793843403722440706, 1747917935722872834, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404687130625, 1793843403441422338, 1793843403722440706, 1748152015395745794, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404708102146, 1793843403441422338, 1793843403722440706, 1747918077913972738, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404733267969, 1793843403441422338, 1793843403722440706, 1747918178480799745, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404758433793, 1793843403441422338, 1793843403722440706, 1747918693755240449, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404775211009, 1793843403441422338, 1793843403722440706, 1747918883321004034, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404800376834, 1793843403441422338, 1793843403722440706, 1748152219645767681, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404829736961, 1793843403441422338, 1793843403722440706, 1747919006339940354, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404850708481, 1793843403441422338, 1793843403722440706, 1747919428949622785, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404871680001, 1793843403441422338, 1793843403722440706, 1748154505751162882, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404892651521, 1793843403441422338, 1793843403722440706, 1747919507039174657, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404913623041, 1793843403441422338, 1793843403722440706, 5, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404934594561, 1793843403441422338, 1793843403722440706, 1747921301937045506, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843404959760386, 1793843403441422338, 1793843403722440706, 1747996340529352705, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405014286338, 1793843403441422338, 1793843403722440706, 1747921723582038017, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405047840769, 1793843403441422338, 1793843403722440706, 9, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405077200898, 1793843403441422338, 1793843403722440706, 10, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405106561025, 1793843403441422338, 1793843403722440706, 1747074132006850561, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405127532546, 1793843403441422338, 1793843403722440706, 1747148469133094913, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405152698370, 1793843403441422338, 1793843403722440706, 12, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405173669890, 1793843403441422338, 1793843403722440706, 1747923834810122241, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405228195842, 1793843403441422338, 1793843403722440706, 1748155012431474690, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405253361665, 1793843403441422338, 1793843403722440706, 1747923976258830338, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405278527489, 1793843403441422338, 1793843403722440706, 1747924189681795073, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405303693314, 1793843403441422338, 1793843403722440706, 1747924307512377346, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405324664833, 1793843403441422338, 1793843403722440706, 13, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405337247746, 1793843403441422338, 1793843403722440706, 14, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405358219266, 1793843403441422338, 1793843403722440706, 1747996508121157633, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405387579393, 1793843403441422338, 1793843403722440706, 1748155709478662146, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405404356609, 1793843403441422338, 1793843403722440706, 1748155618076389377, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405421133826, 1793843403441422338, 1793843403722440706, 1748155869352947713, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405433716738, 1793843403441422338, 1793843403722440706, 1784866340659212290, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405454688257, 1793843403441422338, 1793843403722440706, 1748664655401738242, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405471465474, 1793843403441422338, 1793843403722440706, 1747177110302846977, 0);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405488242689, 1793843403441422338, 1793843403722440706, 3, 1);
INSERT INTO sys_role_menu (id, tenant_id, role_id, menu_id, half_menu) VALUES (1793843405513408514, 1793843403441422338, 1793843403722440706, 6, 1);
INSERT INTO sys_user (id, tenant_id, dept_id, user_code, username, nick_name, real_name, post_id, email, phone_number, sex, birthday, avatar, password, nation, id_type, id_card, culture_type, address, entry_date, political_outlook, status, login_ip, login_date, quick_nav, create_by, create_time, update_by, update_time, remark, deleted) VALUES (1793843405999947778, 1793843403441422338, 1793843405932711936, '001', 'admin', '超级管理员', '测试租户', 1793843405542768641, '', '', '1', '2024-05-24', '', '0e40f23191aac5b831f56a07d38a719929f1f8cdd575840c3d185d71bc5f876e', null, null, null, null, null, null, null, '0', '127.0.0.1', '2024-05-24 14:54:12', null, '1', '2024-05-24 11:16:11', '1793843405999947778', '2024-05-24 14:54:12', null, '0');
INSERT INTO sys_user_dept (id, tenant_id, user_id, dept_id, post_id, default_dept) VALUES (1793843406075445249, 1793843403441422338, 1793843405999947778, 1793843405932711936, 1793843405542768641, 1);
INSERT INTO sys_user_role (id, tenant_id, user_id, role_id) VALUES (1793843406050279426, 1793843403441422338, 1793843405999947778, 1793843403722440706);

INSERT INTO sys_tenant (id, name, description, group_name, status, create_by, create_time, update_by, update_time, remark, deleted, user_id, role_id) VALUES (1, '系统租户', '此为最高租户，管理所有租户信息', '系统', '0', '1', '2024-05-23 10:55:32', '1', '2024-05-23 10:56:50', null, 0, null, null);
INSERT INTO sys_tenant (id, name, description, group_name, status, create_by, create_time, update_by, update_time, remark, deleted, user_id, role_id) VALUES (1793843403441422338, '测试租户', null, null, '0', '1', '2024-05-24 11:16:10', '1', '2024-05-24 11:16:11', null, 0, 1793843405999947778, 1793843403722440706);
