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
