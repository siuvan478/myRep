package com.asgab.constants;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * 全局常量
 */
public class GlobalConstants {

    /**
     * 状态 1=正常 0=失效/无用
     */
    public static class Status {
        public static final Integer INVALID = 0;
        public static final Integer NORMAL = 1;
    }

    /**
     * 服务记录类型 1=存 2=取
     */
    public static class RecordType {
        public static final Integer SAVE = 1;
        public static final Integer TAKE = 2;
    }

    /**
     * 订单状态 0=取消/无效 1=新订单 2=已支付
     */
    public static class OrderStatus {
        public static final Integer CANCEL = 0;
        public static final Integer ORDER = 1;
        public static final Integer PAYMENT = 2;
    }

    /**
     * 服务柜开关 0=空闲 1=正在使用
     */
    public static class BoxFlag {
        public static final Integer FREE = 0;
        public static final Integer USE = 1;
    }

    /**
     * 服务柜状态 0=已过期 1=等待收货 2=已收货 3=等待取货 4=已取货
     */
    public static class ServiceStatus {
        public static final Integer EXPIRY = 0;
        public static final Integer WAIT_FOR_SAVE = 1;
        public static final Integer SAVED = 2;
        public static final Integer WAIT_FOR_TAKE = 3;
        public static final Integer TAKEN = 4;
    }

    /**
     * 记录状态 1=等待提货/收货 1=已收货/提货
     */
    public static class RecordStatus {
        public static final Integer WAITING = 1;
        public static final Integer COMPLETED = 2;
    }

    /**
     * 产品购买周期
     */
    public enum CycleEnum {
        ONE_MONTHLY(1, 1, "一个月"),
        THREE_MONTHLY(2, 3, "3个月"),
        SIX_MONTHLY(3, 6, "6个月"),
        TWELVE_MONTHLY(4, 12, "12个月");

        public Integer code;
        public Integer value;
        public String desc;

        public static boolean validCycle(Integer cycle) {
            List<CycleEnum> enumList = EnumUtils.getEnumList(CycleEnum.class);
            for (CycleEnum ce : enumList) {
                if (ce.getCode().equals(cycle)) {
                    return true;
                }
            }
            return false;
        }

        public static CycleEnum getCycleEnum(int code) {
            List<CycleEnum> enumList = EnumUtils.getEnumList(CycleEnum.class);
            for (CycleEnum ce : enumList) {
                if (ce.getCode().equals(code)) {
                    return ce;
                }
            }
            return null;
        }

        CycleEnum(Integer code, Integer value, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
}
