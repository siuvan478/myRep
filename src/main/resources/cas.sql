/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : cas

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2017-10-24 09:03:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_address
-- ----------------------------
DROP TABLE IF EXISTS `ss_address`;
CREATE TABLE `ss_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `city_id` varchar(10) NOT NULL COMMENT '城市',
  `area_id` varchar(10) NOT NULL COMMENT '区域',
  `address` varchar(100) DEFAULT NULL COMMENT '具体地址',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=正常 0=失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_address
-- ----------------------------
INSERT INTO `ss_address` VALUES ('1', '1', '1', '快手镇老铁村', '1', '1');

-- ----------------------------
-- Table structure for ss_appoint_fee
-- ----------------------------
DROP TABLE IF EXISTS `ss_appoint_fee`;
CREATE TABLE `ss_appoint_fee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `addition` int(11) NOT NULL COMMENT '用户ID',
  `discount` decimal(10,2) NOT NULL COMMENT '优惠后费用',
  `common` decimal(10,2) NOT NULL COMMENT '正常费用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_appoint_fee
-- ----------------------------

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
-- Table structure for ss_box_record
-- ----------------------------
DROP TABLE IF EXISTS `ss_box_record`;
CREATE TABLE `ss_box_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `service_id` bigint(20) NOT NULL COMMENT '外键-服务ID',
  `type` tinyint(4) NOT NULL COMMENT '类型 1=存 2=取',
  `appointment_time` datetime NOT NULL COMMENT '预约时间',
  `cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '费用 周日免费,其他收费',
  `full_cost` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否未全价 0=否 1=是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '记录状态 0=无效/删除 1=等待提货/收货 1=已收货/提货',
  `picture1` varchar(255) DEFAULT NULL COMMENT '图1',
  `picture2` varchar(255) DEFAULT NULL COMMENT '图2',
  `picture3` varchar(255) DEFAULT NULL COMMENT '图3',
  `picture4` varchar(255) DEFAULT NULL COMMENT '图4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_box_record
-- ----------------------------
INSERT INTO `ss_box_record` VALUES ('4', '0', '1', '1', '2017-10-20 16:00:00', '60.00', '0', '2017-10-19 16:55:54', '1', null, null, null, null);
INSERT INTO `ss_box_record` VALUES ('5', '0', '1', '1', '2017-10-20 16:00:00', '60.00', '0', '2017-10-19 17:00:07', '1', null, null, null, null);
INSERT INTO `ss_box_record` VALUES ('6', '1', '1', '1', '2017-10-20 16:00:00', '60.00', '0', '2017-10-20 09:50:55', '1', null, null, null, null);
INSERT INTO `ss_box_record` VALUES ('7', '1', '7', '1', '2017-10-29 09:00:00', '0.00', '0', '2017-10-24 07:39:07', '1', null, null, null, null);

-- ----------------------------
-- Table structure for ss_box_service
-- ----------------------------
DROP TABLE IF EXISTS `ss_box_service`;
CREATE TABLE `ss_box_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `address_id` bigint(20) NOT NULL COMMENT '外键-地址',
  `product_id` bigint(20) NOT NULL COMMENT '外键-产品类型',
  `scale_id` bigint(20) NOT NULL COMMENT '外键-产品规格',
  `cycle` tinyint(4) NOT NULL COMMENT '周期 1=1个月 2=3个月 4=6个月 4=12个月',
  `flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '服务柜状态 0=闲置 1=正在使用',
  `start_time` datetime NOT NULL COMMENT '服务开始时间',
  `end_time` datetime NOT NULL COMMENT '服务截止时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '服务柜状态 0=无效/删除 1=等待收货 2=已收货 3=等待取货 4=已取货',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_box_service
