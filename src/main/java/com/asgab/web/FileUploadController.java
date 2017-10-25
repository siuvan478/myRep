package com.asgab.web;

import com.alibaba.fastjson.JSONObject;
import com.asgab.core.fileupload.ProgressEntity;
import com.asgab.core.fileupload.SuccessBean;
import com.asgab.entity.CustMaster;
import com.asgab.entity.PayTranAttachement;
import com.asgab.repository.PayTranAttachmentMapper;
import com.asgab.service.custMaster.CustMasterService;
import com.asgab.util.CommonUtil;
import com.asgab.util.Identities;
import com.asgab.util.JsonMapper;
import com.asgab.web.custMaster.ReadExcel4CustMaster;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = "/file")
public class FileUploadController {

    public static final String UPLOAD_FOLDER = "/usr/tmp/upload/";
    //	public static final String UPLOAD_FOLDER = "/Users/Jack/tmp/";
    public static final long UPLOAD_MAX_SIZE = 4;// 1M 1048576

    @Resource
    private PayTranAttachmentMapper payTranAttachmentMapper;

    @Resource
    private CustMasterService custMasterService;

    //文件记录图片地址
    private static final String BOX_RECORD_IMAGE_FOLDER = "/usr/temp/upload/BoxRecord/";

    //文件记录图片大小 5MB
    private static final long BOX_RECORD_IMAGE_SIZE = 5;

