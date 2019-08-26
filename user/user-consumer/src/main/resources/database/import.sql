/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2018-11-17 19:24:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `oper_method` varchar(250) DEFAULT NULL COMMENT '操作方法',
  `request_param` varchar(500) DEFAULT NULL COMMENT '操作参数',
  `oper_desc` varchar(255) DEFAULT NULL COMMENT '操作说明',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '1', 'admin', 'UserController.edit', '[User{, id=9, roleId=2, name=test, userName=null, passWord=null, salt=null, state=1, updateTime=null, createTime=null}]', '修改用户操作', '2018-10-27 16:18:35', '2018-10-27 16:18:35');
INSERT INTO `sys_log` VALUES ('2', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=系统日志, parentId=1, parentIds=null, permissionCode=log:view, resourceType=menu, url=/log/list, updateTime=null, createTime=null}]', '保存权限操作', '2018-10-27 16:28:50', '2018-10-27 16:28:50');
INSERT INTO `sys_log` VALUES ('3', '1', 'admin', 'RoleController.save', '[Role{, id=1, permissionIds=,1,19,17,6,9,7,8,16,10,12,13,11,2,4,5,3,14,15,18,, available=1, roleName=超级管理员, roleCode=admin, updateTime=null, createTime=null}]', '保存角色操作', '2018-10-27 16:31:13', '2018-10-27 16:31:13');
INSERT INTO `sys_log` VALUES ('4', '1', 'admin', 'PermissionController.save', '[Permission{, id=15, available=1, permissionName=一级菜单, parentId=14, parentIds=null, permissionCode=onemenu:view, resourceType=menu, url=/onemenu/list, updateTime=null, createTime=null}]', '保存权限操作', '2018-10-27 16:32:20', '2018-10-27 16:32:20');
INSERT INTO `sys_log` VALUES ('5', '1', 'admin', 'RoleController.save', '[Role{, id=1, permissionIds=,1,17,6,9,7,8,18,16,10,13,11,12,2,4,5,3,14,19,15,, available=1, roleName=超级管理员, roleCode=admin, updateTime=null, createTime=null}]', '保存角色操作', '2018-10-27 16:45:03', '2018-10-27 16:45:03');
INSERT INTO `sys_log` VALUES ('6', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=目录测试, parentId=14, parentIds=null, permissionCode=mulutest, resourceType=directory, url=, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 12:47:06', '2018-11-17 12:47:06');
INSERT INTO `sys_log` VALUES ('7', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=菜单测试, parentId=20, parentIds=null, permissionCode=caidantest:view, resourceType=menu, url=/caidantest/list, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 12:49:07', '2018-11-17 12:49:07');
INSERT INTO `sys_log` VALUES ('8', '1', 'admin', 'PermissionController.delBatch', '[[Ljava.lang.Integer;@5837394d]', '删除权限操作', '2018-11-17 12:50:02', '2018-11-17 12:50:02');
INSERT INTO `sys_log` VALUES ('9', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=33, parentId=21, parentIds=null, permissionCode=343, resourceType=button, url=, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 13:01:53', '2018-11-17 13:01:53');
INSERT INTO `sys_log` VALUES ('10', '1', 'admin', 'PermissionController.delBatch', '[[Ljava.lang.Integer;@91d5842]', '删除权限操作', '2018-11-17 13:02:08', '2018-11-17 13:02:08');
INSERT INTO `sys_log` VALUES ('11', '1', 'admin', 'PermissionController.delBatch', '[[Ljava.lang.Integer;@6dd74761]', '删除权限操作', '2018-11-17 13:02:21', '2018-11-17 13:02:21');
INSERT INTO `sys_log` VALUES ('12', '1', 'admin', 'PermissionController.save', '[Permission{, id=21, available=1, permissionName=菜单测试, parentId=20, parentIds=null, permissionCode=caidantest:view, resourceType=menu, url=/caidantest/list, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 14:32:57', '2018-11-17 14:32:57');
INSERT INTO `sys_log` VALUES ('13', '1', 'admin', 'RoleController.save', '[Role{, id=1, permissionIds=,1,2,5,3,4,18,16,6,8,9,7,17,10,12,13,11,14,20,21,, available=1, roleName=超级管理员, roleCode=admin, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 14:36:14', '2018-11-17 14:36:14');
INSERT INTO `sys_log` VALUES ('14', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=测试菜单, parentId=20, parentIds=null, permissionCode=testcaidan:view, resourceType=menu, url=testcaidan:listt, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 14:39:13', '2018-11-17 14:39:13');
INSERT INTO `sys_log` VALUES ('15', '1', 'admin', 'RoleController.save', '[Role{, id=1, permissionIds=,1,17,10,12,13,11,2,4,5,3,18,16,6,9,7,8,14,20,23,21,, available=1, roleName=超级管理员, roleCode=admin, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 14:49:16', '2018-11-17 14:49:16');
INSERT INTO `sys_log` VALUES ('16', '1', 'admin', 'PermissionController.save', '[Permission{, id=23, available=1, permissionName=测试菜单, parentId=20, parentIds=null, permissionCode=testcaidan:view, resourceType=menu, url=testcaidan:listt, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 14:50:05', '2018-11-17 14:50:05');
INSERT INTO `sys_log` VALUES ('17', '1', 'admin', 'PermissionController.save', '[Permission{, id=23, available=1, permissionName=测试菜单, parentId=20, parentIds=null, permissionCode=testcaidan:view, resourceType=menu, url=/testcaidan/listt, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 14:50:26', '2018-11-17 14:50:26');
INSERT INTO `sys_log` VALUES ('18', '1', 'admin', 'PermissionController.save', '[Permission{, id=5, available=1, permissionName=用户编辑, parentId=2, parentIds=null, permissionCode=user:edit, resourceType=button, url=, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 14:54:52', '2018-11-17 14:54:52');
INSERT INTO `sys_log` VALUES ('19', '1', 'admin', 'PermissionController.save', '[Permission{, id=8, available=1, permissionName=角色删除, parentId=6, parentIds=null, permissionCode=role:del, resourceType=button, url=, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 15:01:41', '2018-11-17 15:01:41');
INSERT INTO `sys_log` VALUES ('20', '1', 'admin', 'RoleController.save', '[Role{, id=2, permissionIds=,14,20,23,21,, available=1, roleName=测试员, roleCode=test, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 15:02:53', '2018-11-17 15:02:53');
INSERT INTO `sys_log` VALUES ('21', '1', 'admin', 'PermissionController.save', '[Permission{, id=null, available=1, permissionName=测试按钮, parentId=23, parentIds=null, permissionCode=testcaidan:add, resourceType=button, url=, updateTime=null, createTime=null}]', '保存权限操作', '2018-11-17 15:05:12', '2018-11-17 15:05:12');
INSERT INTO `sys_log` VALUES ('22', '1', 'admin', 'RoleController.save', '[Role{, id=2, permissionIds=,14,20,21,23,24,, available=1, roleName=测试员, roleCode=test, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 15:05:42', '2018-11-17 15:05:42');
INSERT INTO `sys_log` VALUES ('23', '1', 'admin', 'RoleController.save', '[Role{, id=1, permissionIds=,1,18,16,6,8,9,7,17,10,13,11,12,2,5,3,4,14,20,21,23,24,, available=1, roleName=超级管理员, roleCode=admin, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 15:05:48', '2018-11-17 15:05:48');
INSERT INTO `sys_log` VALUES ('24', '1', 'admin', 'UserController.userEdit', '[User{, id=1, roleId=null, name=超级管理员, userName=admin, passWord=null, salt=null, state=1, updateTime=null, createTime=null}]', '本人修改用户操作', '2018-11-17 15:19:21', '2018-11-17 15:19:21');
INSERT INTO `sys_log` VALUES ('25', '1', 'admin', 'UserController.edit', '[User{, id=9, roleId=2, name=test, userName=null, passWord=null, salt=null, state=1, updateTime=null, createTime=null}]', '修改用户操作', '2018-11-17 15:19:38', '2018-11-17 15:19:38');
INSERT INTO `sys_log` VALUES ('26', '1', 'admin', 'RoleController.save', '[Role{, id=2, permissionIds=,14,20,21,23,24,, available=1, roleName=测试员, roleCode=test, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 15:19:51', '2018-11-17 15:19:51');
INSERT INTO `sys_log` VALUES ('27', '1', 'admin', 'RoleController.save', '[Role{, id=2, permissionIds=,14,20,21,23,24,, available=1, roleName=测试员, roleCode=test1, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 17:59:51', '2018-11-17 17:59:51');
INSERT INTO `sys_log` VALUES ('28', '1', 'admin', 'RoleController.save', '[Role{, id=2, permissionIds=,14,20,21,23,24,, available=1, roleName=测试员, roleCode=test, updateTime=null, createTime=null}]', '保存角色操作', '2018-11-17 17:59:58', '2018-11-17 17:59:58');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `geography_location` varchar(50) DEFAULT NULL COMMENT '地理位置',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='系统登录日志表';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('1', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 13:06:35', '2018-10-01 13:06:35');
INSERT INTO `sys_login_log` VALUES ('2', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:10:27', '2018-10-01 14:10:27');
INSERT INTO `sys_login_log` VALUES ('3', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:13:04', '2018-10-01 14:13:04');
INSERT INTO `sys_login_log` VALUES ('4', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:14:53', '2018-10-01 14:14:53');
INSERT INTO `sys_login_log` VALUES ('5', '9', 'test', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:15:59', '2018-10-01 14:15:59');
INSERT INTO `sys_login_log` VALUES ('6', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:16:22', '2018-10-01 14:16:22');
INSERT INTO `sys_login_log` VALUES ('7', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:16:34', '2018-10-01 14:16:34');
INSERT INTO `sys_login_log` VALUES ('8', '9', 'test', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:16:47', '2018-10-01 14:16:47');
INSERT INTO `sys_login_log` VALUES ('9', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-01 14:17:25', '2018-10-01 14:17:25');
INSERT INTO `sys_login_log` VALUES ('10', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-10 22:01:26', '2018-10-10 22:01:26');
INSERT INTO `sys_login_log` VALUES ('11', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:05:35', '2018-10-11 08:05:35');
INSERT INTO `sys_login_log` VALUES ('12', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:05:41', '2018-10-11 08:05:41');
INSERT INTO `sys_login_log` VALUES ('13', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:05:42', '2018-10-11 08:05:42');
INSERT INTO `sys_login_log` VALUES ('14', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:05:43', '2018-10-11 08:05:43');
INSERT INTO `sys_login_log` VALUES ('15', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:05:44', '2018-10-11 08:05:44');
INSERT INTO `sys_login_log` VALUES ('16', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:06:36', '2018-10-11 08:06:36');
INSERT INTO `sys_login_log` VALUES ('17', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:13:02', '2018-10-11 08:13:02');
INSERT INTO `sys_login_log` VALUES ('18', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:14:12', '2018-10-11 08:14:12');
INSERT INTO `sys_login_log` VALUES ('19', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:15:03', '2018-10-11 08:15:03');
INSERT INTO `sys_login_log` VALUES ('20', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:15:41', '2018-10-11 08:15:41');
INSERT INTO `sys_login_log` VALUES ('21', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:20:42', '2018-10-11 08:20:42');
INSERT INTO `sys_login_log` VALUES ('22', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:21:53', '2018-10-11 08:21:53');
INSERT INTO `sys_login_log` VALUES ('23', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:26:35', '2018-10-11 08:26:35');
INSERT INTO `sys_login_log` VALUES ('24', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:26:43', '2018-10-11 08:26:43');
INSERT INTO `sys_login_log` VALUES ('25', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:28:19', '2018-10-11 08:28:19');
INSERT INTO `sys_login_log` VALUES ('26', '9', 'test', '127.0.0.1', 'XX XX 内网IP ', '2018-10-11 08:49:33', '2018-10-11 08:49:33');
INSERT INTO `sys_login_log` VALUES ('27', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-27 14:45:35', '2018-10-27 14:45:35');
INSERT INTO `sys_login_log` VALUES ('28', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-27 16:18:17', '2018-10-27 16:18:17');
INSERT INTO `sys_login_log` VALUES ('29', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-27 16:33:01', '2018-10-27 16:33:01');
INSERT INTO `sys_login_log` VALUES ('30', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-10-27 16:44:50', '2018-10-27 16:44:50');
INSERT INTO `sys_login_log` VALUES ('31', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 10:23:24', '2018-11-17 10:23:24');
INSERT INTO `sys_login_log` VALUES ('32', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 10:59:00', '2018-11-17 10:59:00');
INSERT INTO `sys_login_log` VALUES ('33', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 10:59:07', '2018-11-17 10:59:07');
INSERT INTO `sys_login_log` VALUES ('34', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 11:31:05', '2018-11-17 11:31:05');
INSERT INTO `sys_login_log` VALUES ('35', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 11:32:02', '2018-11-17 11:32:02');
INSERT INTO `sys_login_log` VALUES ('36', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 12:13:14', '2018-11-17 12:13:14');
INSERT INTO `sys_login_log` VALUES ('37', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 12:46:25', '2018-11-17 12:46:25');
INSERT INTO `sys_login_log` VALUES ('38', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 13:16:50', '2018-11-17 13:16:50');
INSERT INTO `sys_login_log` VALUES ('39', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 14:31:15', '2018-11-17 14:31:15');
INSERT INTO `sys_login_log` VALUES ('40', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 14:36:28', '2018-11-17 14:36:28');
INSERT INTO `sys_login_log` VALUES ('41', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 14:47:35', '2018-11-17 14:47:35');
INSERT INTO `sys_login_log` VALUES ('42', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 14:49:27', '2018-11-17 14:49:27');
INSERT INTO `sys_login_log` VALUES ('43', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 14:50:45', '2018-11-17 14:50:45');
INSERT INTO `sys_login_log` VALUES ('44', '9', 'test', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 15:02:13', '2018-11-17 15:02:13');
INSERT INTO `sys_login_log` VALUES ('45', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 15:02:31', '2018-11-17 15:02:31');
INSERT INTO `sys_login_log` VALUES ('46', '9', 'test', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 15:03:13', '2018-11-17 15:03:13');
INSERT INTO `sys_login_log` VALUES ('47', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 15:03:40', '2018-11-17 15:03:40');
INSERT INTO `sys_login_log` VALUES ('48', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 17:58:38', '2018-11-17 17:58:38');
INSERT INTO `sys_login_log` VALUES ('49', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 18:34:09', '2018-11-17 18:34:09');
INSERT INTO `sys_login_log` VALUES ('50', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 18:36:41', '2018-11-17 18:36:41');
INSERT INTO `sys_login_log` VALUES ('51', '1', 'admin', '127.0.0.1', 'XX XX 内网IP ', '2018-11-17 18:51:27', '2018-11-17 18:51:27');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父权限ID',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '父权限ID列表',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `resource_type` varchar(50) DEFAULT NULL COMMENT '资源类型(top_directory/directory/menu/button)',
  `url` varchar(50) DEFAULT NULL COMMENT '资源路径',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '1', '系统管理', '0', '0', 'system', 'top_directory', null, '2018-07-10 11:24:48', '2018-07-10 11:24:48');
INSERT INTO `sys_permission` VALUES ('2', '1', '用户管理', '1', '0/1', 'user:view', 'menu', '/user/list', '2018-07-10 11:24:48', '2018-07-10 11:24:48');
INSERT INTO `sys_permission` VALUES ('3', '1', '用户添加', '2', '0/1/2', 'user:add', 'button', '', '2018-07-10 11:25:40', '2018-07-10 11:25:40');
INSERT INTO `sys_permission` VALUES ('4', '1', '用户删除', '2', '0/1/2', 'user:del', 'button', '', '2018-07-10 11:27:10', '2018-07-10 11:27:10');
INSERT INTO `sys_permission` VALUES ('5', '1', '用户编辑', '2', '0/1/2', 'user:edit', 'button', '', '2018-11-17 14:54:52', '2018-07-10 11:27:36');
INSERT INTO `sys_permission` VALUES ('6', '1', '角色管理', '1', '0/1', 'role:view', 'menu', '/role/list', '2018-08-04 09:38:44', '2018-08-04 09:38:44');
INSERT INTO `sys_permission` VALUES ('7', '1', '角色添加', '6', '0/1/6', 'role:add', 'button', '', '2018-08-04 09:42:05', '2018-08-04 09:42:05');
INSERT INTO `sys_permission` VALUES ('8', '1', '角色删除', '6', '0/1/6', 'role:del', 'button', '', '2018-11-17 15:01:41', '2018-08-04 09:43:26');
INSERT INTO `sys_permission` VALUES ('9', '1', '角色编辑', '6', '0/1/6', 'role:edit', 'button', '', '2018-08-04 09:46:01', '2018-08-04 09:46:01');
INSERT INTO `sys_permission` VALUES ('10', '1', '权限管理', '1', '0/1', 'permission:view', 'menu', '/permission/list', '2018-08-04 09:48:57', '2018-08-04 09:48:57');
INSERT INTO `sys_permission` VALUES ('11', '1', '权限添加', '10', '0/1/10', 'permission:add', 'button', '', '2018-08-04 09:50:50', '2018-08-04 09:50:50');
INSERT INTO `sys_permission` VALUES ('12', '1', '权限删除', '10', '0/1/10', 'permission:del', 'button', '', '2018-08-04 09:50:50', '2018-08-04 09:50:50');
INSERT INTO `sys_permission` VALUES ('13', '1', '权限编辑', '10', '0/1/10', 'permission:edit', 'button', '', '2018-08-23 11:33:34', '2018-08-23 11:33:34');
INSERT INTO `sys_permission` VALUES ('14', '1', '测试管理', '0', '0', 'test', 'top_directory', null, '2018-10-01 14:15:32', '2018-07-10 11:24:48');
INSERT INTO `sys_permission` VALUES ('16', '1', '登录日志', '1', '0/1', 'loginLog:view', 'menu', '/loginLog/list', '2018-10-01 12:25:02', '2018-10-01 12:25:02');
INSERT INTO `sys_permission` VALUES ('17', '1', '系统日志', '1', '0/1', 'log:view', 'menu', '/log/list', '2018-10-27 16:28:50', '2018-10-27 16:28:50');
INSERT INTO `sys_permission` VALUES ('18', '1', '图标管理', '1', '0/1', 'icon:view', 'menu', '/icon/icons', '2018-10-01 12:48:42', '2018-08-23 13:15:51');
INSERT INTO `sys_permission` VALUES ('20', '1', '目录测试', '14', '0/14', 'mulutest', 'directory', null, '2018-11-17 12:47:06', '2018-11-17 12:47:06');
INSERT INTO `sys_permission` VALUES ('21', '1', '菜单测试', '20', '0/14/20', 'caidantest:view', 'menu', '/caidantest/list', '2018-11-17 14:32:57', '2018-11-17 12:49:07');
INSERT INTO `sys_permission` VALUES ('23', '1', '测试菜单', '20', '0/14/20', 'testcaidan:view', 'menu', '/testcaidan/listt', '2018-11-17 14:50:26', '2018-11-17 14:39:13');
INSERT INTO `sys_permission` VALUES ('24', '1', '测试按钮', '23', '0/14/20/23', 'testcaidan:add', 'button', null, '2018-11-17 15:05:12', '2018-11-17 15:05:12');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `permission_ids` varchar(255) DEFAULT NULL COMMENT '权限ID列表',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编号',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', ',1,18,16,6,8,9,7,17,10,13,11,12,2,5,3,4,14,20,21,23,24,', '1', '超级管理员', 'admin', '2018-11-17 15:05:48', '2018-07-10 11:19:49');
INSERT INTO `sys_role` VALUES ('2', ',14,20,21,23,24,', '1', '测试员', 'test', '2018-11-17 17:59:58', '2018-07-10 11:19:49');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(10) DEFAULT NULL COMMENT '角色ID',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(50) DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
  `state` tinyint(1) DEFAULT NULL COMMENT '状态(0：禁用，1：启用，2：锁定)',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '超级管理员', 'admin', '90de4365c537fa959193d13ad8d07665', 'XZUY77Pq41M6jaGeR2q1yMaPOrmemy6A', '1', '2018-11-17 15:19:21', '2018-07-10 11:16:24');
INSERT INTO `sys_user` VALUES ('9', '2', 'test', 'test', 'f58cb4cbc689ace5456577d913c68bfd', 'tPKGoMIgl6y16wWaoaqXyRS2N3WzmsNo', '1', '2018-11-17 15:19:38', '2018-08-11 15:31:26');
