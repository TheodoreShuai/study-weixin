/**
 * @Name WebAuthorizeController
 * @Author niushuai
 * @Date 2018/12/6 15:27
 * @Desc
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class WebAuthorizeController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/webat")
    public WxMpOAuth2AccessToken getWebAccessToken(String code){
        WxMpOAuth2AccessToken auth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            auth2AccessToken.setAccessToken(wxMpService.getAccessToken());
            

            return wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
