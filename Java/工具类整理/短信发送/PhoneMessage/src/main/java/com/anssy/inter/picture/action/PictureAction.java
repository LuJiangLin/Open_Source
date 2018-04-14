/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureAction.java
 * PACKAGE      :  com.anssy.inter.picture.action
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.picture.action;

import com.google.gson.Gson;
import com.anssy.venturebar.picture.entity.PictureInfoEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.picture.vo.PictureIdVo;
import com.anssy.inter.picture.vo.PictureMarkVo;
import com.anssy.inter.picture.vo.PictureTypeVo;
import com.anssy.inter.picture.vo.PictureVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          接口_图片 后续可以控制不存在的图片URL转向默认的图片 使用图片服务器/云存储(框架性能)
 *          当网络不佳时,客户端可以使用默认图片而不需要获取图片URL,减少后台压力(不在搜索列表时返回图片URL)
 *          当图片上传时生成不同尺寸的图片，app根据尺寸来获取图片减少流量使用. 320*480, 640*960, 640*1136
 *          ,2048*1536, 1024*768, 1536*2048, 768*1024
 */
@Controller
@RequestMapping("/app/picture/pictureAction")
public class PictureAction extends AppAction {

    @Resource
    private PictureServer pictureServer;

    /**
     * 上传图片接口(Base64)
     *
     * @param {"pictureType":"1","encrypt":"","suffix":""}                主图上传(主图和列表图片)
     * @param {"pictureType":"1","encrypt":"","pictureId":"","suffix":""} 主图上传(存在图片ID)(主图和列表图片)
     * @param {"pictureType":"2","encrypt":"","pictureId":"","suffix":""} 其它图片上传
     */
    @RequestMapping(value = "/uploadPicture", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> uploadPicture(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PictureVo vo = new Gson().fromJson(getJson(request), PictureVo.class);
        if (vo != null && vo.getPictureType() > 0
                && StringUtils.isNotBlank(vo.getEncrypt())
                && StringUtils.isNotBlank(vo.getSuffix())) {
            map = writeResultRep();
            String[] res = pictureServer.uploadPicture(vo);
            map.put("pictureId", res[0]);
            map.put("pictureMark", res[1]);
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 上传图片接口(web form)
     */
    @RequestMapping(value = "/upload", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        int pictureType = Integer.parseInt(request.getParameter("pictureType"));
        String pictureId = request.getParameter("pictureId");
        if (file != null) {
            map = writeResultRep();
            String[] res = pictureServer.upload(pictureType, pictureId, file);
            map.put("pictureId", res[0]);
            map.put("pictureMark", res[1]);
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 获取列表图片URL接口
     *
     * @param {"pictureId":""}
     */
    @RequestMapping(value = "/findTinyURL", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findTinyURL(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PictureIdVo vo = new Gson().fromJson(getJson(request), PictureIdVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPictureId())) {
            map = writeResultRep();
           String urls = pictureServer.findTinyURL(vo.getPictureId());
            PictureInfoEntity picture = pictureServer.findTinyPicture(vo.getPictureId());
            map.put("urls", urls);
            map.put("picture", picture);
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 获取图片URL接口
     *
     * @param {"pictureId":"","pictureType":""}
     */
    @RequestMapping(value = "/findURL", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findURL(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PictureTypeVo vo = new Gson().fromJson(getJson(request), PictureTypeVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPictureId())) {
            map = writeResultRep();
            List<PictureInfoEntity> pictures = pictureServer.findURL(vo.getPictureId(), vo.getPictureType());
            List<String> urls = pictureServer.findURL(pictures);
            map.put("pictures", pictures);
            map.put("urls", urls);
            if (!urls.isEmpty()) {
                map.put("count", urls.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 删除图片接口(删除pictureId的多张图片)
     *
     * @param {"pictureId":""}
     */
    @RequestMapping(value = "/deletePicture", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deletePicture(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PictureIdVo vo = new Gson().fromJson(getJson(request), PictureIdVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPictureId())) {
            boolean flag = pictureServer.deletePicture(vo.getPictureId());
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(10);// 删除图片失败
            }
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 删除图片接口(删除pictureMark的一张图片)
     *
     * @param {"pictureMark":""}
     */
    @RequestMapping(value = "/deletePictureByMark", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deletePictureByMark(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PictureMarkVo vo = new Gson().fromJson(getJson(request),
                PictureMarkVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPictureMark())) {
            boolean flag = pictureServer.deletePictureByMark(vo.getPictureMark());
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(10);// 删除图片失败
            }
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 请求图片接口
     * <p/>
     * 通过http1.1 Etag来控制图片使用缓存功能。
     * 第一次访问产生Etag参数，再次访问时判断If-None-Match是否有值来判断是否返回304
     * 图片不存在返回默认图片
     */
    @RequestMapping(value = "/findImageStreams")
    public void findImageStreams(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String etag = request.getHeader("If-None-Match");
        String suffixPath = request.getParameter("path");
        if (StringUtils.isNotBlank(etag)) {
            response.setStatus(304);
        } else {
            response.setHeader("Etag", Long.toString(new Date().getTime()));
            response.setHeader("Cache-Control", "public, max-age=31536000");
            response.setContentType("image/png");
            File file = pictureServer.findImageFile(suffixPath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            //// TODO: 15/12/24 check none ignore
            inputStream.read(data);
            inputStream.close();
            response.setContentType("image/png");
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        }
    }

}