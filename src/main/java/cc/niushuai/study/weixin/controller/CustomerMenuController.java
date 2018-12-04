/**
 * @Name CustomerMenuController
 * @Author niushuai
 * @Date 2018/12/3 17:11
 * @Desc
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/cm")
public class CustomerMenuController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/pullMsg")
    public void pullMsg(){

    }
}
