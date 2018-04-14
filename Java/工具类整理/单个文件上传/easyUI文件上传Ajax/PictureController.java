package com.lt.website.controller.admin;

import com.lt.core.constants.ResultStatus;
import com.lt.core.utils.SystemUtils;
import com.lt.website.controller.UploadController;
import com.lt.website.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 * 内容：专注于图片-文件的上传  与下载
 */

@Controller
@RequestMapping("/admin/picture")
public class PictureController {
    private static Logger log = Logger.getLogger(PictureController.class);
    private final static String attachment_root = "upload/images";
    private final static String work_root = "upload/work";

    /**
     * 图片上传到Web服务器
     *
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "file", required = false) MultipartFile file) {
        Map resultMap = new HashMap();
        try {
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = sep +"upload" + sep +"images";
            String uploadDir = request.getRealPath("/") + uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", SystemUtils.getStringValue("domain") + sep + uri + sep + newFileName);
            resultMap.put("pic",uploadDir + sep + newFileName);
            return resultMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }





    /**
     * 作业上传到Web服务器
     *
     * @return
     */
    @RequestMapping(value = "/uploadWork", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadWork(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "file", required = false) MultipartFile file) {
        Map resultMap = new HashMap();
        try {
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = sep +"upload" + sep +"work";
            String uploadDir = request.getRealPath("/") + uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", SystemUtils.getStringValue("domain") + sep + uri + sep + newFileName);
            resultMap.put("text",uri + sep + newFileName);//修改之后的名称
            resultMap.put("name", file.getOriginalFilename());//修改之前的名称
            return resultMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }





    /**
     * 作业上传到Web服务器
     *
     * @return
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPic(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "file", required = false) MultipartFile file) {
        Map resultMap = new HashMap();
        try {
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = sep +"upload" + sep +"images";
            String uploadDir = request.getRealPath("/") + uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", SystemUtils.getStringValue("domain") + sep + uri + sep + newFileName);
            resultMap.put("text",uri + sep + newFileName);//修改之后的名称
            resultMap.put("name", file.getOriginalFilename());//修改之前的名称
            return resultMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }



    /**
     * 上传文件超过指定大小的处理
     *
     * @param e
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseBody
    public Object doException(Exception e, HttpServletRequest request) throws Exception {
        Map resultMap = new HashMap();
        resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
        if (e instanceof MaxUploadSizeExceededException) {
            resultMap.put("error_msg", "文件大小不能超过5M！");
        } else {
            resultMap.put("error_msg", e.getMessage());
            log.error(e.getMessage());
        }
        return resultMap;

    }




    @ResponseBody
    @RequestMapping("/uploadImage")
    public Object uploadImage(MultipartFile file, HttpServletRequest request) {
//        String path = request.getSession().getServletContext().getRealPath(attachment_root);
        String path = request.getRealPath("/") + "/"+attachment_root+"/";
//        String path = "D:\\images";
//        String fileName = file.getOriginalFilename();
        String originalFilename = file.getOriginalFilename();
        String dat = originalFilename.split("\\.")[1];
        String fileName = new Date().getTime()+"."+dat;
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data","/"+attachment_root+"/"+fileName);
        return result;
    }










}
