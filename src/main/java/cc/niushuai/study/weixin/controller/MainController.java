package cc.niushuai.study.weixin.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/at")
    public String getAccessToken() {

        try {

            String accessToken = wxMpService.getAccessToken();
            log.info(accessToken);
            return accessToken;
        } catch (WxErrorException e) {
            log.error("获取accessToken失败.{}",e.getError());
        }
        return null;
    }


    public String getWxIp(){
        try {
            return JSONObject.toJSONString(wxMpService.getCallbackIP());
        } catch (WxErrorException e) {
            log.error("获取微信服务器ip失败.{}",e.getError());
        }
        return null;
    }


}
