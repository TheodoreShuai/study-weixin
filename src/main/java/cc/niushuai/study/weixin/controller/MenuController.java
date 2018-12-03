/**
 * @Name MenuController
 * @Author niushuai
 * @Date 2018/11/30 9:19
 * @Desc
 */
package cc.niushuai.study.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/addMenu")
    public String addMenu() {
        try {
            WxMpMenuService menuService = wxMpService.getMenuService();
            WxMenu menu = new WxMenu();

            WxMenuButton button1 = new WxMenuButton();
            button1.setType(WxConsts.MenuButtonType.CLICK);
            button1.setKey("V1001_MENU");
            button1.setName("业务查询");

            WxMenuButton button2 = new WxMenuButton();
            button2.setType(WxConsts.MenuButtonType.CLICK);
            button2.setKey("V1001_BL");
            button2.setName("业务办理");

            WxMenuButton button3 = new WxMenuButton();
            button3.setType(WxConsts.MenuButtonType.CLICK);
            button3.setKey("V1001_Fans");
            button3.setName("粉丝福利");

            WxMenuButton button4 = new WxMenuButton();
            button4.setType(WxConsts.MenuButtonType.VIEW);
            button4.setKey("V1001_Links");
            button4.setName("百度一下");
            button4.setUrl("http://www.baidu.com");

            menu.getButtons().add(button1);
            menu.getButtons().add(button2);
            menu.getButtons().add(button4);

            return menuService.menuCreate(menu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/addDefault")
    public void addDefaultMenu() throws WxErrorException {
        String json = "{\"button\":[{\"name\":\"扫码\",\"sub_button\":[{\"key\":\"rselfmenu_0_0\",\"name\":\"扫码带提示\",\"type\":\"scancode_waitmsg\"},{\"key\":\"rselfmenu_0_1\",\"name\":\"扫码推事件\",\"type\":\"scancode_push\"}]},{\"name\":\"发图\",\"sub_button\":[{\"key\":\"rselfmenu_1_0\",\"name\":\"系统拍照发图\",\"type\":\"pic_sysphoto\"},{\"key\":\"rselfmenu_1_1\",\"name\":\"拍照或者相册发图\",\"type\":\"pic_photo_or_album\"},{\"key\":\"rselfmenu_1_2\",\"name\":\"微信相册发图\",\"type\":\"pic_weixin\"}]},{\"key\":\"rselfmenu_2_0\",\"name\":\"发送位置\",\"type\":\"location_select\"}]}";
        wxMpService.getMenuService().menuCreate(json);

    }


    @RequestMapping("/getSelfMenuString")
    public String getSelfMenuString() {
        try {
            WxMpMenuService menuService = wxMpService.getMenuService();
            WxMpGetSelfMenuInfoResult selfMenuInfo = null;
            selfMenuInfo = menuService.getSelfMenuInfo();
            return JSONObject.toJSONString(selfMenuInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/menuGet")
    public WxMpMenu menuGet() throws WxErrorException {

        WxMpMenuService menuService = wxMpService.getMenuService();
        WxMpMenu wxMpMenu = menuService.menuGet();
        return wxMpMenu;

    }


    @RequestMapping("/addSubMenu")
    public void addSubMenu() throws WxErrorException {
        j=0;
        WxMpMenu wxMpMenu = menuGet();
        WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
        List<WxMenuButton> buttons = menu.getButtons();

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> menuButtons = wxMenu.getButtons();

        for (int i = 0; i < buttons.size(); i++) {
            WxMenuButton btn = buttons.get(i);
            String name = btn.getName();
            List<WxMenuButton> buttonList = null;
            if (name.equals("业务查询")) {
                buttonList = getButtonList(new String[]{"扫码推事件", "扫码推事件_带信息", "弹出系统拍照", "弹出选择器"});
            } else if (name.equals("业务办理")) {
                buttonList = getButtonList(new String[]{"微信相册发图器", "地理位置选择器"});
            } else if (name.equals("粉丝福利")) {
                buttonList = getButtonList(new String[]{"新人再领500M", "疯狂星期五", "查网龄再送流量"});
            }
            if(null != buttonList)
                btn.getSubButtons().addAll(buttonList);
            menuButtons.add(btn);
        }

        wxMpService.getMenuService().menuCreate(wxMenu);

    }


    int j = 0;
    private List<WxMenuButton> getButtonList(String[] btns) {
        List<WxMenuButton> result = new ArrayList<>();
        for (int i = 0; i < btns.length; i++,j++) {
            WxMenuButton btn = new WxMenuButton();
            btn.setKey("V10012_" + i);
            btn.setName(btns[i]);
            btn.setType(getType(i+j));
            result.add(btn);
        }
        return result;
    }

    private String getType(int i) {

        switch (i) {
            case 0:
                return WxConsts.MenuButtonType.SCANCODE_PUSH;
            case 1:
                return WxConsts.MenuButtonType.SCANCODE_WAITMSG;
            case 2:
                return WxConsts.MenuButtonType.PIC_SYSPHOTO;
            case 3:
                return WxConsts.MenuButtonType.PIC_PHOTO_OR_ALBUM;
            case 4:
                return WxConsts.MenuButtonType.PIC_WEIXIN;
            case 5:
                return WxConsts.MenuButtonType.LOCATION_SELECT;
            //case 6:
            //    return WxConsts.MenuButtonType.MEDIA_ID;
            //case 7:
            //    return WxConsts.MenuButtonType.VIEW_LIMITED;
        }

        return WxConsts.MenuButtonType.CLICK;

    }


    @RequestMapping("/addView")
    public String addView() throws WxErrorException {
        WxMpMenu wxMpMenu = menuGet();
        WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
        List<WxMenuButton> buttons = menu.getButtons();


        return "";
    }


    @RequestMapping("/delete")
    public String delete() throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
        return "|";
    }


    @RequestMapping("/addSubView")
    public String addSubView() {


        return null;
    }


}
