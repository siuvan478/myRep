package com.asgab.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asgab.repository.PayTranAttachmentMapper;
import com.asgab.util.CommonUtil;

@Controller
@RequestMapping(value="/ajax")
public class AjaxController {

	@Resource
	private PayTranAttachmentMapper payTranAttachmentMapper;
	
	
	@RequestMapping(value="delAttach/{attachmentId}" , method=RequestMethod.POST)
	public void delAttach(@PathVariable("attachmentId") Long attachmentId,HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		try {
			payTranAttachmentMapper.delete(attachmentId);
			out = response.getWriter();
			out.print(CommonUtil.i18nStr(request, "删除附件成功", "Attachment deleted successfully"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}
	
}
