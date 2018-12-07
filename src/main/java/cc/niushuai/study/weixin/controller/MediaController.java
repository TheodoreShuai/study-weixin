/**
 * @Name MediaController
 * @Author niushuai
 * @Date 2018/12/6 15:55
 * @Desc 素材管理
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.material.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private WxMpMaterialService wxMpMaterialService;

    /**
     * 统计媒体资源文件数量
     * 包含：
     * 语音 视频 文字 图文
     *
     * @return
     * @throws WxErrorException
     */
    @RequestMapping("/count")
    @ResponseBody
    public WxMpMaterialCountResult testMedia() throws WxErrorException {

        return wxMpMaterialService.materialCount();
    }


    /**
     * 分类获取媒体文件
     *
     * @param type
     * @return
     * @throws WxErrorException
     */
    @RequestMapping("/typelist")
    @ResponseBody
    public WxMpMaterialFileBatchGetResult list(String type) throws WxErrorException {
        return wxMpMaterialService.materialFileBatchGet(type, 0, 20);
    }


    /**
     * 上传一个文件 图片类型 临时素材
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/upload")
    public WxMediaUploadResult uploadFile(String path) {
        try {
            return wxMpMaterialService.mediaUpload(WxConsts.MediaFileType.FILE, new File("c:/f.txt"));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会得到一个访问刚上传的图片链接
     *
     * @return
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public WxMediaImgUploadResult uploadImage() {
        try {
            return wxMpMaterialService.mediaImgUpload(new File("c:/0.jpg"));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取一个临时素材文件
     *
     * @param request
     * @param response
     * @throws WxErrorException
     * @throws IOException
     */
    @RequestMapping("/getone")
    @ResponseBody
    public void getFileById(HttpServletRequest request, HttpServletResponse response) throws WxErrorException, IOException {

        File file = wxMpMaterialService.mediaDownload("FkUCYl3TVOodFa5zj4H7u4Li8FLOqOKp-pSfP2T490ef-6UKSXcfL-jgZ1oVMNJs");
        response.getOutputStream().write(FileUtils.readFileToByteArray(file));
    }


    /**
     * 上传图文素材永久id
     *
     * @return
     * @throws WxErrorException
     */
    @ResponseBody
    @RequestMapping("/newsUpload")
    public WxMpMaterialUploadResult uploadFile() throws WxErrorException {

        WxMpMaterialNews news = new WxMpMaterialNews();
        List<WxMpMaterialNews.WxMpMaterialNewsArticle> arts = new ArrayList<>();
        WxMpMaterialNews.WxMpMaterialNewsArticle e = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        //必须
        e.setTitle("这是素材1的标题 不显示mediaid");
        e.setThumbMediaId("DLtRD2b1oTmqXBr59vfEKEhw814jdOtUhmXWRsYgNvc");
        e.setShowCoverPic(false);
        e.setContent("这是个Context");
        e.setContentSourceUrl("http://www.baidu.com");

        arts.add(e);
        e = null;
        e = new WxMpMaterialNews.WxMpMaterialNewsArticle();

        e.setTitle("这是素材2的标题 显示mediaid");
        e.setThumbMediaId("DLtRD2b1oTmqXBr59vfEKJ7EBgz43Og6kM0GirMsJqo");
        e.setShowCoverPic(true);
        e.setContent("这是个Context");
        e.setContentSourceUrl("http://www.baidu.com");

        arts.add(e);

        news.setArticles(arts);
        news.setCreatedTime(new Date());
        news.setUpdatedTime(new Date());

        WxMpMaterialUploadResult wxMpMaterialUploadResult = wxMpMaterialService.materialNewsUpload(news);

        return wxMpMaterialUploadResult;

    }

    /**
     * s上传永久素材  非图文
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadForeverFile")
    public WxMpMaterialUploadResult uploadForeverFile() {

        File file = new File("c:/1.jpg");

        try {
            WxMpMaterial material = new WxMpMaterial();
            material.setFile(file);
            material.setName("U盘图标");
            material.setVideoTitle("U盘图标标题");
            material.setVideoIntroduction("这是一个U盘图标的永久素材");

            return wxMpMaterialService.materialFileUpload(WxConsts.MediaFileType.IMAGE, material);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取永久素材
     * @return
     */
    @ResponseBody
    @RequestMapping("/getForeverFile/{mediaId}")
    public String getForeverFile(HttpServletResponse response,@PathVariable("mediaId") String mediaId){

        InputStream inputStream = null;
        try {
            inputStream = wxMpMaterialService.materialImageOrVoiceDownload(mediaId);
            File fileImg = me.chanjar.weixin.common.util.fs.FileUtils.createTmpFile(inputStream, "fileImg", null);
            response.getOutputStream().write(FileUtils.readFileToByteArray(fileImg));
        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
