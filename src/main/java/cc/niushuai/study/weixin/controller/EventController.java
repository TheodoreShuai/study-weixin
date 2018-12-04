/**
 * @Name EventController
 * @Author niushuai
 * @Date 2018/12/4 16:30
 * @Desc
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpSubscribeMsgService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.subscribe.WxMpSubscribeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 事件处理器
 */
@Controller("/event")
public class EventController {

    @Autowired
    private WxMpSubscribeMsgService subscribeMsgService;

    public void subscribeEvent(HttpServletResponse response, WxMpXmlMessage msg) {

        PrintWriter w = null;
        try {
            w = response.getWriter();


            String event = msg.getEvent();
            if (event.equalsIgnoreCase(WxConsts.EventType.SUBSCRIBE)) {

                WxMpSubscribeMessage respMsg = new WxMpSubscribeMessage();
                respMsg.setToUser(msg.getFromUser());
                respMsg.setTitle("欢迎订阅");
                respMsg.setContentValue("这是<a href='http://www.baidu.com'>百度</a>页面");


//                System.out.println(respMsg);

                boolean b = subscribeMsgService.sendSubscribeMessage(respMsg);
                System.out.println(b);


            } else if (event.equalsIgnoreCase(WxConsts.EventType.UNSUBSCRIBE)) {

            }

            w.write("ok");
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
