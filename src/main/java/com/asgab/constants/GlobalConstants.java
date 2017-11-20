package com.asgab.constants;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 全局常量
 */
public class GlobalConstants {

    /**
     * 订单状态
     */
    public static final Map<String, String> ORDER_STATUS_ZH = new TreeMap<>();
    public static final Map<String, String> ORDER_STATUS_EN = new TreeMap<>();

    /**
     * 购买周期
     */
    public static final Map<String, String> PRODUCT_CYCLE_ZH = new TreeMap<>();
    public static final Map<String, String> PRODUCT_CYCLE_EN = new TreeMap<>();

    /**
     * 文件柜服务开关
     */
    public static final Map<String, String> BOX_SERVICE_FLAG_ZH = new TreeMap<>();
    public static final Map<String, String> BOX_SERVICE_FLAG_EN = new TreeMap<>();

    /**
     * 文件柜服务状态
     */
    public static final Map<String, String> BOX_SERVICE_STATUS_ZH = new TreeMap<>();
    public static final Map<String, String> BOX_SERVICE_STATUS_EN = new TreeMap<>();

    static {
        //===
        ORDER_STATUS_ZH.put("0", "已取消");
        ORDER_STATUS_ZH.put("1", "新訂單");
        ORDER_STATUS_ZH.put("2", "已生效");
        ORDER_STATUS_EN.put("0", "Cancel");
        ORDER_STATUS_EN.put("1", "New");
        ORDER_STATUS_EN.put("2", "Valid");
        //===
        PRODUCT_CYCLE_ZH.put("1", "1個月");
        PRODUCT_CYCLE_ZH.put("2", "3個月");
        PRODUCT_CYCLE_ZH.put("3", "6個月");
        PRODUCT_CYCLE_ZH.put("4", "12個月");
        PRODUCT_CYCLE_EN.put("1", "1/months");
        PRODUCT_CYCLE_EN.put("2", "3/months");
        PRODUCT_CYCLE_EN.put("3", "6/months");
        PRODUCT_CYCLE_EN.put("4", "12/months");
        //===
        BOX_SERVICE_FLAG_ZH.put("0", "閒置");
        BOX_SERVICE_FLAG_ZH.put("1", "使用中");
        BOX_SERVICE_FLAG_EN.put("0", "Free");
        BOX_SERVICE_FLAG_EN.put("1", "Using");
        //==
        BOX_SERVICE_STATUS_ZH.put(ServiceStatus.WAIT_FOR_SAVE.toString(), "等待收貨");
        BOX_SERVICE_STATUS_ZH.put(ServiceStatus.SAVED.toString(), "已收貨");
        BOX_SERVICE_STATUS_ZH.put(ServiceStatus.WAIT_FOR_TAKE.toString(), "等待提貨");
        BOX_SERVICE_STATUS_ZH.put(ServiceStatus.TAKEN.toString(), "已提貨");
        BOX_SERVICE_STATUS_ZH.put(ServiceStatus.EXPIRY.toString(), "已過期");
        BOX_SERVICE_STATUS_EN.put(ServiceStatus.WAIT_FOR_SAVE.toString(), "Wait for save");
        BOX_SERVICE_STATUS_EN.put(ServiceStatus.SAVED.toString(), "Saved");
        BOX_SERVICE_STATUS_EN.put(ServiceStatus.WAIT_FOR_TAKE.toString(), "Wait for take");
        BOX_SERVICE_STATUS_EN.put(ServiceStatus.TAKEN.toString(), "Taken");
        BOX_SERVICE_STATUS_EN.put(ServiceStatus.EXPIRY.toString(), "Invalid");
    }

    /**
     * 极光推送模板
     */
    public static class JPushMsgTemplate {
        //您购买的{信箱}({12 x 24 x 34 cm})已生效,请耐心等待工作人员上门取货,如有疑问请联系客服
        public static final String order_success_message = "您購買的{0}({1})已生效,屆時工作人員會上門取貨,如有疑問請聯絡客服";
        //您预约的{文件柜}服务将于{今天}进行,如需更改请联系客服
        public static final String schedule_message = "您預約的{0}服務將於{1}進行,如需更改時間,請聯絡客服";
    }

    /**
     * 角色 1=系统管理员 2=普通用户
     */
    public static class Role {
        public static final Integer SYS_ADMIN = 1;
        public static final Integer COMMON_USER = 2;
    }

    /**
     * 是/否
     */
    public static class YesOrNo {
        public static final Integer NO = 0;
        public static final Integer YES = 1;
    }

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

        public static boolean valid(Integer type) {
            return SAVE.equals(type) || TAKE.equals(type);
        }
    }

    /**
     * 订单状态 0=取消/无效 1=新订单 2=已支付
     */
    public static class OrderStatus {
        public static final Integer CANCEL = 0;
        public static final Integer ORDER = 1;
        public static final Integer PAYMENT = 2;

        public static boolean validAuditStatus(Integer status) {
            return CANCEL.equals(status) || PAYMENT.equals(status);
        }

        public static String getLabelClass(Integer status) {
            if (CANCEL.equals(status)) return "label-default";
            else if (ORDER.equals(status)) return "label-info";
            else if (PAYMENT.equals(status)) return "label-success";
            return "";
        }
    }

    /**
     * 服务柜开关 0=空闲 1=正在使用
     */
    public static class BoxFlag {
        public static final Integer FREE = 0;
        public static final Integer USE = 1;
    }

    /**
     * 服务柜状态 0=无效/删除 1=等待收货 2=已收货 3=等待取货 4=已取货
     */
    public static class ServiceStatus {
        public static final Integer INVALID = 0;
        public static final Integer WAIT_FOR_SAVE = 1;
        public static final Integer SAVED = 2;
        public static final Integer WAIT_FOR_TAKE = 3;
        public static final Integer TAKEN = 4;
        public static final Integer EXPIRY = 9;
    }

    /**
     * 记录状态 0=无效/删除 1=等待提货/收货 2=已收货/提货
     */
    public static class RecordStatus {
        public static final Integer INVALID = 0;
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
