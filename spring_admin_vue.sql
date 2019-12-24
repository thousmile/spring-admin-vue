/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql-8
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : spring_admin_vue

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-12-11 23:51:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sys_department
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_department`;
CREATE TABLE `tb_sys_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` int(11) NOT NULL COMMENT '上级部门',
  `level` tinyint(4) NOT NULL COMMENT '部门排序',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门描述',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 部门表';

-- ----------------------------
-- Records of tb_sys_department
-- ----------------------------
INSERT INTO `tb_sys_department` VALUES ('1', '阿里巴巴集团', '0', '0', '阿里巴巴集团', '2018-11-15 03:23:13', '2018-11-15 03:23:13');
INSERT INTO `tb_sys_department` VALUES ('2', '阿里云', '1', '1', '阿里云科技', '2018-11-15 03:23:15', '2018-11-15 03:23:15');
INSERT INTO `tb_sys_department` VALUES ('3', '事业部门', '2', '1', '【阿里云】事业部门', '2018-10-23 04:50:33', '2018-11-17 16:21:56');
INSERT INTO `tb_sys_department` VALUES ('4', '开发部门', '2', '2', '【阿里云】开发部门', '2018-10-23 04:50:11', '2018-10-23 04:50:11');
INSERT INTO `tb_sys_department` VALUES ('5', '安全部门', '2', '3', '【阿里云】安全部门', '2018-10-23 04:50:13', '2018-11-17 16:21:45');
INSERT INTO `tb_sys_department` VALUES ('6', '淘宝网', '1', '2', '淘宝网络科技', '2018-11-15 03:23:26', '2018-11-15 03:23:26');
INSERT INTO `tb_sys_department` VALUES ('8', '客服部门', '6', '1', '【淘宝网】客服部门', '2018-11-15 03:24:11', '2018-11-17 16:22:13');
INSERT INTO `tb_sys_department` VALUES ('9', '菜鸟网络', '1', '3', '菜鸟网络', '2018-10-23 12:53:01', '2018-10-31 09:28:28');

-- ----------------------------
-- Table structure for tb_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_permission`;
CREATE TABLE `tb_sys_permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限唯一ID',
  `parent_id` int(11) NOT NULL COMMENT '上级ID',
  `resources` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限资源 ',
  `title` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称',
  `icon` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源图标',
  `type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型，menu或者button',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`pid`),
  UNIQUE KEY `t_resources` (`resources`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 权限表';

-- ----------------------------
-- Records of tb_sys_permission
-- ----------------------------
INSERT INTO `tb_sys_permission` VALUES ('1', '0', 'pre', '权限设置', 'pre_admin', 'menu', '权限设置', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('2', '0', 'shop', '店铺管理', 'shop_set', 'menu', '店铺管理', '2018-11-17 11:21:31', '2019-11-19 19:53:31');
INSERT INTO `tb_sys_permission` VALUES ('5', '1', 'pre_perm', '权限管理', 'pre_perm_admin', 'menu', '权限管理', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('6', '1', 'pre_user', '用户管理', 'pre_user_admin', 'menu', '用户管理', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('7', '1', 'pre_role', '角色管理', 'pre_role_admin', 'menu', '角色管理', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('8', '1', 'pre_dept', '部门管理', 'pre_dept_admin', 'menu', '部门管理', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('13', '5', 'pre_perm:new', '新增权限', '', 'button', '新增权限', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('14', '5', 'pre_perm:delete', '删除权限', '', 'button', '删除权限', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('15', '5', 'pre_perm:update', '修改权限', '', 'button', '修改权限', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('16', '5', 'pre_perm:view', '查看权限', '', 'button', '查看权限', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('17', '6', 'pre_user:new', '新增用户', '', 'button', '新增角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('18', '6', 'pre_user:delete', '删除用户', '', 'button', '删除角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('19', '6', 'pre_user:update', '修改用户', '', 'button', '修改角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('20', '6', 'pre_user:view', '查看用户', '', 'button', '查看角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('21', '7', 'pre_role:new', '新增角色', '', 'button', '新增角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('22', '7', 'pre_role:delete', '删除角色', '', 'button', '删除角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('23', '7', 'pre_role:update', '修改角色', '', 'button', '修改角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('24', '7', 'pre_role:view', '查看角色', '', 'button', '查看角色', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('25', '8', 'pre_dept:new', '新增部门', '', 'button', '新增部门', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('26', '8', 'pre_dept:delete', '删除部门', '', 'button', '删除部门', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('27', '8', 'pre_dept:update', '修改部门', '', 'button', '修改部门', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('28', '8', 'pre_dept:view', '查看部门', '', 'button', '查看部门', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('33', '2', 'shop_order', '订单管理', 'shop_order', 'menu', '订单管理', '2019-11-19 21:09:11', '2019-11-19 21:09:11');
INSERT INTO `tb_sys_permission` VALUES ('35', '2', 'shop_recipe', '菜谱管理', 'shop_recipe', 'menu', '菜谱管理', '2019-11-19 21:00:18', '2019-11-19 21:00:18');
INSERT INTO `tb_sys_permission` VALUES ('36', '2', 'shop_dining_table', '包厢管理', 'shop_dining_table', 'menu', '包厢管理', '2019-11-19 21:00:31', '2019-11-19 21:00:31');
INSERT INTO `tb_sys_permission` VALUES ('38', '0', 'test', '测试权限', 'test', 'menu', '测试权限', '2018-11-18 12:24:32', '2018-11-18 12:24:32');
INSERT INTO `tb_sys_permission` VALUES ('39', '38', 'test_haha', '测试权限二', 'twqeqw', 'menu', '测试权限二', '2018-11-18 12:32:24', '2018-11-18 12:32:24');
INSERT INTO `tb_sys_permission` VALUES ('40', '39', 'testwqeqwewqe', '测试按钮', 'testwqeqwewqe', 'button', '测试按钮', '2018-11-18 12:33:06', '2018-11-18 12:33:06');
INSERT INTO `tb_sys_permission` VALUES ('41', '36', 'shop_dining_table:new', '新增包厢', '', 'button', '新增包厢', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('42', '36', 'shop_dining_table:delete', '删除包厢', '', 'button', '删除包厢', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('43', '36', 'shop_dining_table:update', '修改包厢', '', 'button', '修改包厢', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('44', '36', 'shop_dining_table:view', '查看包厢', '', 'button', '查看包厢', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('45', '35', 'shop_recipe:new', '新增菜谱', '', 'button', '新增菜谱', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('46', '35', 'shop_recipe:delete', '删除菜谱', '', 'button', '删除菜谱', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('47', '35', 'shop_recipe:update', '修改菜谱', '', 'button', '修改菜谱', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('48', '35', 'shop_recipe:view', '查看菜谱', '', 'button', '查看菜谱', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('49', '33', 'shop_order:new', '新增订单', '', 'button', '新增订单', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('50', '33', 'shop_order:delete', '删除订单', '', 'button', '删除订单', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('51', '33', 'shop_order:update', '修改订单', '', 'button', '修改订单', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('52', '33', 'shop_order:view', '查看订单', '', 'button', '查看订单', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('53', '2', 'shop_create_order', '创建订单', 'shop_create_order', 'menu', '创建订单', '2019-11-20 12:00:59', '2019-11-20 12:00:59');
INSERT INTO `tb_sys_permission` VALUES ('54', '2', 'shop_member', '会员管理', 'shop_member', 'menu', '会员管理', '2019-11-20 16:46:30', '2019-11-20 16:46:30');
INSERT INTO `tb_sys_permission` VALUES ('55', '54', 'shop_member:new', '新增会员', '', 'button', '新增会员', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('56', '54', 'shop_member:delete', '删除会员', '', 'button', '删除会员', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('57', '54', 'shop_member:update', '修改会员', '', 'button', '修改会员', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('58', '54', 'shop_member:view', '查看会员', '', 'button', '查看会员', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('59', '2', 'shop_report', '报表管理', 'shop_report', 'menu', '报表管理', '2019-11-22 13:07:19', '2019-11-22 13:07:19');
INSERT INTO `tb_sys_permission` VALUES ('60', '2', 'shop_preordain', '预定管理', 'shop_preordain', 'menu', '预定管理', '2019-11-22 14:22:32', '2019-11-22 14:22:43');
INSERT INTO `tb_sys_permission` VALUES ('65', '60', 'shop_preordain:new', '新增预定', '', 'button', '新增预定', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('66', '60', 'shop_preordain:delete', '删除预定', '', 'button', '删除预定', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('67', '60', 'shop_preordain:update', '修改预定', '', 'button', '修改预定', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO `tb_sys_permission` VALUES ('68', '60', 'shop_preordain:view', '查看预定', '', 'button', '查看预定', '2018-11-17 11:20:54', '2018-11-17 11:20:54');

-- ----------------------------
-- Table structure for tb_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role`;
CREATE TABLE `tb_sys_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
  `role_name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 角色表';

-- ----------------------------
-- Records of tb_sys_role
-- ----------------------------
INSERT INTO `tb_sys_role` VALUES ('1', 'ROLE_ROOT', '超级管理员', '2018-10-23 12:32:13', '2018-10-23 12:32:10');
INSERT INTO `tb_sys_role` VALUES ('2', 'ROLE_ADMIN', '管理员', '2018-10-23 12:32:31', '2018-10-23 12:32:29');
INSERT INTO `tb_sys_role` VALUES ('3', 'ROLE_WAITER', '服务员', '2018-10-23 12:32:52', '2019-11-19 19:56:21');

-- ----------------------------
-- Table structure for tb_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_permission`;
CREATE TABLE `tb_sys_role_permission` (
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 角色和权限表';

-- ----------------------------
-- Records of tb_sys_role_permission
-- ----------------------------
INSERT INTO `tb_sys_role_permission` VALUES ('16', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('1', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('20', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('5', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('6', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('7', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('24', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('8', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('27', '10');
INSERT INTO `tb_sys_role_permission` VALUES ('16', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('32', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('1', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('20', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('4', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('5', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('6', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('7', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('24', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('8', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('11', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('28', '6');
INSERT INTO `tb_sys_role_permission` VALUES ('16', '9');
INSERT INTO `tb_sys_role_permission` VALUES ('1', '9');
INSERT INTO `tb_sys_role_permission` VALUES ('20', '9');
INSERT INTO `tb_sys_role_permission` VALUES ('5', '9');
INSERT INTO `tb_sys_role_permission` VALUES ('6', '9');
INSERT INTO `tb_sys_role_permission` VALUES ('1', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('5', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('6', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('7', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('8', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('13', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('14', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('15', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('16', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('17', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('18', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('19', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('20', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('21', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('22', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('23', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('24', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('25', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('26', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('27', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('28', '4');
INSERT INTO `tb_sys_role_permission` VALUES ('33', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('2', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('35', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('36', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('41', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('42', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('43', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('44', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('45', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('46', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('47', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('48', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('49', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('50', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('51', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('52', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('53', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('54', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('55', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('56', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('57', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('58', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('59', '5');
INSERT INTO `tb_sys_role_permission` VALUES ('1', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('2', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('5', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('6', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('7', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('8', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('13', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('14', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('15', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('16', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('17', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('18', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('19', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('20', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('21', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('22', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('23', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('24', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('25', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('26', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('27', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('28', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('33', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('35', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('36', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('41', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('42', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('43', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('44', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('45', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('46', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('47', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('48', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('49', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('50', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('51', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('52', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('53', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('54', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('55', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('56', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('57', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('58', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('59', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('60', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('65', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('66', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('67', '3');
INSERT INTO `tb_sys_role_permission` VALUES ('68', '3');

-- ----------------------------
-- Table structure for tb_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user` (
  `uid` int(200) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `avatar` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `email` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `nickname` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `password` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `gender` tinyint(4) NOT NULL COMMENT '性别[ 0.女  1.男  2.未知]',
  `birthday` date NOT NULL COMMENT '生日',
  `status` tinyint(4) NOT NULL COMMENT '状态 【0.禁用 1.正常 2.被删除】',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `last_update_time` datetime NOT NULL COMMENT '修改时间',
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UK_6i5ixxulo5s2i7qoksp54tgwl_username` (`username`) USING BTREE,
  UNIQUE KEY `UK_ulo5s2i7qoksp54tgwl_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 用户表';

-- ----------------------------
-- Records of tb_sys_user
-- ----------------------------
INSERT INTO `tb_sys_user` VALUES ('3', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/18/84922afda435453caffc12c7739c6bad.jpg', 'root_admin', 'root_admin@163.com', '超级管理员', '$2a$10$F47JY5Yt2DGoPuG8Fra8XuyiA20Q9g3.4J5eKXB0DrmvacVO1Olya', '1', '2019-12-11', '1', '2018-11-18 19:18:50', '2018-11-18 19:18:50', '5');
INSERT INTO `tb_sys_user` VALUES ('7', 'http://q17pj3u6q.bkt.clouddn.com/a2693e3320314060b096de0ab1e4486a.jpeg', 'test00003', 'test00003@qq.com', '测试00003', '$2a$10$c9bcH.XPR3c9dzRg2T91g.jJ/PQVnRWo8wof.ybp/IJiMn5Bf03oW', '0', '2019-12-11', '1', '2019-12-11 23:32:54', '2019-12-11 23:32:54', '1');

-- ----------------------------
-- Table structure for tb_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_role`;
CREATE TABLE `tb_sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[ 权限管理 ] 用户表和角色表';

-- ----------------------------
-- Records of tb_sys_user_role
-- ----------------------------
INSERT INTO `tb_sys_user_role` VALUES ('1', '1');
INSERT INTO `tb_sys_user_role` VALUES ('2', '2');
INSERT INTO `tb_sys_user_role` VALUES ('3', '3');
