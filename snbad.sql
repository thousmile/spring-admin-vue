/*
Navicat MySQL Data Transfer

Source Server         : 刘俊mysql
Source Server Version : 50718
Source Host           : rm-wz9t8kxl4zxxm6n1l7o.mysql.rds.aliyuncs.com:3306
Source Database       : snbad

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-10-29 23:15:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `parent_id` int(11) NOT NULL COMMENT '上级部门',
  `level` int(4) NOT NULL COMMENT '部门排序',
  `describe` varchar(255) DEFAULT NULL COMMENT '部门描述',
  `up_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 部门表';

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
INSERT INTO `t_sys_dept` VALUES ('1', '阿里巴巴集团', '0', '1', '阿里巴巴集团', '2018-10-23 12:47:09', '2018-10-23 12:47:11');
INSERT INTO `t_sys_dept` VALUES ('2', '阿里云', '1', '0', '阿里云科技', '2018-10-23 04:51:46', '2018-10-23 04:51:46');
INSERT INTO `t_sys_dept` VALUES ('3', '事业部门', '2', '1', '阿里云事业部', '2018-10-23 04:50:33', '2018-10-23 04:50:33');
INSERT INTO `t_sys_dept` VALUES ('4', '开发部门', '2', '2', '阿里云开发部门', '2018-10-23 04:50:11', '2018-10-23 04:50:11');
INSERT INTO `t_sys_dept` VALUES ('5', '安全部门', '2', '3', '阿里云安全部门', '2018-10-23 04:50:13', '2018-10-23 04:50:13');
INSERT INTO `t_sys_dept` VALUES ('6', '淘宝网', '1', '0', '淘宝网络科技', '2018-10-23 12:51:39', '2018-10-23 12:51:41');
INSERT INTO `t_sys_dept` VALUES ('7', '快递部门', '6', '1', '淘宝网快递部门', '2018-10-23 12:52:28', '2018-10-23 12:52:30');
INSERT INTO `t_sys_dept` VALUES ('8', '客服部门', '6', '2', '淘宝网客服部门', '2018-10-23 12:52:59', '2018-10-23 12:53:01');

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限唯一ID',
  `father` int(11) NOT NULL COMMENT '上级ID',
  `resources` varchar(255) NOT NULL COMMENT '权限资源 ',
  `title` varchar(100) NOT NULL COMMENT '资源名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '资源图标',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '类型，menu或者button',
  `add_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `up_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `describe` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 权限表';

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission` VALUES ('1', '0', 'pre', '权限设置', 'pre_admin', 'menu', '2018-10-28 21:58:53', '2018-10-28 21:58:53', '权限管理，如,用户权限的增删改查');
INSERT INTO `t_sys_permission` VALUES ('2', '0', 'sys', '系统设置', 'sys_set', 'menu', '2018-10-28 21:52:24', '2018-10-28 21:52:24', '系统的详细设置, 如微信公众号 openId');
INSERT INTO `t_sys_permission` VALUES ('3', '0', 'control', '系统监控', 'sys_control', 'menu', '2018-10-28 21:52:25', '2018-10-28 21:52:25', '系统运行时的监控');
INSERT INTO `t_sys_permission` VALUES ('4', '0', 'task', '定时任务', 'time_task', 'menu', '2018-10-28 21:52:27', '2018-10-28 21:52:27', '定时发送文章的任务');
INSERT INTO `t_sys_permission` VALUES ('29', '1', 'perm', '权限管理', 'prem_admin', 'menu', '2018-10-28 21:59:07', '2018-10-28 21:59:07', '');
INSERT INTO `t_sys_permission` VALUES ('30', '1', 'user', '用户管理', 'user__admin', 'menu', '2018-10-28 21:59:11', '2018-10-28 21:59:11', '');
INSERT INTO `t_sys_permission` VALUES ('31', '1', 'role', '角色管理', 'role__admin', 'menu', '2018-10-28 21:59:15', '2018-10-28 21:59:15', '');
INSERT INTO `t_sys_permission` VALUES ('32', '1', 'dept', '部门管理', 'dept__admin', 'menu', '2018-10-28 21:59:19', '2018-10-28 21:59:19', '');
INSERT INTO `t_sys_permission` VALUES ('33', '29', 'perm:new', '新增权限', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('34', '29', 'perm:delete', '删除权限', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('35', '29', 'perm:update', '修改权限', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('36', '29', 'perm:view', '查看权限', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('37', '30', 'user:new', '新增用户', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('38', '30', 'user:delete', '删除用户', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('39', '30', 'user:update', '修改用户', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('40', '30', 'user:view', '查看用户', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('41', '31', 'role:new', '新增角色', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('42', '31', 'role:delete', '删除角色', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('43', '31', 'role:update', '修改角色', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('44', '31', 'role:view', '查看角色', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('45', '32', 'dept:new', '新增部门', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('46', '32', 'dept:delete', '删除部门', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('47', '32', 'dept:update', '修改部门', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('48', '32', 'dept:view', '查看部门', '', 'button', '2018-10-15 11:35:57', '2018-10-15 11:35:57', '');
INSERT INTO `t_sys_permission` VALUES ('49', '3', 'database', '数据库监控', 'control_database', 'menu', '2018-10-28 22:11:11', '2018-10-28 22:11:11', '数据库监控');
INSERT INTO `t_sys_permission` VALUES ('50', '3', 'logs', '系统日志', 'control_logs', 'menu', '2018-10-28 22:11:18', '2018-10-28 22:11:18', '系统日志监控');
INSERT INTO `t_sys_permission` VALUES ('51', '2', 'wechat', '微信设置', 'sys_wechat', 'menu', '2018-10-29 22:26:54', '2018-10-29 22:26:56', '微信相关设置');
INSERT INTO `t_sys_permission` VALUES ('52', '2', 'backstage', '后台设置', 'sys_backstage', 'menu', '2018-10-29 22:29:45', '2018-10-29 22:29:47', '后台设置');

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
  `describe` varchar(255) DEFAULT NULL COMMENT '系统角色描述',
  `name` varchar(255) DEFAULT NULL COMMENT '系统角色名称',
  `state` int(11) DEFAULT NULL COMMENT '系统角色状态[0.删除，1.正常]',
  `up_time` datetime DEFAULT NULL COMMENT '修改时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('3', '超级管理员', 'ROLE_ROOT', '1', '2018-10-23 12:32:10', '2018-10-23 12:32:13');
INSERT INTO `t_sys_role` VALUES ('4', '管理员', 'ROLE_ADMIN', '1', '2018-10-23 12:32:29', '2018-10-23 12:32:31');
INSERT INTO `t_sys_role` VALUES ('5', '编辑', 'ROLE_EDIT', '1', '2018-10-23 12:32:50', '2018-10-23 12:32:52');
INSERT INTO `t_sys_role` VALUES ('6', '测试用户', 'ROLE_USER', '1', '2018-10-23 12:33:11', '2018-10-23 12:33:13');

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
  `permission_id` int(20) NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `t_sys_role_permission_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`rid`),
  CONSTRAINT `t_sys_role_permission_ibfk_3` FOREIGN KEY (`permission_id`) REFERENCES `t_sys_permission` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 角色和权限表';

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES ('1', '3');
INSERT INTO `t_sys_role_permission` VALUES ('2', '3');
INSERT INTO `t_sys_role_permission` VALUES ('3', '3');
INSERT INTO `t_sys_role_permission` VALUES ('4', '3');
INSERT INTO `t_sys_role_permission` VALUES ('29', '3');
INSERT INTO `t_sys_role_permission` VALUES ('30', '3');
INSERT INTO `t_sys_role_permission` VALUES ('31', '3');
INSERT INTO `t_sys_role_permission` VALUES ('32', '3');
INSERT INTO `t_sys_role_permission` VALUES ('33', '3');
INSERT INTO `t_sys_role_permission` VALUES ('34', '3');
INSERT INTO `t_sys_role_permission` VALUES ('35', '3');
INSERT INTO `t_sys_role_permission` VALUES ('36', '3');
INSERT INTO `t_sys_role_permission` VALUES ('37', '3');
INSERT INTO `t_sys_role_permission` VALUES ('38', '3');
INSERT INTO `t_sys_role_permission` VALUES ('39', '3');
INSERT INTO `t_sys_role_permission` VALUES ('40', '3');
INSERT INTO `t_sys_role_permission` VALUES ('41', '3');
INSERT INTO `t_sys_role_permission` VALUES ('42', '3');
INSERT INTO `t_sys_role_permission` VALUES ('43', '3');
INSERT INTO `t_sys_role_permission` VALUES ('44', '3');
INSERT INTO `t_sys_role_permission` VALUES ('45', '3');
INSERT INTO `t_sys_role_permission` VALUES ('46', '3');
INSERT INTO `t_sys_role_permission` VALUES ('47', '3');
INSERT INTO `t_sys_role_permission` VALUES ('48', '3');
INSERT INTO `t_sys_role_permission` VALUES ('49', '3');
INSERT INTO `t_sys_role_permission` VALUES ('50', '3');
INSERT INTO `t_sys_role_permission` VALUES ('1', '4');
INSERT INTO `t_sys_role_permission` VALUES ('29', '4');
INSERT INTO `t_sys_role_permission` VALUES ('33', '4');
INSERT INTO `t_sys_role_permission` VALUES ('34', '4');
INSERT INTO `t_sys_role_permission` VALUES ('35', '4');
INSERT INTO `t_sys_role_permission` VALUES ('36', '4');
INSERT INTO `t_sys_role_permission` VALUES ('51', '3');
INSERT INTO `t_sys_role_permission` VALUES ('52', '3');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `uid` varchar(250) NOT NULL COMMENT '系统用户ID',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `username` varchar(100) NOT NULL COMMENT '账号',
  `nickname` varchar(100) NOT NULL COMMENT '用户名称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `mail` varchar(50) NOT NULL COMMENT '邮箱',
  `state` int(4) NOT NULL COMMENT '状态',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `up_time` datetime DEFAULT NULL COMMENT '修改时间',
  `dept` int(11) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UK_6i5ixxulo5s2i7qoksp54tgwl` (`username`),
  KEY `t_dept` (`dept`),
  CONSTRAINT `t_sys_user_ibfk_1` FOREIGN KEY (`dept`) REFERENCES `t_sys_dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('3BDDD3B7B3AF4BA2A8FA0EFEB585597B', 'https://ifsaid-blog.oss-cn-shenzhen.aliyuncs.com/images/2018/9/28/3BDDD3B7B3AF4BA2A8FA0EFEB585597B.jpg', 'admin', '系统管理员', '$2a$10$XTF2XRl2GR9ARlR/C55bjOYnpfTZeSmDyqJwKQMTm16BXau.BsCC.', null, 'thousmile@163.com', '1', '2018-10-23 12:09:02', '2018-10-23 12:09:05', '4');
INSERT INTO `t_sys_user` VALUES ('3BDwqwqeqwc5448wqevcrA0EFEB585597B', 'https://ifsaid-blog.oss-cn-shenzhen.aliyuncs.com/images/2018/9/28/3BDDD3B7B3AF4BA2A8FA0EFEB585597B.jpg', 'test', '测试用户', '$2a$10$HL7Di2TKZKwJenz/c06W4eEwNvSppfI6mj34JkwVAaJ9eb.8wRPlK', null, '123456789@163.com', '1', '2018-10-23 12:09:02', '2018-10-23 12:09:05', '5');

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `user_uid` varchar(250) NOT NULL COMMENT '用户id',
  `role_rid` int(11) NOT NULL COMMENT '角色id',
  KEY `user_uid` (`user_uid`),
  KEY `role_rid` (`role_rid`),
  CONSTRAINT `t_sys_user_role_ibfk_2` FOREIGN KEY (`role_rid`) REFERENCES `t_sys_role` (`rid`),
  CONSTRAINT `t_sys_user_role_ibfk_3` FOREIGN KEY (`user_uid`) REFERENCES `t_sys_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 角色和权限表';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('3BDDD3B7B3AF4BA2A8FA0EFEB585597B', '3');
INSERT INTO `t_sys_user_role` VALUES ('3BDDD3B7B3AF4BA2A8FA0EFEB585597B', '6');
INSERT INTO `t_sys_user_role` VALUES ('3BDwqwqeqwc5448wqevcrA0EFEB585597B', '4');
