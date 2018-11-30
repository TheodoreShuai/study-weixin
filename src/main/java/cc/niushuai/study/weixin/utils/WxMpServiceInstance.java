package cc.niushuai.study.weixin.utils;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * 实例化service工具类
 */
public class WxMpServiceInstance {

    private static WxMpServiceInstance instance;

    private WxMpService wxMpService;
    private WxMpConfigStorage wxMpConfigStorage;

    private WxMpServiceInstance() {
    }

    /**
     * 得到一个具体的实例化
     *
     * @return
     */
    public static WxMpServiceInstance getInstance() {

        if (instance == null) {
            try {
                instance = new WxMpServiceInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * 使用指定的appid和appsecret得到service
     * @param appId
     * @param appSecret
     * @return
     */
    public WxMpService getWxMpServiceInstance(String appId, String appSecret) {
        wxMpService = getWxMpService();
        wxMpConfigStorage = getWxMpConfigStorage(appId, appSecret);
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);

        return wxMpService;
    }

    private WxMpService getWxMpService() {
        if (wxMpService != null)
            return wxMpService;

        return new WxMpServiceImpl();
    }

    /**
     * 实例化service需要的配置
     * @param appId
     * @param appSecret
     * @return
     */
    private WxMpConfigStorage getWxMpConfigStorage(String appId, String appSecret) {
        if (wxMpConfigStorage != null)
            return wxMpConfigStorage;

        wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setAppId(appId);
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setSecret(appSecret);

        return wxMpConfigStorage;
    }


}