-- ----------------------------
INSERT INTO `ss_box_service` VALUES ('4', '1', '1', '1', '1', '1', '1', '2017-10-19 16:00:00', '2017-10-20 10:00:00', '2017-10-19 16:55:54', '2017-10-19 16:55:54', '0');
INSERT INTO `ss_box_service` VALUES ('5', '1', '1', '1', '1', '1', '1', '2017-10-20 16:00:00', '2017-11-20 16:00:00', '2017-10-19 17:00:07', '2017-10-19 17:00:07', '1');
INSERT INTO `ss_box_service` VALUES ('6', '1', '1', '1', '1', '1', '1', '2017-10-20 16:00:00', '2017-11-20 16:00:00', '2017-10-20 09:50:54', '2017-10-20 09:50:54', '1');
INSERT INTO `ss_box_service` VALUES ('7', '1', '1', '5', '8', '4', '1', '2017-10-29 09:00:00', '2018-10-29 09:00:00', '2017-10-24 07:39:07', '2017-10-24 07:39:07', '1');

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
-- Table structure for ss_config
-- ----------------------------
DROP TABLE IF EXISTS `ss_config`;
CREATE TABLE `ss_config` (
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '全价次数',
  `common_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '预约费用(全价)',
  `discount_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠后费用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_config
-- ----------------------------
INSERT INTO `ss_config` VALUES ('5', '80.00', '20.00');

-- ----------------------------
-- Table structure for ss_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_order`;
CREATE TABLE `ss_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `address_id` bigint(20) NOT NULL COMMENT '外键-取货地址',
  `product_id` bigint(20) NOT NULL COMMENT '外键-产品类型',
  `scale_id` tinyint(20) NOT NULL COMMENT '外键-产品规格',
  `cycle` tinyint(4) NOT NULL COMMENT '周期 1=1个月 2=3个月 3=6个月 4=12个月',
  `quantity` int(10) NOT NULL DEFAULT '1' COMMENT '数量',
  `total_price` decimal(10,2) NOT NULL COMMENT '总价格',
  `order_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '订单时间',
  `effective_time` datetime DEFAULT NULL COMMENT '订单生效时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态 0=已取消 1=已下单 2=已生效(付款)',
  `callback_id` bigint(20) DEFAULT NULL COMMENT '回调ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '订单审核备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_order
-- ----------------------------
INSERT INTO `ss_order` VALUES ('1', '1', 'TODO-ORDER-NO-01', '1', '1', '1', '1', '1', '160.00', '2017-10-19 16:55:54', null, '1', '1', null);
INSERT INTO `ss_order` VALUES ('2', '1', 'TODO-ORDER-NO-01', '1', '1', '1', '1', '1', '160.00', '2017-10-19 17:00:07', null, '1', '1', null);
INSERT INTO `ss_order` VALUES ('3', '1', '20171020095055260794624', '1', '1', '1', '1', '1', '160.00', '2017-10-20 09:50:55', null, '1', '1', null);
INSERT INTO `ss_order` VALUES ('4', '1', '20171024073907545794624', '1', '5', '8', '4', '1', '600.00', '2017-10-24 07:39:07', '2017-10-24 07:39:29', '2', '7', '');

-- ----------------------------
-- Table structure for ss_product
-- ----------------------------
DROP TABLE IF EXISTS `ss_product`;
CREATE TABLE `ss_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `product_name` varchar(50) NOT NULL COMMENT '產品名',
  `product_no` varchar(20) DEFAULT NULL COMMENT '編號',
  `feature` varchar(200) DEFAULT NULL COMMENT '特征',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=正常 0=删除/停用',
  `image` varchar(200) DEFAULT NULL COMMENT '照片',
  `create_time` datetime NOT NULL COMMENT '創建時間',
  `update_time` datetime NOT NULL COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_product
-- ----------------------------
INSERT INTO `ss_product` VALUES ('1', '衣服', 'FM-0001', '- 免費送收服務\r\n- 特定存取點無限次存取\r\n- 每月4日特定送收時間\r\n- 恆溫及濕度控制存放', '換季啲衫點算好？ FREEMAN教您要幾多用幾多！FREEMAN衣櫥特有恆溫及濕度控制存放，存取點可無限次存放，想著邊件衫話咁易！空間管理做得好，生活自然由我話事！', '1', '/usr/tmp/upload/aefb636da24f4d61b3be96a5346e5b0b.png', '2017-10-10 14:45:59', '2017-10-18 09:52:41');
INSERT INTO `ss_product` VALUES ('2', '單車儲存', 'FM-0002', '- 免費送收服務\r\n- 特定存取點無限次存取\r\n- 每月4日特定送收時間\r\n- 恆溫及濕度控制存放', '告别迷你倉！ FREEMAN教您要幾多用幾多！FREEMAN 單車儲存，無限次存取服務，唔怕有入無出，幫您省卻家中空間，想踩車先嚟拎。空間管理做得好，生活自然由我話事！', '1', '/usr/tmp/upload/97bffefb5148451893e3d89bf0ca977a.png', '2017-10-10 14:48:33', '2017-10-18 09:52:51');
INSERT INTO `ss_product` VALUES ('3', '自由箱', 'FM-0003', '- 免費送箱服務\r\n- 特定存取點無限次存取\r\n- 每月4日特定送箱時間\r\n- 恆溫及濕度控制存放', '告别迷你倉！ FREEMAN教您要幾多用幾多！FreeBOX自由箱可以填滿每吋空間，每個箱分類儲存，記低入面放咗啲咩。空間管理做得好，生活自然由我話事！', '1', '/usr/tmp/upload/2ca3defa9c234958876ff66f20b9b495.png', '2017-10-10 14:50:13', '2017-10-18 09:53:03');
INSERT INTO `ss_product` VALUES ('4', '儲物櫃', 'FM-0004', '- 每個儲物櫃獨立指紋鎖\r\n- 最大尺寸儲物櫃可記錄兩組指紋，供二人共用\r\n- 租用者可親身無限次存取\r\n- 全天候360監察系統，高度保安\r\n- 獨立VIP空間，恆溫及濕度控制環境', '告别迷你倉！ FREEMAN教您要幾多用幾多！FREEMAN 儲物櫃備有指紋鎖，保安度極高，而且裝有360監察系統，只有租用者可開啟。儲物櫃有3個尺寸，最大尺寸儲物櫃可二人共用，足夠儲存滑浪板或高爾夫球桿套裝等運動用品。空間管理做得好，生活自然由我話事！', '1', '/usr/tmp/upload/0c28b711dc4646afacb9eb7bbdabd094.png', '2017-10-10 14:52:17', '2017-10-18 09:53:33');
INSERT INTO `ss_product` VALUES ('5', '信箱', 'FM-0005', '- 專屬私人商業地址\r\n- 收信通知服務\r\n- 尊貴VIP空間，恆溫及濕度控制環境', '告别迷你倉！ FREEMAN教您要幾多用幾多！FREEMAN 信箱為客戶提供專屬商業地址，讓中小企或營運獨立生意人士更加方便。空間管理做得好，生活自然由我話事！\r\n', '1', '/usr/tmp/upload/f82c9c78c6be431e8f8bf99aa1657d4b.png', '2017-10-10 14:54:34', '2017-10-18 09:53:44');
INSERT INTO `ss_product` VALUES ('6', '行李箱', 'FM-0006', '- 免費存取服務\r\n- 特定存取點無限次存取\r\n- 每月4日特定存取時間\r\n- 恆溫及濕度控制存放', '告别迷你倉！ FREEMAN教您要幾多用幾多！FREEMAN 幫您寄存行李箱，有需要時預約取回，就可以出發去玩啦。空間管理做得好，生活自然由我話事！\r\n', '1', '/usr/tmp/upload/79f3033e6cd4492c896513381caed075.png', '2017-10-10 14:55:47', '2017-10-18 09:53:56');
INSERT INTO `ss_product` VALUES ('7', '文件箱', 'FM-0007', '- 免費送箱服務\r\n- 特定存取點無限次存取\r\n- 每月4日特定送箱時間\r\n- 恆溫及濕度控制存放', '告别迷你倉！ FREEMAN教您要幾多用幾多！FREEMAN 文件箱可以把文件分類儲存，記低入面放咗啲咩。空間管理做得好，生活自然由我話事！', '1', '/usr/tmp/upload/b8d9566291fa436f9c5d41a13f4a5bc8.png', '2017-10-10 14:58:31', '2017-10-18 09:54:06');
INSERT INTO `ss_product` VALUES ('8', '大型物件', 'FM-0008', '无', '无', '1', '/usr/tmp/upload/22adea85ffd045db912b365e36c9c0d5.png', '2017-10-10 15:00:28', '2017-10-18 09:54:17');

-- ----------------------------
-- Table structure for ss_scale
-- ----------------------------
DROP TABLE IF EXISTS `ss_scale`;
CREATE TABLE `ss_scale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `scale` varchar(50) NOT NULL COMMENT '尺寸',
  `need_quote` tinyint(4) NOT NULL DEFAULT '0' COMMENT '需要报价 0=不需要(默认) 1=需要',
  `twelve_month_price` decimal(10,2) DEFAULT NULL COMMENT '购买1年单价',
  `six_month_price` decimal(10,2) DEFAULT NULL COMMENT '购买6个月单价',
  `three_month_price` decimal(10,2) DEFAULT NULL COMMENT '购买3个月单价',
  `one_month_price` decimal(10,2) DEFAULT NULL COMMENT '购买1个月单价',
  `num` int(10) NOT NULL DEFAULT '1' COMMENT '库存数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=正常 0=删除/停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_scale
-- ----------------------------
INSERT INTO `ss_scale` VALUES ('1', '1', '90 x 60 x 30 cm (約10件T恤)', '0', '65.00', '75.00', '85.00', '100.00', '100', '1', '2017-10-10 14:47:07', '2017-10-13 17:12:34');
INSERT INTO `ss_scale` VALUES ('2', '1', '90 x 60 x 80 cm (約30件T恤)', '0', '195.00', '225.00', '255.00', '300.00', '100', '1', '2017-10-10 14:47:49', '2017-10-13 17:10:48');
INSERT INTO `ss_scale` VALUES ('3', '2', '无规格', '0', '247.00', '285.00', '323.00', '380.00', '100', '1', '2017-10-10 14:49:15', '2017-10-13 17:12:51');
INSERT INTO `ss_scale` VALUES ('4', '3', '60 x 35.5 x 40 cm', '0', '52.00', '60.00', '68.00', '80.00', '1', '1', '2017-10-10 14:51:19', '2017-10-10 14:51:22');
INSERT INTO `ss_scale` VALUES ('5', '4', '50 x 50 x 37.5 cm', '0', '195.00', '225.00', '255.00', '300.00', '1', '1', '2017-10-10 14:52:51', '2017-10-10 14:52:53');
INSERT INTO `ss_scale` VALUES ('6', '4', '100 x 50 x 37.5 cm', '0', '248.63', '337.50', '382.50', '450.00', '1', '1', '2017-10-10 14:53:27', '2017-10-10 14:53:29');
INSERT INTO `ss_scale` VALUES ('7', '4', '150 x 50 x 39 cm (記錄2組指紋，可供2人使用)', '0', '357.50', '412.50', '467.50', '550.00', '1', '1', '2017-10-10 14:54:00', '2017-10-10 14:54:02');
INSERT INTO `ss_scale` VALUES ('8', '5', '12 x 24 x 34 cm', '0', '50.00', '60.00', '0.00', '0.00', '1', '1', '2017-10-10 14:55:15', '2017-10-10 14:55:17');
INSERT INTO `ss_scale` VALUES ('9', '6', '长+宽+高+不超过 150 cm\r\n净重不超过 25kg', '0', '52.00', '60.00', '68.00', '80.00', '1', '1', '2017-10-10 14:56:43', '2017-10-10 14:56:45');
INSERT INTO `ss_scale` VALUES ('10', '6', '长+宽+高+不超过 150-175 cm\r\n净重不超过 25kg', '0', '55.25', '63.75', '72.25', '85.00', '1', '1', '2017-10-10 14:57:17', '2017-10-10 14:57:19');
INSERT INTO `ss_scale` VALUES ('11', '6', '长+宽+高+不超过 175-200 cm\r\n净重不超过 25kg', '0', '58.50', '67.50', '76.50', '90.00', '1', '1', '2017-10-10 14:57:50', '2017-10-10 14:57:52');
INSERT INTO `ss_scale` VALUES ('12', '7', '41 x 34 x 27 cm', '0', '26.00', '30.00', '34.00', '40.00', '1', '1', '2017-10-10 14:59:06', '2017-10-10 14:59:08');
INSERT INTO `ss_scale` VALUES ('13', '8', '	\r\n长+宽+高+不超过 150 cm\r\n净重不超过 25kg', '0', '52.00', '60.00', '68.00', '80.00', '1', '1', '2017-10-10 15:01:04', '2017-10-10 15:01:06');
INSERT INTO `ss_scale` VALUES ('14', '8', '长+宽+高+不超过 150-175 cm\r\n净重不超过 25kg', '0', '55.25', '63.75', '72.25', '85.00', '1', '1', '2017-10-10 15:01:38', '2017-10-10 15:01:40');
INSERT INTO `ss_scale` VALUES ('15', '8', '长+宽+高+不超过 175-200 cm\r\n净重不超过 25kg', '0', '58.50', '67.50', '76.50', '90.00', '1', '1', '2017-10-10 15:02:03', '2017-10-10 15:02:05');
INSERT INTO `ss_scale` VALUES ('16', '8', '长+宽+高+超过 200cm\r\n净重不超过 25kg', '1', '0.00', '0.00', '0.00', '0.00', '1', '1', '2017-10-10 15:02:24', '2017-10-10 15:02:26');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO `ss_user` VALUES ('1', '295636011@qq.com', '夏红凡', '7d4b0bd4179443be783d1231a63f9162d8a2cc15', '842ea2ec22b2d20c', null, '295636011@qq.com', null, '2017-09-29 13:05:12', '2017-10-18 11:33:15', '1');
INSERT INTO `ss_user` VALUES ('2', 'liuwenyang@qq.com', '刘文扬', '23f1d525150bde0e212c8b2be3fe0d08ccf678f8', 'aa605a2cdcb4cda7', null, 'liuwenyang@qq.com', null, '2017-09-29 13:45:50', '2017-10-16 08:32:23', '1');
INSERT INTO `ss_user` VALUES ('3', '380612645@qq.com', null, '6f84864e81331ba171b04e7885ea00ffae7e0274', '167d7f6726f29eff', null, null, null, '2017-10-18 11:33:58', '2017-10-18 11:33:58', '1');
INSERT INTO `ss_user` VALUES ('5', '160402281@qq.com', null, '771a276a34ded4b381872bfe6141c22c8088a09e', 'b96297af17ad4d03', null, null, null, '2017-10-19 18:39:39', '2017-10-19 18:39:39', '1');
INSERT INTO `ss_user` VALUES ('6', '470508081@qq.com', null, 'df478645bd3538d264a30e91875d8a67a37e8782', 'aa9ef7bb5cfae4e3', null, null, null, '2017-10-19 22:33:01', '2017-10-19 22:33:01', '1');
