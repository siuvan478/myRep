package com.asgab.service;

import com.asgab.core.mail.MailTemplateEnum;
import com.asgab.core.mail.MailUtil;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Component
@Transactional
public class MailService {

    public boolean sendCaptcha(String receiver, MailTemplateEnum mte, Map<String, Object> params) {
        boolean success = true;
        try {
            MailUtil.sendMailByTemplate(receiver, mte.getTitle(), params, mte.getTemplateName());
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } catch (TemplateException e) {
            e.printStackTrace();
            success = false;
        } catch (MessagingException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}
