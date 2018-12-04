/**
 * @Name WxMpServiceConfig
 * @Author niushuai
 * @Date 2018/11/30 8:53
 * @Desc
 */
package cc.niushuai.study.weixin.config;

import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMpServiceConfig {

    @Autowired
    private Constant constant;

    @Bean
    public WxMpService wxMpService() {

        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpConfigStorage provider = new WxMpInMemoryConfigStorage();
        ((WxMpInMemoryConfigStorage) provider).setAppId(constant.getAppId());
        ((WxMpInMemoryConfigStorage) provider).setSecret(constant.getAppSecret());
        wxMpService.setWxMpConfigStorage(provider);

        return wxMpService;
    }

    @Bean
    public WxMpMenuService menuService() {
        return wxMpService().getMenuService();
    }


    @Bean(value = "subscribeMsgService")
    public WxMpSubscribeMsgService subscribeMsgService() {
        return wxMpService().getSubscribeMsgService();
    }

}
