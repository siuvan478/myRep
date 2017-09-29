package com.asgab.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.asgab.entity.CurrMaster;

public class CommonUtil {
	
	public static final Map<Character,String> STATUSES_ZH = new HashMap<Character,String>();
	public static final Map<Character,String> STATUSES_EN = new HashMap<Character,String>();
	
	public static final Character STATUS_NEW = '1';
	public static final Character STATUS_REJECT = '2';
	public static final Character STATUS_CHECK= '3';
	public static final Character STATUS_FINANCE_CONFIRM = '4';
	public static final Character STATUS_OPS_CONFIRM = '5';
	public static final Character STATUS_DONE = '6';
	
	static{
		//N R C F O D
		STATUSES_EN.put('1', "New");
		STATUSES_EN.put('2', "Rejected");
		STATUSES_EN.put('3', "Checked");
		STATUSES_EN.put('4', "Finance Confirm");
		STATUSES_EN.put('5', "Ops Confirm");
		STATUSES_EN.put('6', "Done");
		STATUSES_ZH.put('1', "新建");
		STATUSES_ZH.put('2', "拒绝");
		STATUSES_ZH.put('3', "检查");
		STATUSES_ZH.put('4', "财务确认");
		STATUSES_ZH.put('5', "AM确认");
		STATUSES_ZH.put('6', "完成");
	}
	
	public static String i18nStr(HttpServletRequest request, String zh,String en) {
		return "zh".equalsIgnoreCase(request.getLocale().getLanguage()) ? zh: en;
	}
	
	public static String formatDate(Date date){
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDate(Date date,String format){
		if(date!=null){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
		}else{
			return "";
		}
	}
	
	
	/**
	 * @param file
	 * @return 返回 fileName,showName,filePath
	 */
	public static JSONObject saveFile(MultipartFile file,String uploadDir){
		JSONObject jsonObject = null;
		String filePath = uploadDir + formatDate(new Date(),"yyyy-MM-dd");  
		File fileDir = new File(filePath);
		if(!fileDir.exists()){
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
                file.transferTo(new File(filePath +"/" + fileName+suffix));
                jsonObject = new JSONObject();
                jsonObject.put("fileName", fileName+suffix);
                jsonObject.put("showName", showName+suffix);
                jsonObject.put("filePath", filePath);
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return jsonObject;  
	}
	
	// TODO 
	/**
	 * 其他币种转人民币
	 * @param currency 货币
	 * @param money
	 * @return
	 */
	public static Double transferMontyToRMB(List<CurrMaster> currMasters ,String currency,Double money){
		return transferMontyToBigDecimalRMB(currMasters,currency,money).doubleValue();
	}
	
	public static BigDecimal transferMontyToBigDecimalRMB(List<CurrMaster> currMasters,String currency,Double money){
		for(int i = 0 ; i <currMasters.size();i++){
			if(currency.equalsIgnoreCase(currMasters.get(i).getCurr_code())){
				return new BigDecimal(money).divide(new BigDecimal(currMasters.get(i).getCurr_rate()),2,BigDecimal.ROUND_HALF_UP);
			}
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 
	 * @param num
	 * @param decDigit小数位数
	 * @return
	 */
	public static Double formatNumber(String num,int decDigit){
		return formatNumber(Double.parseDouble(num),decDigit);
	}
	
	public static Double formatNumber(Double num,int decDigit){
		String format = "0.";
		for(int i=0;i<decDigit;i++){
			format+="0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return Double.parseDouble(df.format(num));
	}
	
}
