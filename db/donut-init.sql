/*
 Navicat Premium Data Transfer

 Source Server         : tencent8
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 139.155.69.83:15308
 Source Schema         : donut

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 23/01/2024 10:08:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
  `system_config` tinyint(1) NULL DEFAULT 0 COMMENT '是否系统内置',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '验证码开关', 'LOGIN_CAPTCHA_SWITCH', 'false', 1, '', NULL, '', NULL, NULL);
INSERT INTO `sys_config` VALUES (2, '默认密码', 'USER_DEFAULT_PASSWORD', 'qwe@123', 1, '', NULL, '', NULL, NULL);
INSERT INTO `sys_config` VALUES (3, '最大部门层级', 'MAX_DEPT_LEVEL', '0', 1, '', NULL, '', NULL, NULL);
INSERT INTO `sys_config` VALUES (6, '系统名称', 'SYSTEM_NAME', 'Dount Admin', 1, '', NULL, '', NULL, NULL);
INSERT INTO `sys_config` VALUES (7, '系统默认菜单', 'DEFAULT_MENU', '2,1747177110302846977,1748664655401738242', 1, '', NULL, '', NULL, NULL);
INSERT INTO `sys_config` VALUES (1748164430560751617, '页脚', 'SITE_COPYRIGHT', 'Copyright © 2024 Donut Admin', 1, '1', '2024-01-19 10:04:14', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `level` tinyint(1) NULL DEFAULT NULL COMMENT '等级',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `short_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简称',
  `dept_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位编号',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (2, 0, 1, '2', 'Donut总公司', '总公司', 'GS0001', 1, 'LenMotion', NULL, NULL, '0', '1', '2024-01-04 22:06:27', '1', '2024-01-04 22:06:49', NULL, 0);
INSERT INTO `sys_dept` VALUES (3, 2, 2, '2,3', '成都分公司', '成都', 'GS0002', 1, NULL, NULL, NULL, '0', '1', '2024-01-04 22:08:23', '1', '2024-01-04 22:37:01', NULL, 0);
INSERT INTO `sys_dept` VALUES (4, 2, 2, '2,4', '北京分公司', '北京', 'GS0003', 2, NULL, NULL, NULL, '0', '1', '2024-01-04 22:36:50', '1', '2024-01-04 22:36:50', NULL, 0);
INSERT INTO `sys_dept` VALUES (1749431618629652481, 3, 3, '2,3,1749431618629652481', '技术研发部', '研发部', 'JSYF', 1, NULL, NULL, NULL, '0', '1', '2024-01-22 21:59:35', '1', '2024-01-22 21:59:35', NULL, 0);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL COMMENT '字典编码',
  `dict_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 'sys_base_status', '正常', '0', 1, 'blue', '0', '1', '2023-12-23 17:14:54', '1', '2023-01-17 18:16:46', '正常状态');
INSERT INTO `sys_dict_data` VALUES (2, 'sys_base_status', '禁用', '1', 2, 'red', '0', '1', '2023-12-23 17:14:54', '1', '2023-01-17 18:16:50', '停用状态');
INSERT INTO `sys_dict_data` VALUES (3, 'sys_base_sex', '男', '0', 1, 'blue', '0', '', NULL, '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (4, 'sys_base_sex', '女', '1', 2, 'pink', '0', '', NULL, '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (5, 'sys_menu_type', '目录', '0', 1, 'blue', '0', '', NULL, '1', '2024-01-03 09:46:07', NULL);
INSERT INTO `sys_dict_data` VALUES (6, 'sys_menu_type', '菜单', '1', 2, 'blue', '0', '', NULL, '1', '2024-01-03 09:46:13', NULL);
INSERT INTO `sys_dict_data` VALUES (7, 'sys_menu_type', '按钮', '2', 3, 'blue', '0', '', NULL, '1', '2024-01-03 09:46:18', NULL);
INSERT INTO `sys_dict_data` VALUES (9, 'sys_data_scope', '本部门及以下数据权限', '1', 0, 'blue', '0', '1', '2024-01-03 17:52:21', '1', '2024-01-03 18:00:19', NULL);
INSERT INTO `sys_dict_data` VALUES (10, 'sys_data_scope', '本部门数据权限', '2', 1, 'cyan', '0', '1', '2024-01-03 17:59:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (11, 'sys_data_scope', '用户自己的数据', '3', 3, 'blue', '0', '1', '2024-01-08 17:29:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (12, 'sys_job_exec_type', '自动执行', '0', 0, 'green', '0', '1', '2024-01-03 21:59:22', '1', '2024-01-03 22:00:55', NULL);
INSERT INTO `sys_dict_data` VALUES (13, 'sys_job_exec_type', '手动执行', '1', 1, 'blue', '0', '1', '2024-01-03 21:59:40', '1', '2024-01-03 22:01:01', NULL);
INSERT INTO `sys_dict_data` VALUES (15, 'sys_job_exec_status', '执行中', '0', 0, 'cyan', '0', '1', '2024-01-03 22:01:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (16, 'sys_job_exec_status', '执行成功', '1', 1, 'green', '0', '1', '2024-01-03 22:01:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (17, 'sys_job_exec_status', '执行失败', '2', 2, 'red', '0', '1', '2024-01-03 22:02:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (18, 'sys_base_nation', '汉族', '1', 1, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (19, 'sys_base_nation', '蒙古族', '2', 2, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (20, 'sys_base_nation', '回族', '3', 3, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (21, 'sys_base_nation', '藏族', '4', 4, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (22, 'sys_base_nation', '维吾尔族', '5', 5, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (23, 'sys_base_nation', '苗族', '6', 6, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (24, 'sys_base_nation', '彝族', '7', 7, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (25, 'sys_base_nation', '壮族', '8', 8, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (26, 'sys_base_nation', '布依族', '9', 9, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (27, 'sys_base_nation', '朝鲜族', '10', 10, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (28, 'sys_base_nation', '满族', '11', 11, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (29, 'sys_base_nation', '侗族', '12', 12, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (30, 'sys_base_nation', '瑶族', '13', 13, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (31, 'sys_base_nation', '白族', '14', 14, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (32, 'sys_base_nation', '土家族', '15', 15, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (33, 'sys_base_nation', '哈尼族', '16', 16, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (34, 'sys_base_nation', '哈萨克族', '17', 17, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (35, 'sys_base_nation', '傣族', '18', 18, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (36, 'sys_base_nation', '黎族', '19', 19, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (37, 'sys_base_nation', '傈僳族', '20', 20, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (38, 'sys_base_nation', '佤族', '21', 21, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (39, 'sys_base_nation', '畲族', '22', 22, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (40, 'sys_base_nation', '高山族', '23', 23, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (41, 'sys_base_nation', '拉祜族', '24', 24, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (42, 'sys_base_nation', '水族', '25', 25, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (43, 'sys_base_nation', '东乡族', '26', 26, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (44, 'sys_base_nation', '纳西族', '27', 27, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (45, 'sys_base_nation', '景颇族', '28', 28, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (46, 'sys_base_nation', '柯尔克孜族', '29', 29, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (47, 'sys_base_nation', '土族', '30', 30, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (48, 'sys_base_nation', '达斡尔族', '31', 31, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (49, 'sys_base_nation', '仫佬族', '32', 32, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (50, 'sys_base_nation', '羌族', '33', 33, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (51, 'sys_base_nation', '布朗族', '34', 34, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (52, 'sys_base_nation', '撒拉族', '35', 35, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (53, 'sys_base_nation', '毛难族', '36', 36, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (54, 'sys_base_nation', '仡佬族', '37', 37, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (55, 'sys_base_nation', '锡伯族', '38', 38, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (56, 'sys_base_nation', '阿昌族', '39', 39, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (57, 'sys_base_nation', '普米族', '40', 40, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (58, 'sys_base_nation', '塔吉克族', '41', 41, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (59, 'sys_base_nation', '怒族', '42', 42, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (60, 'sys_base_nation', '乌孜别克族', '43', 43, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (61, 'sys_base_nation', '俄罗斯族', '44', 44, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (62, 'sys_base_nation', '鄂温克族', '45', 45, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (63, 'sys_base_nation', '崩龙族', '46', 46, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (64, 'sys_base_nation', '保安族', '47', 47, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (65, 'sys_base_nation', '裕固族', '48', 48, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (66, 'sys_base_nation', '京族', '49', 49, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (67, 'sys_base_nation', '塔塔尔族', '50', 50, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (68, 'sys_base_nation', '独龙族', '51', 51, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (69, 'sys_base_nation', '鄂伦春族', '52', 52, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (70, 'sys_base_nation', '赫哲族', '53', 53, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (71, 'sys_base_nation', '门巴族', '54', 54, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (72, 'sys_base_nation', '珞巴族', '55', 55, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (73, 'sys_base_nation', '基诺族', '56', 56, 'blue', '0', '', '2024-01-05 00:00:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (74, 'sys_id_type', '身份证', '1', 1, 'blue', '0', '1', '2024-01-06 13:52:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (75, 'sys_id_type', '护照', '2', 2, 'blue', '0', '1', '2024-01-06 13:54:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (76, 'sys_culture_type', '小学', '0', 0, 'blue', '0', '1', '2024-01-06 13:56:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (77, 'sys_culture_type', '初中', '1', 1, 'blue', '0', '1', '2024-01-06 13:56:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (78, 'sys_culture_type', '高中', '2', 2, 'blue', '0', '1', '2024-01-06 13:56:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (79, 'sys_culture_type', '中专', '3', 3, 'blue', '0', '1', '2024-01-06 13:57:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (80, 'sys_culture_type', '大专', '4', 4, 'blue', '0', '1', '2024-01-06 13:57:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (81, 'sys_culture_type', '本科', '5', 5, 'blue', '0', '1', '2024-01-06 13:57:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (82, 'sys_culture_type', '硕士', '6', 6, 'blue', '0', '1', '2024-01-06 13:57:45', '1', '2024-01-06 13:57:53', NULL);
INSERT INTO `sys_dict_data` VALUES (83, 'sys_culture_type', '博士', '7', 7, 'blue', '0', '1', '2024-01-06 13:58:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (84, 'sys_base_political_outlook', '群众', '0', 0, 'blue', '0', '1', '2024-01-06 13:59:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (85, 'sys_base_political_outlook', '团员', '1', 1, 'blue', '0', '1', '2024-01-06 13:59:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (86, 'sys_base_political_outlook', '党员', '2', 2, 'blue', '0', '1', '2024-01-06 14:00:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1747075078795534338, 'sys_notice_type', '通知', '0', 0, 'blue', '0', '1', '2024-01-16 09:55:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1747075141437464577, 'sys_notice_type', '公告', '1', 1, 'cyan', '0', '1', '2024-01-16 09:55:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1747075779130949633, 'sys_notice_send_type', '全体人员', '0', 0, 'blue', '0', '1', '2024-01-16 09:58:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1747075862404661250, 'sys_notice_send_type', '所选部门人员', '1', 1, 'cyan', '0', '1', '2024-01-16 09:58:39', '1', '2024-01-16 13:47:12', NULL);
INSERT INTO `sys_dict_data` VALUES (1747075939898621954, 'sys_notice_send_type', '所选部门及以下部门', '2', 2, 'green', '0', '1', '2024-01-16 09:58:58', '1', '2024-01-16 13:47:18', NULL);
INSERT INTO `sys_dict_data` VALUES (1747075990318350337, 'sys_notice_send_type', '特定人员', '3', 3, 'purple', '1', '1', '2024-01-16 09:59:10', '1', '2024-01-19 11:32:06', '暂时搁置该功能');
INSERT INTO `sys_dict_data` VALUES (1747440962991005697, 'sys_login_status', '成功', '0', 0, 'green', '0', '1', '2024-01-17 10:09:26', '1', '2024-01-17 10:22:15', NULL);
INSERT INTO `sys_dict_data` VALUES (1747441011082895362, 'sys_login_status', '失败', '1', 1, 'red', '0', '1', '2024-01-17 10:09:38', '1', '2024-01-17 10:22:19', NULL);
INSERT INTO `sys_dict_data` VALUES (1747444459538890753, 'sys_operation_status', '正常', '0', 0, 'green', '0', '1', '2024-01-17 10:23:20', '1', '2024-01-17 10:24:02', NULL);
INSERT INTO `sys_dict_data` VALUES (1747444504367611905, 'sys_operation_status', '异常', '1', 1, 'red', '0', '1', '2024-01-17 10:23:30', '1', '2024-01-17 10:24:09', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL COMMENT '字典主键',
  `dict_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '字典类型 0系统 1业务',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典名称',
  `dict_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典key',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_key`(`dict_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '0', '系统通用状态', 'sys_base_status', '0', '1', '2023-12-26 09:19:00', '', NULL, '系统通用状态');
INSERT INTO `sys_dict_type` VALUES (2, '0', '系统通用性别', 'sys_base_sex', '0', '1', '2023-12-26 14:39:41', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (3, '0', '系统菜单类型', 'sys_menu_type', '0', '1', '2023-12-30 12:13:19', '1', '2024-01-03 14:42:35', NULL);
INSERT INTO `sys_dict_type` VALUES (5, '0', '系统数据权限范围', 'sys_data_scope', '0', '1', '2024-01-03 17:52:03', '', NULL, '用于角色配置');
INSERT INTO `sys_dict_type` VALUES (6, '0', '系统定时任务执行类型', 'sys_job_exec_type', '0', '1', '2024-01-03 21:58:49', '1', '2024-01-03 22:00:40', NULL);
INSERT INTO `sys_dict_type` VALUES (7, '0', '系统定时任务执行状态', 'sys_job_exec_status', '0', '1', '2024-01-03 22:01:23', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (8, '0', '民族', 'sys_base_nation', '0', '1', '2024-01-05 23:33:08', '1', '2024-01-06 13:56:16', NULL);
INSERT INTO `sys_dict_type` VALUES (9, '0', '身份证件类型', 'sys_id_type', '0', '1', '2024-01-06 13:52:21', '1', '2024-01-06 13:56:10', NULL);
INSERT INTO `sys_dict_type` VALUES (10, '0', '文化水平', 'sys_culture_type', '0', '1', '2024-01-06 13:56:03', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (11, '0', '政治面貌', 'sys_base_political_outlook', '0', '1', '2024-01-06 13:59:20', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1747074753795641346, '0', '系统通知公告类型', 'sys_notice_type', '0', '1', '2024-01-16 09:54:15', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1747075301903147010, '0', '系统通知公告范围', 'sys_notice_send_type', '0', '1', '2024-01-16 09:56:26', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1747440888701493250, '0', '系统登录状态', 'sys_login_status', '0', '1', '2024-01-17 10:09:08', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1747444394829168641, '0', '系统操作状态', 'sys_operation_status', '0', '1', '2024-01-17 10:23:04', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_file_storage
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_storage`;
CREATE TABLE `sys_file_storage`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件id',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件访问地址',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小，单位字节',
  `filename` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `original_filename` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始文件名',
  `base_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基础存储路径',
  `path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `ext` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件扩展名',
  `content_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MIME类型',
  `platform` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储平台',
  `th_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图访问路径',
  `th_filename` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图名称',
  `th_size` bigint NULL DEFAULT NULL COMMENT '缩略图大小，单位字节',
  `th_content_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图MIME类型',
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件所属对象id',
  `object_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件所属对象类型，例如用户头像，评价图片',
  `metadata` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文件元数据',
  `user_metadata` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文件用户元数据',
  `th_metadata` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '缩略图元数据',
  `th_user_metadata` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '缩略图用户元数据',
  `attr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '附加属性',
  `file_acl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件ACL',
  `th_file_acl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图文件ACL',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_storage
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `cron` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务表达式',
  `task_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务执行器的class',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '执行参数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '任务状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (23, '系统测试任务', '0 0/1 * * * ?', 'cn.lenmotion.donut.system.job.SystemTestJob', NULL, '1', '2024-01-03 21:44:20', '1', '2024-01-22 14:00:07', NULL, 0);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `id` bigint NOT NULL COMMENT 'id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '0自动执行 1手动执行',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0执行中 1执行成功，2执行失败',
  `time` bigint NULL DEFAULT 0 COMMENT '耗时(毫秒)',
  `error_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '错误日志',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务执行日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint NOT NULL COMMENT '访问ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL COMMENT '菜单ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面名称 全局唯一',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `current_active_menu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前激活的菜单。用于配置详情页时左侧激活的菜单路径',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `ignore_keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '是否忽略缓存（0不忽略 1忽略）',
  `hide_menu` tinyint(1) NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `hide_children_in_menu` tinyint(1) NULL DEFAULT 0 COMMENT '隐藏所有子菜单',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单图标',
  `order_no` int NULL DEFAULT 0 COMMENT '显示顺序',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `frame` tinyint(1) NULL DEFAULT 0 COMMENT '是否为外链（0否 1是）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '首页', 'Home', 0, '0', '/dashboard', NULL, '/dashboard/workbench', 'LAYOUT', 0, 0, 1, '0', NULL, 'bx:bx-home', 0, NULL, 0, '', NULL, '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2, '工作台', 'Workbench', 1, '1', 'workbench', '/dashboard', NULL, '/dashboard/workbench/index', 1, 0, 0, '0', NULL, NULL, 0, NULL, 0, '', NULL, '1', '2024-01-20 19:00:12', NULL, 0);
INSERT INTO `sys_menu` VALUES (3, '权限管理', 'Permission', 0, '0', '/permission', NULL, NULL, 'LAYOUT', 0, 0, 0, '0', NULL, 'ant-design:safety-outlined', 2, NULL, 0, '', NULL, '1', '2024-01-04 13:15:47', NULL, 0);
INSERT INTO `sys_menu` VALUES (4, '用户管理', 'UserManagement', 15, '1', 'userManagement', NULL, NULL, '/permission/user/index', 0, 0, 0, '0', 'system:user:list', NULL, 0, NULL, 0, '', NULL, '1', '2024-01-05 22:09:08', NULL, 0);
INSERT INTO `sys_menu` VALUES (5, '角色管理', 'RoleManagement', 3, '1', 'roleManagement', NULL, NULL, '/permission/role/index', 0, 0, 0, '0', 'system:role:list', NULL, 1, NULL, 0, '', NULL, '1', '2024-01-19 09:35:45', NULL, 0);
INSERT INTO `sys_menu` VALUES (6, '系统管理', 'System', 0, '0', '/system', NULL, NULL, 'LAYOUT', 0, 0, 0, '0', NULL, 'ant-design:setting-outlined', 3, NULL, 0, '', NULL, '1', '2024-01-04 13:15:53', NULL, 0);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', 'MenuManagement', 3, '1', 'menuManagement', NULL, NULL, '/permission/menu/index', 0, 0, 0, '0', 'system:menu:list', '', 0, NULL, 0, '', NULL, '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (9, '数据字典', 'DictManagement', 6, '1', 'dictManagement', NULL, NULL, '/sys/modules/dict/index', 0, 0, 0, '0', 'system:dict:list', NULL, 1, NULL, 0, '1', '2024-01-02 15:44:51', '1', '2024-01-18 18:06:34', NULL, 0);
INSERT INTO `sys_menu` VALUES (10, '系统设置', 'ConfigManagement', 6, '1', 'configManagement', NULL, NULL, '/sys/modules/config/index', 0, 0, 0, '0', 'system:config:list', NULL, 2, NULL, 0, '1', '2024-01-03 15:06:58', '1', '2024-01-19 09:44:12', NULL, 0);
INSERT INTO `sys_menu` VALUES (11, '定时任务', 'JobManagement', 6, '1', 'jobManagement', NULL, NULL, '/sys/modules/job/index', 1, 0, 0, '0', 'system:job:list', NULL, 3, NULL, 0, '1', '2024-01-03 20:09:21', '1', '2024-01-18 18:06:58', NULL, 0);
INSERT INTO `sys_menu` VALUES (12, '系统日志', 'SystemLog', 1747515992408047617, '0', '/systemLog', NULL, NULL, 'LAYOUT', 1, 0, 0, '0', NULL, NULL, 3, NULL, 0, '1', '2024-01-03 22:38:36', '1', '2024-01-19 09:40:41', NULL, 0);
INSERT INTO `sys_menu` VALUES (13, '登录日志', 'LoginLogManagement', 12, '1', 'loginLog', NULL, NULL, '/sys/modules/loginLog/index', 0, 0, 0, '0', NULL, NULL, 0, NULL, 0, '1', '2024-01-03 22:40:30', '1', '2024-01-19 09:46:11', NULL, 0);
INSERT INTO `sys_menu` VALUES (14, '操作日志', 'OperationLogManagement', 12, '1', 'operationLog', NULL, NULL, '/sys/modules/operationLog/index', 0, 0, 0, '0', NULL, NULL, 1, NULL, 0, '1', '2024-01-03 22:42:41', '1', '2024-01-19 09:46:14', NULL, 0);
INSERT INTO `sys_menu` VALUES (15, '组织架构', 'Organ', 0, '0', '/organ', NULL, NULL, 'LAYOUT', 1, 0, 0, '0', NULL, 'ant-design:cluster-outlined', 1, NULL, 0, '1', '2024-01-04 13:34:32', '1', '2024-01-11 22:10:14', NULL, 0);
INSERT INTO `sys_menu` VALUES (16, '部门管理', 'DeptManagement', 15, '1', 'deptManagement', NULL, NULL, '/permission/dept/index', 1, 0, 0, '0', 'system:dept:list', NULL, 1, NULL, 0, '1', '2024-01-04 13:35:13', '1', '2024-01-18 17:51:14', NULL, 0);
INSERT INTO `sys_menu` VALUES (17, '岗位管理', 'PostManagement', 15, '1', 'postManagement', NULL, NULL, '/permission/post/index', 1, 0, 0, '0', 'system:post:list', NULL, 2, NULL, 0, '1', '2024-01-05 20:37:34', '1', '2024-01-18 17:51:43', NULL, 0);
INSERT INTO `sys_menu` VALUES (18, '用户详情', 'UserDetail', 4, '1', 'userDetail/:id', NULL, NULL, '/permission/user/detail/index', 1, 1, 0, '0', 'system:user:detail', NULL, 1, NULL, 0, '1', '2024-01-07 22:02:42', '1', '2024-01-18 17:41:08', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747074132006850561, '通知公告', 'NoticeManagement', 6, '1', 'noticeManagement', NULL, NULL, '/sys/modules/notice/index', 1, 0, 0, '0', 'system:notice:list', NULL, 4, NULL, 0, '1', '2024-01-16 09:51:47', '1', '2024-01-18 18:07:09', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747148469133094913, '文件管理', 'FileManagement', 6, '1', 'fileManagement', NULL, NULL, '/sys/modules/fileStorage/index', 1, 0, 0, '0', 'system:fileStorage:list', NULL, 5, NULL, 0, '1', '2024-01-16 14:47:10', '1', '2024-01-18 18:07:32', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747177110302846977, '个人信息设置', 'UserSettingManagement', 0, '1', 'userSetting', NULL, NULL, '/sys/modules/userSetting/index', 0, 1, 0, '0', NULL, NULL, 99, NULL, 0, '1', '2024-01-16 16:40:59', '1', '2024-01-19 09:44:19', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747515992408047617, '系统监控', 'Monitor', 0, '0', '/monitor', NULL, NULL, 'LAYOUT', 1, 0, 0, '0', NULL, 'ant-design:fund-projection-screen-outlined', 4, NULL, 0, '1', '2024-01-17 15:07:35', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747516518310854657, '服务器监控', 'ServerMonitor', 1747515992408047617, '1', 'serverInfo', NULL, NULL, '/monitor/server/index', 1, 0, 0, '0', 'monitor:server:info', NULL, 0, NULL, 0, '1', '2024-01-17 15:09:40', '1', '2024-01-18 22:00:46', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747516759445585921, '缓存监控', 'RedisMonitor', 1747515992408047617, '1', 'redisInfo', NULL, NULL, '/monitor/redis/index', 1, 0, 0, '0', 'monitor:redis:info', NULL, 1, NULL, 0, '1', '2024-01-17 15:10:37', '1', '2024-01-18 23:09:31', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747917935722872834, '保存用户', NULL, 4, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:user:save', NULL, 2, NULL, 0, '1', '2024-01-18 17:44:45', '1', '2024-01-19 09:14:04', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747918077913972738, '导出用户', NULL, 4, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:user:export', NULL, 4, NULL, 0, '1', '2024-01-18 17:45:19', '1', '2024-01-19 09:14:11', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747918178480799745, '导入用户', NULL, 4, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:user:import', NULL, 5, NULL, 0, '1', '2024-01-18 17:45:43', '1', '2024-01-19 09:14:20', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747918693755240449, '删除用户', NULL, 4, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:user:remove', NULL, 6, NULL, 0, '1', '2024-01-18 17:47:46', '1', '2024-01-19 09:14:29', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747918883321004034, '保存部门', NULL, 16, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dept:save', '', 0, NULL, 0, '1', '2024-01-18 17:48:31', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747919006339940354, '删除部门', NULL, 16, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dept:remove', NULL, 2, NULL, 0, '1', '2024-01-18 17:49:01', '1', '2024-01-19 09:15:50', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747919428949622785, '保存岗位', NULL, 17, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:post:save', '', 0, NULL, 0, '1', '2024-01-18 17:50:41', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747919507039174657, '删除岗位', NULL, 17, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:post:remove', NULL, 2, NULL, 0, '1', '2024-01-18 17:51:00', '1', '2024-01-19 09:24:55', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747920412488744962, '保存菜单', NULL, 7, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:menu:save', '', 0, NULL, 0, '1', '2024-01-18 17:54:36', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747920515844784130, '删除菜单', NULL, 7, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:menu:remove', '', 1, NULL, 0, '1', '2024-01-18 17:55:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747921301937045506, '保存角色', NULL, 5, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:role:save', '', 0, NULL, 0, '1', '2024-01-18 17:58:08', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747921723582038017, '删除角色', NULL, 5, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:role:remove', '', 1, NULL, 0, '1', '2024-01-18 17:59:48', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747923834810122241, '保存字典', NULL, 9, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dict:save', '', 0, NULL, 0, '1', '2024-01-18 18:08:12', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747923976258830338, '删除字典', NULL, 9, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dict:remove', NULL, 2, NULL, 0, '1', '2024-01-18 18:08:45', '1', '2024-01-19 09:27:06', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747924189681795073, '保存设置', NULL, 10, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:config:save', NULL, 0, NULL, 0, '1', '2024-01-18 18:09:36', '1', '2024-01-18 18:10:15', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747924307512377346, '删除设置', NULL, 10, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:config:remove', '', 1, NULL, 0, '1', '2024-01-18 18:10:04', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747975069705027586, '保存任务', NULL, 11, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:job:save', '', 0, NULL, 0, '1', '2024-01-18 21:31:47', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747975171043606530, '执行任务', NULL, 11, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:job:status', '', 1, NULL, 0, '1', '2024-01-18 21:32:11', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747977617916674049, '删除任务', NULL, 11, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:job:remove', NULL, 3, NULL, 0, '1', '2024-01-18 21:41:55', '1', '2024-01-22 22:01:12', NULL, 0);
INSERT INTO `sys_menu` VALUES (1747996340529352705, '更新角色状态', NULL, 5, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:role:status', '', 0, NULL, 0, '1', '2024-01-18 22:56:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1747996508121157633, '保存公告', NULL, 1747074132006850561, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:notice:save', '', 0, NULL, 0, '1', '2024-01-18 22:56:58', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748152015395745794, '更新状态', NULL, 4, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:user:status', '', 3, NULL, 0, '1', '2024-01-19 09:14:54', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748152219645767681, '更新状态', NULL, 16, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dept:status', '', 1, NULL, 0, '1', '2024-01-19 09:15:43', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748154505751162882, '更新状态', NULL, 17, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:post:status', '', 1, NULL, 0, '1', '2024-01-19 09:24:48', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748155012431474690, '更新状态', NULL, 9, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:dict:status', '', 1, NULL, 0, '1', '2024-01-19 09:26:49', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748155618076389377, '删除公告', NULL, 1747074132006850561, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:notice:remove', NULL, 2, NULL, 0, '1', '2024-01-19 09:29:13', '1', '2024-01-19 09:29:40', NULL, 0);
INSERT INTO `sys_menu` VALUES (1748155709478662146, '发布撤回', NULL, 1747074132006850561, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:notice:status', '', 1, NULL, 0, '1', '2024-01-19 09:29:35', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748155869352947713, '导出日志', NULL, 13, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:loginLog:export', '', 0, NULL, 0, '1', '2024-01-19 09:30:13', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1748664655401738242, '我的通知公告', 'UserNoticeList', 0, '1', 'notice/mineList', NULL, NULL, '/sys/modules/notice/userNoticeList', 0, 1, 0, '0', NULL, NULL, 98, NULL, 0, '1', '2024-01-20 19:11:57', '1', '2024-01-20 19:12:25', NULL, 0);
INSERT INTO `sys_menu` VALUES (1749432002051952641, '执行任务', NULL, 11, '2', '', NULL, NULL, '', 1, 0, 0, '0', 'system:job:exec', '', 2, NULL, 0, '1', '2024-01-22 22:01:07', '', NULL, '', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_send_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告发送类型（0全体人员 1本部门人员 2本部门及以下部门 3特定人员）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `read_num` int NULL DEFAULT 0 COMMENT '阅读量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
INSERT INTO `sys_notice` VALUES (1748661387233615874, '关于Donut Admin上线的公告', '1', '0', 0x3C703EE5B08AE695ACE79A84E59BA2E9989FE68890E59198EFBC8C3C2F703E0A3C703EE68891E4BBACE5BE88E9AB98E585B4E59CB0E5AEA3E5B883EFBC8CE68891E4BBACE79A84E585A8E696B0E7AEA1E79086E5B9B3E58FB020446F6E75742041646D696E20E5B7B2E7BB8FE68890E58A9FE4B88AE7BABFEFBC81446F6E75742041646D696E20E5B086E68890E4B8BAE68891E4BBACE5B7A5E4BD9CE4B8ADE4B88DE58FAFE68896E7BCBAE79A84E4B880E983A8E58886EFBC8CE4B8BAE68891E4BBACE68F90E4BE9BE69BB4E9AB98E69588E38081E69BB4E699BAE883BDE79A84E7AEA1E79086E4BD93E9AA8CE38082E4BBA5E4B88BE698AFE4B880E4BA9BE585B3E994AEE79A84E58A9FE883BDE5928CE4BC98E58ABFEFBC9A3C2F703E0A3C6F6C3E0A3C6C693E0A3C703E3C7374726F6E673EE79BB4E8A782E79A84E4BBAAE8A1A8E69DBFEFBC9A3C2F7374726F6E673E20446F6E75742041646D696E20E68F90E4BE9BE4BA86E79BB4E8A782E38081E58FAFE5AE9AE588B6E79A84E4BBAAE8A1A8E69DBFEFBC8CE8AEA9E682A8E58FAFE4BBA5E4B880E79BAEE4BA86E784B6E59CB0E69FA5E79C8BE585B3E994AEE695B0E68DAEE5928CE7BB9FE8AEA1E4BFA1E681AFE38082E4BB8EE5B7A5E4BD9CE6A682E8A788E588B0E4BBBBE58AA1E8BF9BE5BAA6EFBC8CE68980E69C89E79A84E4BFA1E681AFE983BDE6B187E8819AE59CA8E4B880E4B8AAE59CB0E696B9E380823C2F703E0A3C2F6C693E0A3C6C693E0A3C703E3C7374726F6E673EE781B5E6B4BBE79A84E69D83E99990E7AEA1E79086EFBC9A3C2F7374726F6E673E20E4B88DE5908CE8A792E889B2E5928CE59BA2E9989FE68890E59198E69C89E4B88DE5908CE79A84E99C80E6B182EFBC8C446F6E75742041646D696E20E58581E8AEB8E682A8E7BB86E7B292E5BAA6E59CB0E8AEBEE7BDAEE69D83E99990EFBC8CE7A1AEE4BF9DE6AF8FE4B8AAE4BABAE983BDE883BDE5A49FE8AEBFE997AEE5B9B6E689A7E8A18CE4BB96E4BBACE99C80E8A681E79A84E6938DE4BD9CE380823C2F703E0A3C2F6C693E0A3C6C693E0A3C703E3C7374726F6E673EE4BBBBE58AA1E5928CE9A1B9E79BAEE7AEA1E79086EFBC9A3C2F7374726F6E673E20446F6E75742041646D696E20E99B86E68890E4BA86E5BCBAE5A4A7E79A84E4BBBBE58AA1E5928CE9A1B9E79BAEE7AEA1E79086E5B7A5E585B7EFBC8CE4BDBFE682A8E883BDE5A49FE69BB4E8BDBBE69DBEE59CB0E8B79FE8B8AAE5928CE58D8FE8B083E5B7A5E4BD9CE38082E58886E9858DE4BBBBE58AA1E38081E8AEBEE7BDAEE688AAE6ADA2E697A5E69C9FE38081E8BFBDE8B8AAE8BF9BE5BAA6EFBC8CE4B880E58887E983BDE59CA8E682A8E79A84E68E8CE68FA1E4B98BE4B8ADE380823C2F703E0A3C2F6C693E0A3C6C693E0A3C703E3C7374726F6E673EE5AE9EE697B6E9809AE79FA5E5928CE68F90E98692EFBC9A3C2F7374726F6E673E20E4B88DE5868DE99499E8BF87E9878DE8A681E79A84E58F98E69BB4E68896E688AAE6ADA2E697A5E69C9FEFBC81446F6E75742041646D696E20E5B086E9809AE8BF87E5AE9EE697B6E9809AE79FA5E5928CE68F90E98692E7A1AEE4BF9DE682A8E5A78BE7BB88E4BA86E8A7A3E9A1B9E79BAEE5928CE4BBBBE58AA1E79A84E69C80E696B0E78AB6E68081E380823C2F703E0A3C2F6C693E0A3C6C693E0A3C703E3C7374726F6E673EE7AE80E58C96E79A84E59BA2E9989FE58D8FE4BD9CEFBC9A3C2F7374726F6E673E20E68F90E9AB98E59BA2E9989FE58D8FE4BD9CE69588E78E87EFBC8CE9809AE8BF8720446F6E75742041646D696E20E4B8ADE79A84E8AEA8E8AEBAE69DBFE38081E69687E4BBB6E585B1E4BAABE5928CE5AE9EE697B6E58D8FE4BD9CE58A9FE883BDEFBC8CE8BDBBE69DBEE8BF9BE8A18CE4BFA1E681AFE4BAA4E6B581E5928CE585B1E4BAABE380823C2F703E0A3C2F6C693E0A3C2F6F6C3E0A3C703EE8AFB7E7AB8BE58DB3E799BBE5BD95203C6120687265663D2268747470733A2F2F646F6E75742D61646D696E2E636F6D2F22207461726765743D225F6E6577223E446F6E75742041646D696E3C2F613E20E5BC80E5A78BE4BD93E9AA8CE8BF99E4B880E696B0E79A84E7AEA1E79086E5B7A5E585B7E38082E68891E4BBACE79BB8E4BFA1EFBC8C446F6E75742041646D696E20E5B086E4B8BAE682A8E79A84E5B7A5E4BD9CE5B8A6E69DA5E69BB4E5A4A7E79A84E4BEBFE588A9E5928CE69588E78E87E380823C2F703E0A3C703EE5A682E69E9CE682A8E69C89E4BBBBE4BD95E79691E997AEE68896E99C80E8A681E5B8AEE58AA9EFBC8CE58FAFE4BBA5E99A8FE697B6E88194E7B3BBE68891E4BBACE79A84E68A80E69CAFE694AFE68C81E59BA2E9989FE38082E6849FE8B0A2E682A8E79A84E88090E5BF83E7AD89E5BE85E5928CE5AFB9E68891E4BBACE5B7A5E4BD9CE79A84E694AFE68C81EFBC813C2F703E0A3C703EE69C9FE5BE85E682A8E59CA820446F6E75742041646D696E20E4B8ADE5BAA6E8BF87E68489E5BFABE79A84E7AEA1E79086E697B6E58589EFBC813C2F703E0A3C703EE8B0A2E8B0A23C2F703E, '0', '2024-01-22 16:10:43', 1, '1', '2024-01-20 18:58:58', '1', '2024-01-22 16:10:43', NULL);

-- ----------------------------
-- Table structure for sys_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_read`;
CREATE TABLE `sys_notice_read`  (
  `id` bigint NOT NULL COMMENT '公告ID',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `notice_id` bigint NOT NULL COMMENT '通知id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告阅读表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_notice_send_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_send_relation`;
CREATE TABLE `sys_notice_send_relation`  (
  `id` bigint NOT NULL COMMENT '公告ID',
  `notice_id` bigint NOT NULL COMMENT '通知id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门id',
  `dept_ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门层级',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告发送关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_send_relation
-- ----------------------------
INSERT INTO `sys_notice_send_relation` VALUES (1748351170402856962, 1747133278391574530, NULL, 2, NULL, NULL);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint NOT NULL COMMENT '日志主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '操作人员',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'ip地址',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `json_result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回参数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误消息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL COMMENT '岗位ID',
  `post_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '岗位类型 0全局岗位 1部门岗位',
  `dept_id` bigint NULL DEFAULT 0 COMMENT '部门id',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `order_no` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (11, '0', 0, '001', '董事长', 1, '0', '1', '2024-01-05 21:56:50', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (12, '0', 3, 'cd001', '成都分公司的岗位', 1, '0', '1', '2024-01-08 22:08:14', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'SUPER', 1, '3', '0', '1', '2024-01-03 18:04:41', '', NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `half_menu` tinyint(1) NULL DEFAULT 0 COMMENT '是否是半菜单',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_menu_unique_index`(`menu_id` ASC, `role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户编号',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `post_id` bigint NULL DEFAULT NULL COMMENT '关联岗位',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `nation` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '民族',
  `id_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证类型',
  `id_card` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证 - 做加密',
  `culture_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文化程度',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '住址',
  `entry_date` datetime NULL DEFAULT NULL COMMENT '入职时间',
  `political_outlook` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `quick_nav` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快捷导航，首页的快捷菜单id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 2, 'U01', 'admin', '超级管理员', NULL, 11, 'lenmotion@sina.com', '18001010101', '0', 25, 'http://localhost:9000/2024-01-20/65abd255db9add95652a070a.jpeg', 'c7084fa75f5d32685e89f7490f04efb160511c652c72a692135077b1c9092ec1', '1', NULL, NULL, '4', 'ces', NULL, NULL, '0', '127.0.0.1', '2024-01-22 22:17:29', '', '', NULL, '1', '2024-01-22 22:22:19', NULL, '0');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  `default_dept` tinyint(1) NULL DEFAULT 0 COMMENT '默认部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户额外任职关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_role_unique_index`(`user_id` ASC, `role_id` ASC) USING BTREE COMMENT '用户与角色的唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1749360733059801089, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
