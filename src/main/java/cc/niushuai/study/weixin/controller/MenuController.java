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
import org.springframework.web.bind.annotation.PathVariable;
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
            button4.setKey("V1001_Fans");
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
        WxMpMenu wxMpMenu = menuGet();
        WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
        List<WxMenuButton> buttons = menu.getButtons();

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> menuButtons = wxMenu.getButtons();

        for (int i = 0; i < buttons.size(); i++) {
            WxMenuButton btn = buttons.get(i);
            String name = btn.getName();
            List<WxMenuButton> buttonList = null;
            if(name.equals("业务查询")){
                buttonList = getButtonList(new String[]{"流量查询", "话费查询", "套餐余量", "已开业务"});
            }else if(name.equals("业务办理")){
                buttonList = getButtonList(new String[]{"话费充值","宽带新装"});
            }else if(name.equals("粉丝福利")){
                buttonList = getButtonList(new String[]{"新人再领500M","疯狂星期五","查网龄再送流量"});
            }
            btn.getSubButtons().addAll(buttonList);
            menuButtons.add(btn);
        }

        wxMpService.getMenuService().menuCreate(wxMenu);

    }

    private List<WxMenuButton> getButtonList(String[] btns) {
        List<WxMenuButton> result = new ArrayList<>();
        for (int i = 0; i < btns.length; i++) {
            WxMenuButton btn = new WxMenuButton();
            btn.setKey("V1001_"+i);
            btn.setName(btns[i]);
            btn.setType(WxConsts.MenuButtonType.CLICK);
            result.add(btn);
        }
        return result;
    }



    @RequestMapping("/addView")
    public String addView() throws WxErrorException {
        WxMpMenu wxMpMenu = menuGet();
        WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
        List<WxMenuButton> buttons = menu.getButtons();




        return "";
    }


    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
        return "|";
    }


    @RequestMapping("/addSubView")
    public String addSubView(){


        return null;
    }


}
