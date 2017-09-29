package com.asgab.core.mail;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MailUtilTest {
	private static Logger log = LoggerFactory.getLogger(MailUtilTest.class);

	@Test
	public void testMailTemplate() {
		String templateName = "my.ftl";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", "test");
		try {
			MailUtil.sendMailByTemplate("295636011@qq.com", "test222222", map, templateName);
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (TemplateException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}
/*
	@Test
	public void testMail() {
		try {
			MailUtil.sendMail("jack.sun@i-click.com", "test", "普通邮件");
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}

	@Test
	public void testMailAndFile() {
		try {
			String filePath = "/Users/Jack/tmp/112920_398.doc";
			MailUtil.sendMailAndFile("jack.sun@i-click.com", "test", filePath, "普通邮件");
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}
*/
}
