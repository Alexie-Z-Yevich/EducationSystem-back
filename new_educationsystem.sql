/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : new_educationsystem

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 19/07/2023 22:50:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stu_message
-- ----------------------------
DROP TABLE IF EXISTS `stu_message`;
CREATE TABLE `stu_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学号/工号',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `class_id` int NOT NULL COMMENT '班级号（教师/管理班级号为 0 ）',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `enrollment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '入学年份',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `telephone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `id_card` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `birth` datetime NULL DEFAULT NULL COMMENT '出生日期',
  `nation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '民族',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `role` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色权限（#114514# 仅学生操作；#114515 教师操作；#114516# 管理员操作 ）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stu_message
-- ----------------------------
INSERT INTO `stu_message` VALUES (1, 'admin', 'admin', 0, '$2a$10$9gYDQGeXaHHHdqrOrkJeheM.Q3ukhdG/jZiNpamfS4nsPlBKNOMVK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '#114516#');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  `created` datetime NOT NULL,
  `updated` datetime NULL DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '学生管理', '', 'sys:manage', '', 0, 'Expand', 1, '2021-01-15 18:58:18', '2021-01-15 18:58:20', 1);
INSERT INTO `sys_menu` VALUES (2, 0, '任务管理', '', 'sys:tools', NULL, 0, 'Tools', 2, '2021-01-15 19:06:11', '2022-07-10 21:51:32', 1);
INSERT INTO `sys_menu` VALUES (3, 0, '考试模式', '', 'sys:tools', NULL, 0, 'Tools', 3, '2021-01-15 19:06:11', '2022-07-10 21:51:32', 1);
INSERT INTO `sys_menu` VALUES (8, 0, '系统管理', '', 'sys:manage', '', 0, 'Expand', 4, '2021-01-15 18:58:18', '2021-01-15 18:58:20', 1);
INSERT INTO `sys_menu` VALUES (9, 0, '作业详情', '', 'task:control', NULL, 0, 'Tools', 5, '2022-07-21 00:29:46', '2022-07-21 00:29:50', 1);
INSERT INTO `sys_menu` VALUES (100, 1, '学生信息', '/stu/messages', 'sys:user:list', 'stu/Message', 1, 'Avatar', 1, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (101, 1, '学生录入', '/stu/push', 'sys:menu:list', 'stu/Push', 1, 'Edit', 2, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (201, 2, '任务录入', '/task/build', 'sys:dict:list', 'task/Build', 1, 'FolderAdd', 1, '2021-01-15 19:07:18', '2021-01-18 16:32:13', 1);
INSERT INTO `sys_menu` VALUES (202, 2, '任务总览', '/task/all', 'task:all', 'task/All', 1, 'Files', 2, '2022-07-10 21:50:28', '2022-07-10 21:50:31', 1);
INSERT INTO `sys_menu` VALUES (203, 2, '任务发布', '/task/push', 'task:push', 'task/Push', 1, 'FolderChecked', 3, '2022-07-10 21:48:56', '2022-07-10 21:48:59', 1);
INSERT INTO `sys_menu` VALUES (801, 8, '权限管理', '/sys/users', 'sys:user:list', 'sys/User', 1, 'Avatar', 1, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (901, 9, '任务内容', '/stu/classes', 'task:description', 'task/Message', 1, 'Flag', 1, '2022-07-21 00:30:33', '2022-07-21 00:30:35', 1);
INSERT INTO `sys_menu` VALUES (902, 9, '考试内容', '/stu/classes', 'task:description', 'task/Message', 2, 'Flag', 1, '2022-07-21 00:30:33', '2022-07-21 00:30:35', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限（#114514# 仅学生操作；#114515 教师操作；#114516# 管理员操作 ）',
  `menu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (171, '#114516#', 9);
INSERT INTO `sys_role_menu` VALUES (172, '#114516#', 901);
INSERT INTO `sys_role_menu` VALUES (173, '#114516#', 902);

SET FOREIGN_KEY_CHECKS = 1;
