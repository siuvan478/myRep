/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : cas

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2017-09-30 18:15:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_area
-- ----------------------------
DROP TABLE IF EXISTS `ss_area`;
CREATE TABLE `ss_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '地区名称-中文',
  `name_en` varchar(50) DEFAULT NULL COMMENT '地区名称-英文',
  `city_id` bigint(20) NOT NULL COMMENT '外键-城市编号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1=正常 0=删除/停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_area
-- ----------------------------
INSERT INTO `ss_area` VALUES ('1', '香港仔', 'Aberdeen', '1', '0');
INSERT INTO `ss_area` VALUES ('2', '金鐘', 'Admiralty', '1', '1');
INSERT INTO `ss_area` VALUES ('3', '鴨脷洲', 'Ap Lei Chau', '1', '1');
INSERT INTO `ss_area` VALUES ('4', '大浪灣', 'Big Wave Bay', '1', '1');
INSERT INTO `ss_area` VALUES ('5', '銅鑼灣', 'Causeway Bay', '1', '1');
INSERT INTO `ss_area` VALUES ('6', '中環', 'Central', '1', '1');
INSERT INTO `ss_area` VALUES ('7', '柴灣', 'Chai Wan', '1', '1');
INSERT INTO `ss_area` VALUES ('8', '數碼港', 'Cyberport', '1', '1');
INSERT INTO `ss_area` VALUES ('9', '深水灣', 'Deep Water Bay', '1', '1');
INSERT INTO `ss_area` VALUES ('10', '砲台山', 'Fortress Hill', '1', '1');
INSERT INTO `ss_area` VALUES ('11', '跑馬地', 'Happy Valley', '1', '1');
INSERT INTO `ss_area` VALUES ('12', '杏花村', 'Heng Fa Chuen', '1', '1');
INSERT INTO `ss_area` VALUES ('13', '渣甸山', 'Jardine\'s Lookout', '1', '1');
INSERT INTO `ss_area` VALUES ('14', '堅尼地城', 'Kennedy Town', '1', '1');
INSERT INTO `ss_area` VALUES ('15', '勵德村', 'Lai Tak Tsuen', '1', '1');
INSERT INTO `ss_area` VALUES ('16', '半山', 'Mid-Levels', '1', '1');
INSERT INTO `ss_area` VALUES ('17', '西半山', 'Mid-Levels West', '1', '1');
INSERT INTO `ss_area` VALUES ('18', '北角', 'North Point', '1', '1');
INSERT INTO `ss_area` VALUES ('19', '薄扶林', 'Pok Fu Lam', '1', '1');
INSERT INTO `ss_area` VALUES ('20', '鰂魚涌', 'Quarry Bay', '1', '1');
INSERT INTO `ss_area` VALUES ('21', '淺水灣', 'Repulse Bay', '1', '1');
INSERT INTO `ss_area` VALUES ('22', '西環', 'Sai Wan', '1', '1');
INSERT INTO `ss_area` VALUES ('23', '西灣河', 'Sai Wan Ho', '1', '1');
INSERT INTO `ss_area` VALUES ('24', '西營盤', 'Sai Ying Pun', '1', '1');
INSERT INTO `ss_area` VALUES ('25', '筲箕灣', 'Shau Kei Wan', '1', '1');
INSERT INTO `ss_area` VALUES ('26', '石澳', 'Shek O', '1', '1');
INSERT INTO `ss_area` VALUES ('27', '上環', 'Sheung Wan', '1', '1');
INSERT INTO `ss_area` VALUES ('28', '小西灣', 'Siu Sai Wan', '1', '1');
INSERT INTO `ss_area` VALUES ('29', '掃桿埔', 'So Kon Po', '1', '1');
INSERT INTO `ss_area` VALUES ('30', '南區', 'Southern District', '1', '1');
INSERT INTO `ss_area` VALUES ('31', '赤柱', 'Stanley', '1', '1');
INSERT INTO `ss_area` VALUES ('32', '大坑', 'Tai Hang', '1', '1');
INSERT INTO `ss_area` VALUES ('33', '大潭', 'Tai Tam', '1', '1');
INSERT INTO `ss_area` VALUES ('34', '太古', 'Taikoo', '1', '1');
INSERT INTO `ss_area` VALUES ('35', '山頂', 'The Peak', '1', '1');
INSERT INTO `ss_area` VALUES ('36', '天后', 'Tin Hau', '1', '1');
INSERT INTO `ss_area` VALUES ('37', '華富', 'Wah Fu', '1', '1');
INSERT INTO `ss_area` VALUES ('38', '灣仔', 'Wan Chai', '1', '1');
INSERT INTO `ss_area` VALUES ('39', '黃竹坑', 'Wong Chuk Hang', '1', '1');
INSERT INTO `ss_area` VALUES ('40', '黃泥涌', 'Wong Nai Chung', '1', '1');
INSERT INTO `ss_area` VALUES ('41', '柯士甸', 'Austin', '2', '1');
INSERT INTO `ss_area` VALUES ('42', '長沙灣', 'Cheung Sha Wan', '2', '1');
INSERT INTO `ss_area` VALUES ('43', '彩虹', 'Choi Hung', '2', '1');
INSERT INTO `ss_area` VALUES ('44', '鑽石山', 'Diamond Hill', '2', '1');
INSERT INTO `ss_area` VALUES ('45', '何文田', 'Ho Man Tin', '2', '1');
INSERT INTO `ss_area` VALUES ('46', '紅磡', 'Hung Hom', '2', '1');
INSERT INTO `ss_area` VALUES ('47', '佐敦', 'Jordan', '2', '1');
INSERT INTO `ss_area` VALUES ('48', '九龍灣', 'Kowloon Bay', '2', '1');
INSERT INTO `ss_area` VALUES ('49', '九龍城', 'Kowloon City', '2', '1');
INSERT INTO `ss_area` VALUES ('50', '九龍塘', 'Kownloon Tong', '2', '1');
INSERT INTO `ss_area` VALUES ('51', '觀塘', 'Kwun Tong', '2', '1');
INSERT INTO `ss_area` VALUES ('52', '荔枝角', 'Lai Chi Kok', '2', '1');
INSERT INTO `ss_area` VALUES ('53', '藍田', 'Lam Tin', '2', '1');
INSERT INTO `ss_area` VALUES ('54', '樂富', 'Lok Fu', '2', '1');
INSERT INTO `ss_area` VALUES ('55', '馬頭圍', 'Ma Tau Kok', '2', '1');
INSERT INTO `ss_area` VALUES ('56', '美孚', 'Mei Foo', '2', '1');
INSERT INTO `ss_area` VALUES ('57', '旺角', 'Mong Kok', '2', '1');
INSERT INTO `ss_area` VALUES ('58', '南昌', 'Nam Cheong', '2', '1');
INSERT INTO `ss_area` VALUES ('59', '牛池灣', 'Ngau Chi Wan', '2', '1');
INSERT INTO `ss_area` VALUES ('60', '牛頭角', 'Ngau Tau Kok', '2', '1');
INSERT INTO `ss_area` VALUES ('61', '太子', 'Prince Edward', '2', '1');
INSERT INTO `ss_area` VALUES ('62', '新蒲崗', 'San Po Kong', '2', '1');
INSERT INTO `ss_area` VALUES ('63', '秀茂坪', 'Sau Mau Ping', '2', '1');
INSERT INTO `ss_area` VALUES ('64', '深水埗', 'Sham Shui Po', '2', '1');
INSERT INTO `ss_area` VALUES ('65', '石硤尾', 'Shek Kip Mei', '2', '1');
INSERT INTO `ss_area` VALUES ('66', '奧運（大角咀）', 'Olympic (Tai Kok Tsui)', '2', '1');
INSERT INTO `ss_area` VALUES ('67', '土瓜灣', 'Tokwawan', '2', '1');
INSERT INTO `ss_area` VALUES ('68', '尖沙咀', 'Tsim Sha Tsui', '2', '1');
INSERT INTO `ss_area` VALUES ('69', '慈雲山', 'Tsz Wan Shan', '2', '1');
INSERT INTO `ss_area` VALUES ('70', '西九龍（九龍站）', 'West Kowloon (Kowloon Station)', '2', '1');
INSERT INTO `ss_area` VALUES ('71', '黃大仙', 'Wong Tai Sin', '2', '1');
INSERT INTO `ss_area` VALUES ('72', '油麻地', 'Yau Ma Tei', '2', '1');
INSERT INTO `ss_area` VALUES ('73', '油塘', 'Yau Tong', '2', '1');
INSERT INTO `ss_area` VALUES ('74', '赤鱲角', 'Chek Lap Kok', '3', '1');
INSERT INTO `ss_area` VALUES ('75', '清水灣', 'Clear Water Bay', '3', '1');
INSERT INTO `ss_area` VALUES ('76', '粉嶺', 'Fanling', '3', '1');
INSERT INTO `ss_area` VALUES ('77', '火炭', 'Fo Tan', '3', '1');
INSERT INTO `ss_area` VALUES ('78', '葵涌', 'Kwai Chung', '3', '1');
INSERT INTO `ss_area` VALUES ('79', '日出康城', 'Lohas Park', '3', '1');
INSERT INTO `ss_area` VALUES ('80', '馬鞍山', 'Ma On Shan', '3', '1');
INSERT INTO `ss_area` VALUES ('81', '馬灣', 'Ma Wan', '3', '1');
INSERT INTO `ss_area` VALUES ('82', '西貢（北）', 'Sai Kung (North)', '3', '1');
INSERT INTO `ss_area` VALUES ('83', '西貢（南）', 'Sai Kung (South)', '3', '1');
INSERT INTO `ss_area` VALUES ('84', '沙頭角', 'Sha Tau Kok', '3', '1');
INSERT INTO `ss_area` VALUES ('85', '沙田', 'Shatin', '3', '1');
INSERT INTO `ss_area` VALUES ('86', '上水', 'Sheung Shui', '3', '1');
INSERT INTO `ss_area` VALUES ('87', '大埔', 'Tai Po', '3', '1');
INSERT INTO `ss_area` VALUES ('88', '大圍', 'Tai Wai', '3', '1');
INSERT INTO `ss_area` VALUES ('89', '天水圍', 'Tin Shui Wai', '3', '1');
INSERT INTO `ss_area` VALUES ('90', '將軍澳', 'Tseung Kwan O', '3', '1');
INSERT INTO `ss_area` VALUES ('91', '青衣', 'Tsing Yi', '3', '1');
INSERT INTO `ss_area` VALUES ('92', '荃灣', 'Tsuen Wan', '3', '1');
INSERT INTO `ss_area` VALUES ('93', '屯門', 'Tuen Mun', '3', '1');
INSERT INTO `ss_area` VALUES ('94', '東涌', 'Tung Chung', '3', '1');
INSERT INTO `ss_area` VALUES ('95', '烏溪沙', 'Wu Kai Sha', '3', '1');
INSERT INTO `ss_area` VALUES ('96', '元朗', 'Yuen Long', '3', '1');

-- ----------------------------
-- Table structure for ss_box_mode
-- ----------------------------
DROP TABLE IF EXISTS `ss_box_mode`;
CREATE TABLE `ss_box_mode` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_box_mode
-- ----------------------------

-- ----------------------------
-- Table structure for ss_city
-- ----------------------------
DROP TABLE IF EXISTS `ss_city`;
CREATE TABLE `ss_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '城市名称-中文',
  `name_en` varchar(50) DEFAULT NULL COMMENT '城市名称-英文',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1=正常 0=删除/停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_city
-- ----------------------------
INSERT INTO `ss_city` VALUES ('1', '香港島', '香港島', '1');
INSERT INTO `ss_city` VALUES ('2', '九龍', '九龍', '1');
INSERT INTO `ss_city` VALUES ('3', '新界', '新界', '1');

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
  `login_name` varchar(50) NOT NULL COMMENT '用户名',
  `name` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(16) NOT NULL COMMENT '盐值',
  `roles` varchar(255) DEFAULT NULL COMMENT '角色',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 1=正常 0=停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO `ss_user` VALUES ('1', 'siuvan', '夏红凡', '9be14abf6c620807ab884296a1299ff6693f3b5a', '5fd5ca5a21799a8f', null, '295636011@qq.com', null, '2017-09-29 13:05:12', '2017-09-30 18:07:35', '1');
INSERT INTO `ss_user` VALUES ('2', 'liuwenyang', '刘文扬', '23f1d525150bde0e212c8b2be3fe0d08ccf678f8', 'aa605a2cdcb4cda7', null, 'liuwenyang@qq.com', null, '2017-09-29 13:45:50', '2017-09-29 13:45:50', '1');