    /**
     * 上传图片
     *
     * @param request
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("/image/upload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String success = "true";
        String message = CommonUtil.i18nStr(request, "文件上传成功", "file upload success");
        String fileUrl = "";
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                // 文件超大，不保存文件，给出提示
                if (file != null && file.getSize() > (BOX_RECORD_IMAGE_SIZE * 1048576)) {
                    // message = "请上传不超过"+UPLOAD_MAX_SIZE+"M的文件";
                    message = CommonUtil.i18nStr(request, "请上传不超过" + UPLOAD_MAX_SIZE + "M的文件",
                            "check the file size,it must be less than" + UPLOAD_MAX_SIZE + "M.");
                    success = "false";
                    break;
                }
                try {
                    if (file != null) {
                        File imageFile = new File(BOX_RECORD_IMAGE_FOLDER);
                        if (!imageFile.exists()) {
                            imageFile.mkdirs();
                        }
                        String path = BOX_RECORD_IMAGE_FOLDER + Identities.uuid2() + "." + prefix;
                        file.transferTo(new File(path));
                        fileUrl = path;
                        break;
                    }
                } catch (Exception e) {
                    message = CommonUtil.i18nStr(request, "上传失败!请检查文件", "upload  failure, please check file");
                    success = "false";
                    e.printStackTrace();
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("fileUrl", fileUrl);
        JsonMapper mapper = JsonMapper.nonDefaultMapper();
        response.getWriter().print(mapper.toJson(map));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /***
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("upload")
    public String fileUpload(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {

        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        String success = "true";
        String message = CommonUtil.i18nStr(request, "文件上传成功", "file upload success");
        String fileUrl = "";
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                // 文件超大，不保存文件，给出提示
                if (file != null && file.getSize() > (UPLOAD_MAX_SIZE * 1048576)) {
                    success = "false";
                    // message = "请上传不超过"+UPLOAD_MAX_SIZE+"M的文件";
                    message = CommonUtil.i18nStr(request, "请上传不超过" + UPLOAD_MAX_SIZE + "M的文件",
                            "check the file size,it must be less than" + UPLOAD_MAX_SIZE + "M.");
                    break;
                }
                if (file != null) {
                    try {
                        File rootFileUrl = new File(UPLOAD_FOLDER);
                        if (!rootFileUrl.exists()) rootFileUrl.mkdirs();
                        String path = UPLOAD_FOLDER + Identities.uuid2() + "." + prefix;
                        file.transferTo(new File(path));
                        fileUrl = path;
                        break;// 一次只能上传一个文件
                    } catch (Exception e) {
                        success = "false";
                        message = CommonUtil.i18nStr(request, "上传失败!请检查文件", "upload  failure, please check file");
                        e.printStackTrace();
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("fileUrl", fileUrl);
        JsonMapper mapper = JsonMapper.nonDefaultMapper();
        response.getWriter().print(mapper.toJson(map));
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public SuccessBean getProgress(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("upload_ps") == null) {
            return new SuccessBean(true, "0");
        }
        ProgressEntity ps = (ProgressEntity) request.getSession().getAttribute("upload_ps");
        Double percent = 0d;
        if (ps.getpContentLength() != 0L) {
            percent = (double) ps.getpBytesRead() / (double) ps.getpContentLength() * 100; // 百分比

        }
        // logger.info("当前上传进度:" + percent.toString());
        return new SuccessBean(true, String.valueOf(percent.intValue()));
    }

    @RequestMapping("paytranUpload")
    public String paytranUpload(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {
        Map<String, Object> map = new HashMap<>();
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        String success = "true";
        String message = CommonUtil.i18nStr(request, "文件上传成功", "file uppload success");

        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());

                // 文件超大，不保存文件，给出提示
                if (file != null && file.getSize() > (UPLOAD_MAX_SIZE * 1048576)) {
                    success = "false";
                    // message = "请上传不超过"+UPLOAD_MAX_SIZE+"M的文件";
                    message = CommonUtil.i18nStr(request, "请上传不超过" + UPLOAD_MAX_SIZE + "M的文件",
                            "check the file size,it must be less than " + UPLOAD_MAX_SIZE + "M.");
                    break;
                }

                if (file != null) {
                    JSONObject jsonObject = CommonUtil.saveFile(file, UPLOAD_FOLDER);
                    // 写到数据库
                    PayTranAttachement payTranAttachement = new PayTranAttachement();
                    payTranAttachement.setFileName(jsonObject.getString("fileName"));
                    payTranAttachement.setShowName(jsonObject.getString("showName"));
                    payTranAttachement.setPath(jsonObject.getString("filePath"));
                    payTranAttachement.setCreateDate(new Date());
                    payTranAttachmentMapper.save(payTranAttachement);
                    map.put("fileId", payTranAttachement.getAttachmentId());
                    map.put("filePath", payTranAttachement.getPath() + File.separator + payTranAttachement.getFileName());
                }
            }
        }

        map.put("success", success);
        map.put("message", message);
        JsonMapper mapper = JsonMapper.nonDefaultMapper();
        response.getWriter().print(mapper.toJson(map));
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    @RequestMapping("custMasterUpload")
    public String custMasterUpload(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {

        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        String success = "true";
        String message = CommonUtil.i18nStr(request, "文件上传成功", "file uppload success");
        String result = "";

        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();

            List<CustMaster> list = null;
            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                // 文件超大，不保存文件，给出提示
                if (file != null && file.getSize() > (UPLOAD_MAX_SIZE * 1048576)) {
                    success = "false";
                    message = CommonUtil.i18nStr(request, "请上传不超过" + UPLOAD_MAX_SIZE + "MB的文件",
                            "check file size,it must be less than" + UPLOAD_MAX_SIZE + "MB.");
                    break;
                }
                if (file.getOriginalFilename() != null && !file.getOriginalFilename().contains(".xlsx")
                        && !file.getOriginalFilename().contains(".XLSX")) {
                    success = "false";
                    // message = "请上传后缀为 .xlsx 类型的文件";
                    message = CommonUtil.i18nStr(request, "请上传后缀为 .xlsx 类型的文件",
                            "please upload the type of xlsx excel file");
                    break;
                }
                if (file != null) {
                    try {
                        CommonsMultipartFile cf = (CommonsMultipartFile) file;
                        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
                        final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fi.getStoreLocation()));
                        list = ReadExcel4CustMaster.read(workbook, "11");
                        result = custMasterService.addCustMasters(list);
                        // message = "上传成功，" + result.split(":")[0] + "条新增，" +
                        // result.split(":")[1]+ "条修改!";
                        message = CommonUtil.i18nStr(request,
                                "上传成功，" + result.split(":")[0] + "条新增，" + result.split(":")[1] + "条修改!",
                                "upload success，add " + result.split(":")[0] + " items，update " + result.split(":")[1]
                                        + "items");
                    } catch (Exception e) {
                        success = "false";
                        message = CommonUtil.i18nStr(request, "上传失败!请检查文件", "upload  fialue, please check file");
                        e.printStackTrace();
                    }

                    break;// 一次只能上传一个文件
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        JsonMapper mapper = JsonMapper.nonDefaultMapper();
        response.getWriter().print(mapper.toJson(map));
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 将图片读到页面上
     *
     * @param request  内置对象
     * @param response 内置对象
     * @return
     */
    @RequestMapping("dumpImage")
    public void dumpImage(HttpServletRequest request, HttpServletResponse response) {
        String paramPath = request.getParameter("path");
        String path = "";
        if (paramPath == null) {
            path = (String) request.getAttribute("path");
        } else {
            try {
                path = new String(paramPath.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
//		String picPath = request.getRealPath("/") + "upload" + File.separator + path;
        String picPath = path;
        InputStream in = null;
        BufferedInputStream bis = null;
        OutputStream out = null;
        BufferedOutputStream bos = null;

        // 判断文件是否存在
        File file = new File(picPath);
        if (!file.exists() || file.isDirectory()) {
            return;
        }
        try {
            in = new FileInputStream(picPath);
            bis = new BufferedInputStream(in);

            byte[] data = new byte[1024];
            int bytes = 0;
            out = response.getOutputStream();
            bos = new BufferedOutputStream(out);
            while ((bytes = bis.read(data, 0, data.length)) != -1) {
                bos.write(data, 0, bytes);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (out != null)
                    out.close();
                if (bis != null)
                    bis.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
