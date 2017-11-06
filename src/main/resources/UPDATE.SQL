
--2017年11月6日 21:08:14
ALTER TABLE `ss_order`
ADD COLUMN `belong_no`  varchar(50) NULL DEFAULT NULL COMMENT '分配编号' AFTER `remark`;

ALTER TABLE `ss_box_service`
ADD COLUMN `belong_no`  varchar(50) NULL DEFAULT NULL COMMENT '分配编号' AFTER `create_time`;