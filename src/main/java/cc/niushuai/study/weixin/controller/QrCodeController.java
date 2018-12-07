/**
 * @Name QrCodeController
 * @Author niushuai
 * @Date 2018/12/7 10:42
 * @Desc 二维码操作
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/qrcode")
public class QrCodeController {

    @Autowired
    private WxMpQrcodeService wxMpQrcodeService;

    @RequestMapping("/getTmp")
    public void getQrcode(HttpServletResponse response){

        try {
            WxMpQrCodeTicket ticket = wxMpQrcodeService.qrCodeCreateTmpTicket("有效期为120秒的二维码", 120);
            File file = wxMpQrcodeService.qrCodePicture(ticket);

            response.getOutputStream().write(FileUtils.readFileToByteArray(file));

        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
