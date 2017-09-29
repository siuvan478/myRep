package com.asgab.service;

import com.asgab.core.mail.MailUtil;
import com.asgab.entity.*;
import com.asgab.entity.Process;
import com.asgab.repository.CustMasterMapper;
import com.asgab.repository.MailMapper;
import com.asgab.repository.ProcessMapper;
import com.asgab.service.currMaster.CurrMasterService;
import com.asgab.service.paymentPurpose.PaymentPurposeService;
import com.asgab.service.paytran.PayTranHeaderService;
import com.asgab.util.CommonUtil;
import com.asgab.util.RandomNumUtil;
import com.asgab.web.AutoEmailNotification;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

@Component
@Transactional
public class MailService {


    @Resource
    private EhCacheService ehCacheService;

    public boolean sendCaptcha(String username) {
        String captcha = RandomNumUtil.getRandNumber(6);
        String templateName = "my.ftl";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "验证码为：" + captcha);
        boolean success = true;
        try {
            MailUtil.sendMailByTemplate(username, "注册验证码", map, templateName);
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

    @Resource
    private MailMapper mailMapper;

    @Resource
    private ProcessMapper processMapper;

    @Autowired
    private PayTranHeaderService payTranHeaderService;

    @Resource
    private CustMasterMapper custMasterMapper;

    @Autowired
    private CurrMasterService currMasterService;

    @Autowired
    private PaymentPurposeService paymentPurposeService;

    public void save(Mail mail) throws Exception {
        // 保存信息
        mailMapper.save(mail);
        // 立即发送邮件
        String template = mail.getTemplate();
        // 当前的流程
        Process process = processMapper.getById(mail.getProcessId());
        if (AutoEmailNotification.EMAIL_TEMPLATE_NAME_1.equalsIgnoreCase(template)) {
            // 发送给财务check
            step1_send_to_fin(process.getProcessId(), mail);
        } else if (AutoEmailNotification.EMAIL_TEMPLATE_NAME_2.equalsIgnoreCase(template)) {
            // 发送给财务confirm
            step2_send_to_fin(process.getProcessId(), mail);
        } else if (AutoEmailNotification.EMAIL_TEMPLATE_NAME_3.equalsIgnoreCase(template)) {
            // 发送给AM confirm
            step3_send_to_ops(process.getProcessId(), mail);
        } else if (AutoEmailNotification.EMAIL_TEMPLATE_NAME_4.equalsIgnoreCase(template)) {
            // 通知成功
            step4_send_to_AM_and_sales(process.getProcessId(), mail);
        } else if (AutoEmailNotification.EMAIL_TEMPLATE_NAME_5.equalsIgnoreCase(template)) {
            // 通知页面填写的邮箱
            step5_send_to_paytranEmail(mail);
        } else {
            // receiver subject paytranNum description
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("receiver", mail.getReceiver());
            map.put("subject", mail.getSubject());
            map.put("paytranNum", mail.getPaytranNum() + "");
            map.put("description", mail.getDescription());
            send_reject(map);
        }
        // 发送结果
        mail.setStatus('3');
        // 修改邮件状态
        mail.setSendDate(new Date());
        mailMapper.update(mail);
    }

    public void step1_send_to_fin(long processId, Mail mail) throws Exception {
        Process process = processMapper.getById(processId);
        PayTranHeader payTranHeader = payTranHeaderService.get(process.getPayTranNum());
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("subject", "客户入账通知-----财务查账用");
        parms.put("confirmArrivalURL", mail.getUrlConfirm());
        parms.put("rejectArrivalURL", mail.getUrlReject());
        parms.put("receiver", mail.getReceiver());
        resetPaytranHeader(payTranHeader, true);
        AutoEmailNotification.send1(getCustMasterMap(payTranHeader), payTranHeader, parms);
    }

    public void step2_send_to_fin(long processId, Mail mail) throws Exception {
        Process process = (Process) processMapper.getById(processId);
        PayTranHeader payTranHeader = payTranHeaderService.get(process.getPayTranNum());
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("subject", "客户入账通知-----财务确认打款用");
        parms.put("confirmPayTranURL", mail.getUrlConfirm());
        parms.put("receiver", mail.getReceiver());
        // 获取上一个流程
        Process lastProcess = processMapper.getLastProcess(processId);
        parms.put("finConfirmAuditDate", lastProcess == null ? "" : lastProcess.getUpdateDate());
        parms.put("finAuditBy", lastProcess == null ? "" : lastProcess.getUpdateBy());
        resetPaytranHeader(payTranHeader, true);
        AutoEmailNotification.send2(getCustMasterMap(payTranHeader), payTranHeader, parms);
    }

    public void step3_send_to_ops(long processId, Mail mail) throws Exception {
        Process process = (Process) processMapper.getById(processId);
        PayTranHeader payTranHeader = payTranHeaderService.get(process.getPayTranNum());
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("subject", "客户入账通知");
        // 获取上一个流程
        Process lastProcess = processMapper.getLastProcess(processId);
        Process last_lastProcess = null;
        if (lastProcess != null) {
            last_lastProcess = processMapper.getLastProcess(lastProcess.getProcessId());
        }
        parms.put("receiver", mail.getReceiver());
        parms.put("finConfirmReceivableDate", last_lastProcess == null ? "" : last_lastProcess.getUpdateDate());
        parms.put("finConfirmReceivableBy", last_lastProcess == null ? "" : last_lastProcess.getUpdateBy());
        parms.put("finPayTranDate", lastProcess == null ? "" : lastProcess.getUpdateDate());
        parms.put("finPayTranBy", lastProcess == null ? "" : lastProcess.getUpdateBy());
        parms.put("confirmCustArrivalURL", mail.getUrlConfirm());
        resetPaytranHeader(payTranHeader, true);
        AutoEmailNotification.send3(getCustMasterMap(payTranHeader), payTranHeader, parms);
    }

    public void step4_send_to_AM_and_sales(long processId, Mail mail) throws Exception {
        Process process = (Process) processMapper.getById(processId);
        PayTranHeader payTranHeader = payTranHeaderService.get(process.getPayTranNum());
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("subject", "客户入账通知");
        parms.put("receiver", mail.getReceiver());
        // 获取上一个流程
        Process lastProcess = processMapper.getLastProcess(processId);
        Process last_lastProcess = null;
        if (lastProcess != null) {
            last_lastProcess = processMapper.getLastProcess(lastProcess.getProcessId());
        }

        parms.put("finConfirmReceivableDate", last_lastProcess == null ? "" : last_lastProcess.getUpdateDate());
        parms.put("finConfirmReceivableBy", last_lastProcess == null ? "" : last_lastProcess.getUpdateBy());
        parms.put("finPayTranDate", lastProcess == null ? "" : lastProcess.getUpdateDate());
        parms.put("finPayTranBy", lastProcess == null ? "" : lastProcess.getUpdateBy());
        resetPaytranHeader(payTranHeader, true);
        AutoEmailNotification.send4(getCustMasterMap(payTranHeader), payTranHeader, parms);
    }

    public void step5_send_to_paytranEmail(Mail mail) throws Exception {
        PayTranHeader payTranHeader = payTranHeaderService.get(mail.getPaytranNum());
        resetPaytranHeader(payTranHeader, true);
        AutoEmailNotification.send5(payTranHeader, mail);
    }

    public void send_reject(Map<String, Object> map) {
        AutoEmailNotification.send_reject(map);
    }

    private Map<String, CustMaster> getCustMasterMap(PayTranHeader payTranHeader) {
        Map<String, CustMaster> custMasterMap = new HashMap<String, CustMaster>();
        for (int i = 0; i < payTranHeader.getPayTranDetails().size(); i++) {
            String baiduUserName = payTranHeader.getPayTranDetails().get(i).getBdUserName();
            CustMaster tempCustMaster = custMasterMapper.findByCustUsername(baiduUserName);
            if (tempCustMaster != null) {
                custMasterMap.put(baiduUserName, tempCustMaster);
            }
        }
        return custMasterMap;
    }

    private void resetPaytranHeader(PayTranHeader payTranHeader, boolean isZH) {
        payTranHeader.setStatuses(isZH ? CommonUtil.STATUSES_ZH : CommonUtil.STATUSES_EN);

        List<CurrMaster> currMasters = currMasterService.getAllCurrMaster();
        payTranHeader.getCurrMasters().clear();
        payTranHeader.getCurrMasters().addAll(currMasters);

        payTranHeader.getCurrencys().clear();
        // 货币
        for (CurrMaster currmaster : currMasters) {
            payTranHeader.getCurrencys().put(currmaster.getCurr_code(), isZH ? currmaster.getCurr_name() : currmaster.getCurr_code());
        }

        List<PaymentPurpose> paymentPurposes = paymentPurposeService.getAllPaymentPurpose();
        payTranHeader.getPayCodes().clear();
        // 交易方式
        for (PaymentPurpose paymentPurpose : paymentPurposes) {
            payTranHeader.getPayCodes().put(Long.parseLong(paymentPurpose.getPay_code()), isZH ? paymentPurpose.getPay_purpose() : paymentPurpose.getPay_purposeEN());
        }
        // detail 的交易方式
        for (PayTranDetail payTranDetail : payTranHeader.getPayTranDetails()) {
            payTranDetail.getPayCodes().clear();
            payTranDetail.getPayCodes().putAll(payTranHeader.getPayCodes());
        }
    }

}
