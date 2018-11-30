package cc.niushuai.study.weixin.utils;

import cc.niushuai.study.weixin.config.Constant;
import cc.niushuai.study.weixin.entity.WeiXinAccessToken;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenUtil {

    private WxMpService wxMpService = null;

    @Autowired
    private Constant constant;

    public WeiXinAccessToken getAccessToken(boolean forceRefresh) {
        WeiXinAccessToken accessToken = new WeiXinAccessToken();
        try {
            if (wxMpService == null)
                wxMpService = initWxMpService(constant.getAppId(), constant.getAppSecret());

            accessToken.setAccessToken(wxMpService.getAccessToken(forceRefresh));
            //向后推迟4分钟
            DateTime offSetDateTime = DateUtil.offsetMillisecond(new Date(DateUtil.offsetMinute(new Date(), 4).getTime()), 30);
            accessToken.setExpirationTime(offSetDateTime.getTime());

            return accessToken;
        } catch (WxErrorException e) {
            log.error("获取Access_Token失败...{}", e.getError());
        }
        return null;
    }

    public WeiXinAccessToken freshAccessToken() {
        return getAccessToken(false);
    }


    private WxMpService initWxMpService(String appId, String appSecret) {
        if (wxMpService == null)
            wxMpService = WxMpServiceInstance.getInstance().getWxMpServiceInstance(appId, appSecret);

        return wxMpService;
    }


}
