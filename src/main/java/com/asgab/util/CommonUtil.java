package com.asgab.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

    public static String i18nStr(HttpServletRequest request, String zh, String en) {
        return "zh".equalsIgnoreCase(request.getLocale().getLanguage()) ? zh : en;
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }


    /**
     * @param file
     * @return 返回 fileName,showName,filePath
     */
    public static JSONObject saveFile(MultipartFile file, String uploadDir) {
        JSONObject jsonObject = null;
        String filePath = uploadDir + formatDate(new Date(), "yyyy-MM-dd");
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                int fileNameLegth = file.getOriginalFilename().lastIndexOf(".");
                String showName = file.getOriginalFilename().substring(0, fileNameLegth);
                String suffix = file.getOriginalFilename().substring(fileNameLegth, file.getOriginalFilename().length());
                String fileName = UUID.randomUUID().toString();
                // 生成当前日期文件夹
                // 转存文件  
                file.transferTo(new File(filePath + "/" + fileName + suffix));
                jsonObject = new JSONObject();
                jsonObject.put("fileName", fileName + suffix);
                jsonObject.put("showName", showName + suffix);
                jsonObject.put("filePath", filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }


    public static Double formatNumber(String num, int decDigit) {
        return formatNumber(Double.parseDouble(num), decDigit);
    }

    public static Double formatNumber(Double num, int decDigit) {
        String format = "0.";
        for (int i = 0; i < decDigit; i++) {
            format += "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return Double.parseDouble(df.format(num));
    }

}
