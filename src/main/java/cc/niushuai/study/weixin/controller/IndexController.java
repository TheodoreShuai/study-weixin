package cc.niushuai.study.weixin.controller;

import cc.niushuai.study.weixin.config.Constant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
@RequestMapping("/wx")
public class IndexController {

    @Autowired
    private Constant constant;

    @RequestMapping("/confirmServerInfo")
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


    @RequestMapping("/test")
    @ResponseBody
    public String testWxInfo(){

        return "Hello Test";
    }

    @RequestMapping("/index")
    public String indexPage(){
        return "index";
    }

}
