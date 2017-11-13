
--2017年11月6日 21:08:14
ALTER TABLE `ss_order`
ADD COLUMN `belong_no`  varchar(50) NULL DEFAULT NULL COMMENT '分配编号' AFTER `remark`;

ALTER TABLE `ss_box_service`
ADD COLUMN `belong_no`  varchar(50) NULL DEFAULT NULL COMMENT '分配编号' AFTER `create_time`;

/*2017年11月13日10:32:21*/
ALTER TABLE `ss_box_service`
ADD COLUMN `order_id`  bigint(20) NULL DEFAULT -1 COMMENT '订单ID' AFTER `address_id`;