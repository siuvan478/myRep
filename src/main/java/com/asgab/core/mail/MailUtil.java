package com.asgab.core.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import freemarker.template.TemplateException;

/**
 * 邮件发送工具类
 */
public class MailUtil {
	
	public static void main(String[] args) throws IOException, TemplateException, MessagingException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tradeNo", "20151126151055");
		map.put("uploadDate", "2015-11-26 18:47:10");
		map.put("custUsername", "baidu-iclick-hk");
		
		map.put("custName", "香港XXXXXX有限公司");
		map.put("currency", "HKD");
		map.put("rzjeLowercase", "");
		map.put("rzjeCapital", "叁仟元整");
		map.put("item", "年费");
		map.put("rebate", "10%");
		map.put("rebateAmount", "900");
		map.put("giftAmount", "0");
		map.put("jkjeLowercase", "0");
		map.put("jkjeCapital", "0");
		map.put("custPort", "baidu-iclick-hk");
		map.put("sales_contact", "刘诗诗");
		map.put("custService", "李思思");
		map.put("agent", "爱点击");
		
		
		sendMailByTemplate("siuvan.xia@i-click.com", "客户入账通知", map, "example2.ftl");
	}

	/**
	 * 根据模板名称查找模板，加载模板内容后发送邮件
	 * 
	 * @param receiver
	 *            收件人地址
	 * @param subject
	 *            邮件主题
	 * @param map
	 *            邮件内容与模板内容转换对象
	 * @param templateName
	 *            模板文件名称
	 */
	public static void sendMailByTemplate(String receiver, String subject, Map<String, Object> map, String templateName)
			throws IOException, TemplateException, MessagingException {
		String maiBody = "";
		String server = ConfigLoader.getServer();
		String sender = ConfigLoader.getSender();
		String username = ConfigLoader.getUsername();
		String password = ConfigLoader.getPassword();
		String nickname = ConfigLoader.getNickname();
		MailSender mail = new MailSender(server);
		mail.setNeedAuth(true);
		mail.setNamePass(username, password, nickname);
		maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
		mail.setSubject(subject);
		mail.setBody(maiBody);
		mail.setReceiver(receiver);
		mail.setSender(sender);
		mail.sendout();
	}

	/**
	 * 根据模板名称查找模板，加载模板内容后发送邮件
	 * 
	 * @param receiver
	 *            收件人地址
	 * @param subject
	 *            邮件主题
	 * @param map
	 *            邮件内容与模板内容转换对象
	 * @param templateName
	 *            模板文件名称
	 */
	public static void sendMailAndFileByTemplate(String receiver, String subject, List<String> filePaths,
			Map<String, Object> map, String templateName) throws IOException, TemplateException, MessagingException {
		String maiBody = "";
		String server = ConfigLoader.getServer();
		String sender = ConfigLoader.getSender();
		String username = ConfigLoader.getUsername();
		String password = ConfigLoader.getPassword();
		String nickname = ConfigLoader.getNickname();
		MailSender mail = new MailSender(server);
		mail.setNeedAuth(true);
		mail.setNamePass(username, password, nickname);
		maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
		mail.setSubject(subject);
		if(filePaths!=null && filePaths.size()>0){
			for(String filePath:filePaths){
				mail.addFileAffix(filePath);
			}
		}
		mail.setBody(maiBody);
		mail.setReceiver(receiver);
		mail.setSender(sender);
		mail.sendout();
	}

	/**
	 * 普通方式发送邮件内容
	 * 
	 * @param receiver
	 *            收件人地址
	 * @param subject
	 *            邮件主题
	 * @param maiBody
	 *            邮件正文
	 */
	public static void sendMail(String receiver, String subject, String maiBody)
			throws IOException, MessagingException {
		String server = ConfigLoader.getServer();
		String sender = ConfigLoader.getSender();
		String username = ConfigLoader.getUsername();
		String password = ConfigLoader.getPassword();
		String nickname = ConfigLoader.getNickname();
		MailSender mail = new MailSender(server);
		mail.setNeedAuth(true);
		mail.setNamePass(username, password, nickname);
		mail.setSubject(subject);
		mail.setBody(maiBody);
		mail.setReceiver(receiver);
		mail.setSender(sender);
		mail.sendout();
	}

	/**
	 * 普通方式发送邮件内容，并且附带文件附件
	 * 
	 * @param receiver
	 *            收件人地址
	 * @param subject
	 *            邮件主题
	 * @param filePath
	 *            文件的绝对路径
	 * @param maiBody
	 *            邮件正文
	 */
	public static void sendMailAndFile(String receiver, String subject, String filePath, String maiBody)
			throws IOException, MessagingException {
		String server = ConfigLoader.getServer();
		String sender = ConfigLoader.getSender();
		String username = ConfigLoader.getUsername();
		String password = ConfigLoader.getPassword();
		String nickname = ConfigLoader.getNickname();
		MailSender mail = new MailSender(server);
		mail.setNeedAuth(true);
		mail.setNamePass(username, password, nickname);
		mail.setSubject(subject);
		mail.setBody(maiBody);
		mail.addFileAffix(filePath);
		mail.setReceiver(receiver);
		mail.setSender(sender);
		mail.sendout();
	}

}