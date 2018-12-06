/**
 * @Name KefuController
 * @Author niushuai
 * @Date 2018/12/5 11:38
 * @Desc
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kefu")
public class KefuController {

    @Autowired
    private WxMpKefuService wxkefuService;

    @RequestMapping("/list")
    public String getAllKefu() {
        try {
            WxMpKfList wxMpKfList = wxkefuService.kfList();

            System.out.println(wxMpKfList);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("/addkefu")
    public String addKefu() {

        try {
            WxMpKfAccountRequest build = WxMpKfAccountRequest.builder().build();
            String invitedWx = "ttkf@gh_a061e53e1075";
            String kfAccount = "ns1225803134";
            String nickName = "甜甜";

            build.setInviteWx(invitedWx);
            build.setKfAccount(kfAccount);
            build.setNickName(nickName);

            boolean b = wxkefuService.kfAccountAdd(build);
            System.out.println(b);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "success";
    }


}
