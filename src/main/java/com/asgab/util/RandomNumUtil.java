package com.asgab.util;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月29日 下午 6:13
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class RandomNumUtil {

    /**
     * 生成随机数
     *
     * @param digit 位数
     * @return 6位字符串
     */
    public static String getRandNumber(int digit) {
        StringBuffer numStr = new StringBuffer();
        int num;
        for (int i = 0; i < digit; i++) {
            // Math.random() 随机出0-1之间的实数，返回值是一个double 类型的
            num = (int) (Math.random() * 10);
            numStr.append(String.valueOf(num));
        }
        return numStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandNumber(3));
    }
}