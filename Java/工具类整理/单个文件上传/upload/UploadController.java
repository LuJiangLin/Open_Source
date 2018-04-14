package com.anssy.lt.controller.admin;

import com.anssy.lt.constants.ResultStatus;
import com.anssy.lt.domain.GameUser;
import com.anssy.lt.service.IGameUserService;
import com.anssy.lt.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangjinchang on 15/12/21.
 */
@Controller
@RequestMapping("/game/upload")
public class UploadController {

    private static Logger log = Logger.getLogger(UploadController.class);
   @Autowired
   private IGameUserService userService;
    /**
     * 文件上传到Web服务器
     * @return
     */
    @RequestMapping("/web")
    @ResponseBody
    public Object uploadFileDemo(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value="file", required=false) MultipartFile file){
        Map resultMap = new HashMap();
        try{
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = "upload" + sep + DateUtil.format(new Date(), "yyyyMMdd");
            String uploadDir = request.getRealPath("/")+uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", CompetitionConfig.getStringValue("domain")+sep+uri+sep+newFileName);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }

    /**
     * 上传文件到阿里云
     * @param request
     * @param response
     * @param file
     * @param type 文件类型 user：用户相关文件；merchant：商家相关文件；goods：商品相关文件；adv：广告图片
     * @return
     */
    @RequestMapping("/aliyun_oss")
    @ResponseBody
    public Object uploadFileToAliyun(HttpServletRequest request,HttpServletResponse response,
                                 @RequestParam(value="file", required=false) MultipartFile file,
                                     String type){
        Map resultMap = new HashMap();
        try{
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uploadDir = request.getRealPath("/")+"upload";
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String newFilePath = uploadDir + sep + newFileName;
            File uploadedFile = new File(newFilePath);
            FileCopyUtils.copy(bytes, uploadedFile);

            //上传到阿里云
            String path = AliyunOSSUtil.uploadFileForController(newFilePath,type);

            //删除服务器文件
            new File(newFilePath).delete();

            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", path);
            return resultMap;
        }catch (Exception e){
            log.error(e.getMessage());
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }


    /**
     * 上传文件超过指定大小的处理
     * @param e
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseBody
    public Object doException(Exception e,HttpServletRequest request) throws Exception {
        Map resultMap = new HashMap();
        resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
        if (e instanceof MaxUploadSizeExceededException) {
            resultMap.put("error_msg","文件大小不能超过5M！");
        }else{
            resultMap.put("error_msg",e.getMessage());
            log.error(e.getMessage());
        }
        return resultMap;

    }

    @RequestMapping("/web2")
    @ResponseBody
    public Object uploadFileDemo2(HttpServletRequest request,HttpServletResponse response,
                                 @RequestParam(value="file", required=false) MultipartFile[] files,Integer index){
        Map resultMap = new HashMap();
        try{
            MultipartFile file = files[index-1];
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = "upload" + sep + DateUtil.format(new Date(), "yyyyMMdd");
            String uploadDir = request.getRealPath("/")+uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", CompetitionConfig.getStringValue("domain")+sep+uri+sep+newFileName);
            return resultMap;
        }catch (Exception e){
            log.error(e.getMessage());
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }


    @RequestMapping("/web3")
    @ResponseBody
    public Object uploadFileDemo3(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value="file", required=false) MultipartFile file, Integer type, HttpSession session){
        Map resultMap = new HashMap();
        try{
            GameUser user = SessionUtil.getGameUser(session);
                if(user ==null){
                    resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
                    resultMap.put("error_msg", "请重新登录!");
                    return resultMap;
                }
            byte[] bytes = file.getBytes();
            String newFileName = FileUtil.generateName(file.getOriginalFilename());
            String sep = "/";
            String uri = "upload" + sep + DateUtil.format(new Date(), "yyyyMMdd");
            String uploadDir = request.getRealPath("/")+uri;
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadedFile = new File(uploadDir + sep + newFileName);
            FileCopyUtils.copy(bytes, uploadedFile);
            if(type ==1){
                user.setHeadPic(CompetitionConfig.getStringValue("domain")+sep+uri+sep+newFileName);
                userService.updateByPrimaryKeySelective(user);
            }
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            resultMap.put("path", CompetitionConfig.getStringValue("domain")+sep+uri+sep+newFileName);
            return resultMap;
        }catch (Exception e){
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "文件上传失败!");
            return resultMap;
        }
    }


}
