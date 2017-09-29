/*
Navicat MySQL Data Transfer

Source Server         : cas
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : rep

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-09-29 23:29:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_area
-- ----------------------------
DROP TABLE IF EXISTS `ss_area`;
CREATE TABLE `ss_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_code` varchar(10) NOT NULL COMMENT '外键-城市编号',
  `code` varchar(10) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '地区名称-中文',
  `name_en` varchar(50) DEFAULT NULL COMMENT '地区名称-英文',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_area
-- ----------------------------

-- ----------------------------
-- Table structure for ss_city
-- ----------------------------
DROP TABLE IF EXISTS `ss_city`;
CREATE TABLE `ss_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '城市名称-中文',
  `name_en` varchar(50) DEFAULT NULL COMMENT '城市名称-英文',
  `code` varchar(10) NOT NULL COMMENT '城市编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_city
-- ----------------------------

-- ----------------------------
-- Table structure for ss_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_order`;
CREATE TABLE `ss_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `address_id` bigint(20) NOT NULL COMMENT '外键-取货地址',
  `service_id` bigint(20) NOT NULL COMMENT '外键-服务规格',
  `quantity` int(10) NOT NULL COMMENT '数量',
  `total_price` decimal(10,2) NOT NULL COMMENT '总价格',
  `order_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '订单时间',
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '存储开始时间',
  `expire_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '到期时间',
  `storage_position` varchar(255) DEFAULT NULL COMMENT '存储位置(图片)',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态 0=已取消 1=已下单 2=已收件 3=已生效/储存 4=已到期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_order
-- ----------------------------

-- ----------------------------
-- Table structure for ss_pickup_address
-- ----------------------------
DROP TABLE IF EXISTS `ss_pickup_address`;
CREATE TABLE `ss_pickup_address` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_code` varchar(10) NOT NULL COMMENT '城市',
  `area_code` varchar(10) NOT NULL COMMENT '区域',
  `address` varchar(100) DEFAULT NULL COMMENT '具体地址',
  `special_instruction` varchar(100) DEFAULT NULL COMMENT '特殊服务',
  `need_stair` tinyint(4) DEFAULT NULL COMMENT '需要楼梯',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_pickup_address
-- ----------------------------

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(50) NOT NULL COMMENT '登陆名',
  `name` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `salt` varchar(16) NOT NULL COMMENT '盐值',
  `roles` varchar(255) DEFAULT NULL COMMENT '角色',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `register_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=正常 0=停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_user
-- ----------------------------
