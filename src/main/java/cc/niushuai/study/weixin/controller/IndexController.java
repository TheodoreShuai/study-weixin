package cc.niushuai.study.weixin.controller;

import cc.niushuai.study.weixin.config.Constant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/wx")
public class IndexController {

    @Autowired
    private Constant constant;

    @Autowired
    private EventController eventController;

    @GetMapping("/confirmServerInfo")
    public void testWx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        ((WxMpInMemoryConfigStorage) configStorage).setToken(constant.getConfirmToken());
        wxMpService.setWxMpConfigStorage(configStorage);

        boolean checkResult = wxMpService.checkSignature(timestamp, signature, nonce);

        PrintWriter writer = response.getWriter();
        //如果是测试号 不需要加密校验 直接返回相应的字符串即可
        writer.print(echostr);

//        if(checkResult){
//            log.info("验证通过");
//            writer.print(echostr);
//        }else{
//            log.error("验证失败");
//            writer.write("error");
//        }
    }


    @PostMapping("/confirmServerInfo")
    public String postMapping(HttpServletRequest request, HttpServletResponse response) {

        try {

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            WxMpXmlMessage msg = WxMpXmlMessage.fromXml(request.getInputStream());
            System.out.println(msg);


            if (WxConsts.XmlMsgType.TEXT.equalsIgnoreCase(msg.getMsgType()))
                text(response, msg);
            else if (WxConsts.XmlMsgType.IMAGE.equalsIgnoreCase(msg.getMsgType()))
                image(response, msg);
            else if (WxConsts.XmlMsgType.LINK.equalsIgnoreCase(msg.getMsgType()))
                link(response, msg);
            else if ("event".equalsIgnoreCase(msg.getMsgType())) {
                if (WxConsts.EventType.SUBSCRIBE.equalsIgnoreCase(msg.getEvent()) || WxConsts.EventType.UNSUBSCRIBE.equalsIgnoreCase(msg.getEvent()))
                    eventController.subscribeEvent(response, msg);
                else if(msg.getEvent().equalsIgnoreCase(WxConsts.EventType.VIEW))
                    eventController.viewEvent(response,msg);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return "success";
    }

    private void link(HttpServletResponse response, WxMpXmlMessage msg) {

        System.out.println("这是link类型的消息");


    }

    private void image(HttpServletResponse response, WxMpXmlMessage msg) {

        String picUrl = msg.getPicUrl();

        try {
            URL url = new URL(picUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileUtils.copyInputStreamToFile(dataInputStream, new File("c:/" + UUID.randomUUID().toString().replace("-", "") + ".jpg"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void text(HttpServletResponse response, WxMpXmlMessage msg) throws IOException {
        TextBuilder text = WxMpXmlOutMessage.TEXT();
        text.toUser(msg.getFromUser());
        text.fromUser(msg.getToUser());
        String content = msg.getContent();
        text.content("本次发送的内容是：" + content);
        WxMpXmlOutMessage build = text.build();

        PrintWriter writer = response.getWriter();

        if (content.equalsIgnoreCase("链接")) {


            return;

        }


        System.out.println(build.toXml());
        writer.print(build.toXml());

    }


    @RequestMapping("/test")
    @ResponseBody
    public String testWxInfo() {

        return "Hello Test";
    }

    @RequestMapping("/index")
    public String indexPage() {
        return "index";
    }

}
