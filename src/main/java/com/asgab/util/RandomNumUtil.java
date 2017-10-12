package com.asgab.util;

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