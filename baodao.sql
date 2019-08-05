/*
Navicat MySQL Data Transfer

Source Server         : docker-mysql
Source Server Version : 50718
Source Host           : 47.107.41.199:3306
Source Database       : baodao

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-19 09:39:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `t_aid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `t_cover_image` varchar(255) NOT NULL COMMENT '封面图片',
  `t_title` varchar(100) NOT NULL COMMENT '标题',
  `t_introduce` varchar(255) NOT NULL COMMENT '简介',
  `t_content` text NOT NULL COMMENT '文章内容',
  `t_source` char(50) NOT NULL COMMENT '来源',
  `t_total` int(11) NOT NULL COMMENT '总浏览量',
  `t_weight` tinyint(4) NOT NULL COMMENT '权重：10最高 默认1',
  `t_user_id` char(200) NOT NULL COMMENT '作者ID',
  `t_category_id` int(11) NOT NULL COMMENT '类目ID',
  `t_state` tinyint(4) NOT NULL COMMENT '状态[0.删除，1.正常 2.草稿]',
  `t_create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `t_up_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`t_aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='[三农网] 文章表';

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES ('1', '[]', '这是一篇好文章', '一篇好文章', '一篇好文章', '广东三农', '100', '10', 'dwqewqeqw546181wefwerfve5', '1', '1', '2018-11-16 11:46:21', '2018-11-16 11:46:21');

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `t_cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '类目id',
  `t_title` varchar(100) NOT NULL COMMENT '类目标题',
  `t_describe` varchar(255) NOT NULL COMMENT '描述',
  `t_create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `t_up_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `t_region` char(20) NOT NULL COMMENT '区域： gd.广东三农   gx.广西三农 gz.贵州',
  PRIMARY KEY (`t_cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='[三农网] 文章类目表';

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES ('1', '三农资讯', '三农资讯【广东三农】', '2018-11-15 16:49:15', '2018-11-15 16:49:15', 'gd');
INSERT INTO `tb_category` VALUES ('2', '人物风采', '人物风采【广东三农】', '2018-11-15 16:49:38', '2018-11-15 16:49:38', 'gd');
INSERT INTO `tb_category` VALUES ('3', '特色旅游', '特色旅游【广东三农】', '2018-11-15 16:49:53', '2018-11-15 16:49:53', 'gd');

-- ----------------------------
-- Table structure for tb_dept
-- ----------------------------
DROP TABLE IF EXISTS `tb_dept`;
CREATE TABLE `tb_dept` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `t_name` varchar(255) NOT NULL COMMENT '部门名称',
  `t_parent_id` int(11) NOT NULL COMMENT '上级部门',
  `t_level` tinyint(4) NOT NULL COMMENT '部门排序',
  `t_describe` varchar(255) NOT NULL COMMENT '部门描述',
  `t_up_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `t_create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 部门表';

-- ----------------------------
-- Records of tb_dept
-- ----------------------------
INSERT INTO `tb_dept` VALUES ('1', '阿里巴巴集团', '0', '0', '阿里巴巴集团', '2018-11-15 03:23:13', '2018-11-15 03:23:13');
INSERT INTO `tb_dept` VALUES ('2', '阿里云', '1', '1', '阿里云科技', '2018-11-15 03:23:15', '2018-11-15 03:23:15');
INSERT INTO `tb_dept` VALUES ('3', '事业部门', '2', '1', '【阿里云】事业部门', '2018-11-17 16:21:56', '2018-10-23 04:50:33');
INSERT INTO `tb_dept` VALUES ('4', '开发部门', '2', '2', '【阿里云】开发部门', '2018-10-23 04:50:11', '2018-10-23 04:50:11');
INSERT INTO `tb_dept` VALUES ('5', '安全部门', '2', '3', '【阿里云】安全部门', '2018-11-17 16:21:45', '2018-10-23 04:50:13');
INSERT INTO `tb_dept` VALUES ('6', '淘宝网', '1', '2', '淘宝网络科技', '2018-11-15 03:23:26', '2018-11-15 03:23:26');
INSERT INTO `tb_dept` VALUES ('8', '客服部门', '6', '1', '【淘宝网】客服部门', '2018-11-17 16:22:13', '2018-11-15 03:24:11');
INSERT INTO `tb_dept` VALUES ('9', '菜鸟网络', '1', '3', '菜鸟网络', '2018-10-31 09:28:28', '2018-10-23 12:53:01');
INSERT INTO `tb_dept` VALUES ('12', '菜鸟物流', '9', '1', '【菜鸟网络】菜鸟物流', '2018-10-31 09:28:33', '2018-10-23 12:53:01');

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `t_pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限唯一ID',
  `t_parent_id` int(11) NOT NULL COMMENT '上级ID',
  `t_resources` char(255) NOT NULL COMMENT '权限资源 ',
  `t_title` char(100) NOT NULL COMMENT '资源名称',
  `t_icon` char(100) NOT NULL COMMENT '资源图标',
  `t_type` char(10) NOT NULL DEFAULT '' COMMENT '类型，menu或者button',
  `t_create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `t_up_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `t_describe` varchar(255) NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`t_pid`),
  UNIQUE KEY `t_resources` (`t_resources`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 权限表';

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES ('1', '0', 'pre', '权限设置', 'pre_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '权限设置');
INSERT INTO `tb_permission` VALUES ('2', '0', 'sys', '系统设置', 'sys_set', 'menu', '2018-11-17 11:21:31', '2018-11-17 11:21:31', '系统设置');
INSERT INTO `tb_permission` VALUES ('4', '0', 'article', '文章管理', 'article_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '文章管理');
INSERT INTO `tb_permission` VALUES ('5', '1', 'pre_perm', '权限管理', 'pre_perm_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '权限管理');
INSERT INTO `tb_permission` VALUES ('6', '1', 'pre_user', '用户管理', 'pre_user_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '用户管理');
INSERT INTO `tb_permission` VALUES ('7', '1', 'pre_role', '角色管理', 'pre_role_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '角色管理');
INSERT INTO `tb_permission` VALUES ('8', '1', 'pre_dept', '部门管理', 'pre_dept_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '部门管理');
INSERT INTO `tb_permission` VALUES ('9', '4', 'article_write', '发布文章', 'article_article_write', 'menu', '2018-11-17 11:33:14', '2018-11-17 11:33:14', '发布文章');
INSERT INTO `tb_permission` VALUES ('10', '4', 'article_my', '我的文章', 'article_article_my', 'menu', '2018-11-17 11:33:20', '2018-11-17 11:33:20', '我的文章');
INSERT INTO `tb_permission` VALUES ('11', '4', 'article_category', '类目管理', 'article_article_category', 'menu', '2018-11-17 11:33:27', '2018-11-17 11:33:27', '类目管理');
INSERT INTO `tb_permission` VALUES ('12', '4', 'article_list', '文章列表', 'article_article_list', 'menu', '2018-11-17 11:33:34', '2018-11-17 11:33:34', '文章列表');
INSERT INTO `tb_permission` VALUES ('13', '5', 'pre_perm:new', '新增权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增权限');
INSERT INTO `tb_permission` VALUES ('14', '5', 'pre_perm:delete', '删除权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除权限');
INSERT INTO `tb_permission` VALUES ('15', '5', 'pre_perm:update', '修改权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改权限');
INSERT INTO `tb_permission` VALUES ('16', '5', 'pre_perm:view', '查看权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看权限');
INSERT INTO `tb_permission` VALUES ('17', '6', 'pre_user:new', '新增用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增角色');
INSERT INTO `tb_permission` VALUES ('18', '6', 'pre_user:delete', '删除用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除角色');
INSERT INTO `tb_permission` VALUES ('19', '6', 'pre_user:update', '修改用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改角色');
INSERT INTO `tb_permission` VALUES ('20', '6', 'pre_user:view', '查看用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看角色');
INSERT INTO `tb_permission` VALUES ('21', '7', 'pre_role:new', '新增角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增角色');
INSERT INTO `tb_permission` VALUES ('22', '7', 'pre_role:delete', '删除角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除角色');
INSERT INTO `tb_permission` VALUES ('23', '7', 'pre_role:update', '修改角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改角色');
INSERT INTO `tb_permission` VALUES ('24', '7', 'pre_role:view', '查看角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看角色');
INSERT INTO `tb_permission` VALUES ('25', '8', 'pre_dept:new', '新增部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增部门');
INSERT INTO `tb_permission` VALUES ('26', '8', 'pre_dept:delete', '删除部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除部门');
INSERT INTO `tb_permission` VALUES ('27', '8', 'pre_dept:update', '修改部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改部门');
INSERT INTO `tb_permission` VALUES ('28', '8', 'pre_dept:view', '查看部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看部门');
INSERT INTO `tb_permission` VALUES ('29', '11', 'article_category:new', '新增类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增类目');
INSERT INTO `tb_permission` VALUES ('30', '11', 'article_category:delete', '删除类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除类目');
INSERT INTO `tb_permission` VALUES ('31', '11', 'article_category:update', '修改类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改类目');
INSERT INTO `tb_permission` VALUES ('32', '11', 'article_category:view', '查看类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看类目');
INSERT INTO `tb_permission` VALUES ('33', '2', 'sys_database', '数据库监控', 'sys_database', 'menu', '2018-10-28 22:11:11', '2018-10-28 22:11:11', '数据库监控');
INSERT INTO `tb_permission` VALUES ('34', '2', 'sys_logs', '系统日志', 'sys_logs', 'menu', '2018-10-28 22:11:18', '2018-10-28 22:11:18', '系统日志监控');
INSERT INTO `tb_permission` VALUES ('35', '2', 'sys_wechat', '微信设置', 'sys_wechat', 'menu', '2018-10-29 22:26:54', '2018-10-29 22:26:56', '微信相关设置');
INSERT INTO `tb_permission` VALUES ('36', '2', 'sys_backstage', '后台设置', 'sys_backstage', 'menu', '2018-10-29 22:29:45', '2018-10-29 22:29:47', '后台设置');
INSERT INTO `tb_permission` VALUES ('38', '0', 'test', '测试权限', 'test', 'menu', '2018-11-18 12:24:32', '2018-11-18 12:24:32', '测试权限');
INSERT INTO `tb_permission` VALUES ('39', '38', 'test_haha', '测试权限二', 'twqeqw', 'menu', '2018-11-18 12:32:24', '2018-11-18 12:32:24', '测试权限二');
INSERT INTO `tb_permission` VALUES ('40', '39', 'testwqeqwewqe', '测试按钮', 'testwqeqwewqe', 'button', '2018-11-18 12:33:06', '2018-11-18 12:33:06', '测试按钮');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `t_rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
  `t_describe` varchar(255) NOT NULL COMMENT '系统角色描述',
  `t_name` char(255) NOT NULL COMMENT '系统角色名称',
  `t_state` tinyint(4) NOT NULL COMMENT '系统角色状态[0.删除，1.正常]',
  `t_up_time` datetime NOT NULL COMMENT '修改时间',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`t_rid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='[权限管理] 角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('3', '超级管理员', 'ROLE_ROOT', '1', '2018-10-23 12:32:10', '2018-10-23 12:32:13');
INSERT INTO `tb_role` VALUES ('4', '管理员', 'ROLE_ADMIN', '1', '2018-10-23 12:32:29', '2018-10-23 12:32:31');
INSERT INTO `tb_role` VALUES ('5', '编辑', 'ROLE_EDIT', '1', '2018-10-23 12:32:50', '2018-10-23 12:32:52');
INSERT INTO `tb_role` VALUES ('6', '普通用户', 'ROLE_USER', '1', '2018-10-23 12:33:11', '2018-10-23 12:33:13');
INSERT INTO `tb_role` VALUES ('9', '运维人员', 'ROLE_OPERATION', '1', '2018-11-02 17:11:00', '2018-10-31 14:48:16');
INSERT INTO `tb_role` VALUES ('10', '测试用户', 'ROLE_TEST', '1', '2018-11-17 16:58:14', '2018-11-17 16:51:26');

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission` (
  `t_permission_id` int(11) NOT NULL COMMENT '权限ID',
  `t_role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 角色和权限表';

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES ('1', '3');
INSERT INTO `tb_role_permission` VALUES ('2', '3');
INSERT INTO `tb_role_permission` VALUES ('4', '3');
INSERT INTO `tb_role_permission` VALUES ('5', '3');
INSERT INTO `tb_role_permission` VALUES ('6', '3');
INSERT INTO `tb_role_permission` VALUES ('7', '3');
INSERT INTO `tb_role_permission` VALUES ('8', '3');
INSERT INTO `tb_role_permission` VALUES ('9', '3');
INSERT INTO `tb_role_permission` VALUES ('10', '3');
INSERT INTO `tb_role_permission` VALUES ('11', '3');
INSERT INTO `tb_role_permission` VALUES ('12', '3');
INSERT INTO `tb_role_permission` VALUES ('13', '3');
INSERT INTO `tb_role_permission` VALUES ('14', '3');
INSERT INTO `tb_role_permission` VALUES ('15', '3');
INSERT INTO `tb_role_permission` VALUES ('16', '3');
INSERT INTO `tb_role_permission` VALUES ('17', '3');
INSERT INTO `tb_role_permission` VALUES ('18', '3');
INSERT INTO `tb_role_permission` VALUES ('19', '3');
INSERT INTO `tb_role_permission` VALUES ('20', '3');
INSERT INTO `tb_role_permission` VALUES ('21', '3');
INSERT INTO `tb_role_permission` VALUES ('22', '3');
INSERT INTO `tb_role_permission` VALUES ('23', '3');
INSERT INTO `tb_role_permission` VALUES ('24', '3');
INSERT INTO `tb_role_permission` VALUES ('25', '3');
INSERT INTO `tb_role_permission` VALUES ('26', '3');
INSERT INTO `tb_role_permission` VALUES ('27', '3');
INSERT INTO `tb_role_permission` VALUES ('28', '3');
INSERT INTO `tb_role_permission` VALUES ('29', '3');
INSERT INTO `tb_role_permission` VALUES ('30', '3');
INSERT INTO `tb_role_permission` VALUES ('31', '3');
INSERT INTO `tb_role_permission` VALUES ('32', '3');
INSERT INTO `tb_role_permission` VALUES ('33', '3');
INSERT INTO `tb_role_permission` VALUES ('34', '3');
INSERT INTO `tb_role_permission` VALUES ('35', '3');
INSERT INTO `tb_role_permission` VALUES ('36', '3');
INSERT INTO `tb_role_permission` VALUES ('1', '4');
INSERT INTO `tb_role_permission` VALUES ('5', '4');
INSERT INTO `tb_role_permission` VALUES ('6', '4');
INSERT INTO `tb_role_permission` VALUES ('7', '4');
INSERT INTO `tb_role_permission` VALUES ('8', '4');
INSERT INTO `tb_role_permission` VALUES ('13', '4');
INSERT INTO `tb_role_permission` VALUES ('14', '4');
INSERT INTO `tb_role_permission` VALUES ('15', '4');
INSERT INTO `tb_role_permission` VALUES ('16', '4');
INSERT INTO `tb_role_permission` VALUES ('17', '4');
INSERT INTO `tb_role_permission` VALUES ('18', '4');
INSERT INTO `tb_role_permission` VALUES ('19', '4');
INSERT INTO `tb_role_permission` VALUES ('20', '4');
INSERT INTO `tb_role_permission` VALUES ('21', '4');
INSERT INTO `tb_role_permission` VALUES ('22', '4');
INSERT INTO `tb_role_permission` VALUES ('23', '4');
INSERT INTO `tb_role_permission` VALUES ('24', '4');
INSERT INTO `tb_role_permission` VALUES ('25', '4');
INSERT INTO `tb_role_permission` VALUES ('26', '4');
INSERT INTO `tb_role_permission` VALUES ('27', '4');
INSERT INTO `tb_role_permission` VALUES ('28', '4');
INSERT INTO `tb_role_permission` VALUES ('32', '5');
INSERT INTO `tb_role_permission` VALUES ('4', '5');
INSERT INTO `tb_role_permission` VALUES ('9', '5');
INSERT INTO `tb_role_permission` VALUES ('10', '5');
INSERT INTO `tb_role_permission` VALUES ('11', '5');
INSERT INTO `tb_role_permission` VALUES ('12', '5');
INSERT INTO `tb_role_permission` VALUES ('29', '5');
INSERT INTO `tb_role_permission` VALUES ('30', '5');
INSERT INTO `tb_role_permission` VALUES ('31', '5');
INSERT INTO `tb_role_permission` VALUES ('16', '10');
INSERT INTO `tb_role_permission` VALUES ('1', '10');
INSERT INTO `tb_role_permission` VALUES ('20', '10');
INSERT INTO `tb_role_permission` VALUES ('5', '10');
INSERT INTO `tb_role_permission` VALUES ('6', '10');
INSERT INTO `tb_role_permission` VALUES ('7', '10');
INSERT INTO `tb_role_permission` VALUES ('24', '10');
INSERT INTO `tb_role_permission` VALUES ('8', '10');
INSERT INTO `tb_role_permission` VALUES ('27', '10');
INSERT INTO `tb_role_permission` VALUES ('16', '6');
INSERT INTO `tb_role_permission` VALUES ('32', '6');
INSERT INTO `tb_role_permission` VALUES ('1', '6');
INSERT INTO `tb_role_permission` VALUES ('20', '6');
INSERT INTO `tb_role_permission` VALUES ('4', '6');
INSERT INTO `tb_role_permission` VALUES ('5', '6');
INSERT INTO `tb_role_permission` VALUES ('6', '6');
INSERT INTO `tb_role_permission` VALUES ('7', '6');
INSERT INTO `tb_role_permission` VALUES ('24', '6');
INSERT INTO `tb_role_permission` VALUES ('8', '6');
INSERT INTO `tb_role_permission` VALUES ('11', '6');
INSERT INTO `tb_role_permission` VALUES ('28', '6');
INSERT INTO `tb_role_permission` VALUES ('16', '9');
INSERT INTO `tb_role_permission` VALUES ('1', '9');
INSERT INTO `tb_role_permission` VALUES ('20', '9');
INSERT INTO `tb_role_permission` VALUES ('5', '9');
INSERT INTO `tb_role_permission` VALUES ('6', '9');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `t_uid` char(200) NOT NULL COMMENT '用户ID',
  `t_avatar` char(255) NOT NULL COMMENT '头像',
  `t_account` char(50) NOT NULL COMMENT '账号',
  `t_mail` char(50) NOT NULL COMMENT '邮箱',
  `t_open_id` char(200) NOT NULL COMMENT '微信open Id',
  `t_nickname` char(100) NOT NULL COMMENT '用户名称',
  `t_password` char(255) NOT NULL COMMENT '密码',
  `t_gender` tinyint(4) NOT NULL COMMENT '性别[ 0.女  1.男  2.未知]',
  `t_birthday` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '生日',
  `t_state` tinyint(4) NOT NULL COMMENT '状态 【0.禁用 1.正常 2.被删除】',
  `t_create_time` datetime NOT NULL COMMENT '添加时间',
  `t_up_time` datetime NOT NULL COMMENT '修改时间',
  `t_dept` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`t_uid`),
  UNIQUE KEY `UK_6i5ixxulo5s2i7qoksp54tgwl` (`t_account`),
  UNIQUE KEY `t_mail` (`t_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('402881e7672672df016726730baa0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/18/47/8e4227d68b74444bb5e77b460e1696ca.jpg', 'user_test', 'user_test@163.com', '', '隔壁小王', '$2a$10$Ks5PVJfFqqE1/gLpGStYaeLVbKMetwFN8uPpp4bSkt2F2HBrbcOlS', '0', '1975-06-03 00:00:00', '1', '2018-11-18 18:52:13', '2018-11-18 19:17:07', '3');
INSERT INTO `tb_user` VALUES ('402881e7672689ac0167268b687a0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/18/84922afda435453caffc12c7739c6bad.jpg', 'root_admin', 'root_admin@163.com', '', '超级管理员', '$2a$10$F47JY5Yt2DGoPuG8Fra8XuyiA20Q9g3.4J5eKXB0DrmvacVO1Olya', '1', '1989-11-23 00:00:00', '1', '2018-11-18 19:18:50', '2018-11-18 19:18:50', '5');
INSERT INTO `tb_user` VALUES ('402881e7672699f60167269cdd2f0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/37/f499ea6a683548a6bb6c986a38284c1d.jpeg', 'xiaoxianv', 'xiaoxianv@qq.com', '', '小仙女', '$2a$10$PeWGgYaPq3dIIV5fNgdT1.DSq9w6696T.pyBJl6XZRfNKVwBGvoSG', '0', '2018-11-14 00:00:00', '1', '2018-11-18 19:37:54', '2018-11-18 19:37:54', '8');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `t_user_id` char(200) NOT NULL COMMENT '用户ID',
  `t_role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[权限管理] 用户表和角色表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('402881e7672672df016726730baa0000', '4');
INSERT INTO `tb_user_role` VALUES ('402881e7672689ac0167268b687a0000', '3');
INSERT INTO `tb_user_role` VALUES ('402881e7672699f60167269cdd2f0000', '5');
