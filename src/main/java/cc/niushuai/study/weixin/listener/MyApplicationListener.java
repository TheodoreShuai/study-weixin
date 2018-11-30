package cc.niushuai.study.weixin.listener;

import cc.niushuai.study.weixin.entity.WeiXinAccessToken;
import cc.niushuai.study.weixin.utils.ApplicationContextUtil;
import cc.niushuai.study.weixin.utils.TokenUtil;
import cc.niushuai.study.weixin.utils.WebApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //初始化一个访问令牌
        //initAccessToken();
    }

    private void initAccessToken() {
        log.info("初始化ACCESS_TOKEN开始...");
        TokenUtil tokenUtil = ApplicationContextUtil.getBean("tokenUtil");
        WeiXinAccessToken token = tokenUtil.getAccessToken(true);
        log.info(token.toString());
        WebApplicationContextUtil.setAttribute("wxToken", token);
        log.info("初始化ACCESS_TOKEN成功...");
    }


}
