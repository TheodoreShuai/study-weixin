/**
 * @Name UserController
 * @Author niushuai
 * @Date 2018/12/7 10:04
 * @Desc 用户管理操作
 */
package cc.niushuai.study.weixin.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private WxMpUserService wxMpUserService;

    @RequestMapping("/getUser/{openid}")
    @ResponseBody
    public WxMpUser getUser(@PathVariable String openid){
        try {
            return wxMpUserService.userInfo(openid);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }


    @ResponseBody
    @RequestMapping("/list")
    public List<WxMpUser> getUserList(){
        List<String> openids = new ArrayList<>();
        openids.add("oxuCF0jIRWs1UWHYbxdNHuIhrO70");
        openids.add("oxuCF0rBtmvDJI-7sdWZvlsMT0q0");
        openids.add("oxuCF0kQ2HxFpkdpT-rqwh9YhkSk");
        List<WxMpUser> wxMpUsers = null;
        try {
            wxMpUsers = wxMpUserService.userInfoList(openids);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return wxMpUsers;
    }

    @ResponseBody
    @RequestMapping("/userList")
    public WxMpUserList List(){

        try {
            return wxMpUserService.userList(null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }



}
