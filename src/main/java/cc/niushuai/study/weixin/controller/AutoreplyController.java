/**
 * @Name AutoreplyController
 * @Author niushuai
 * @Date 2018/12/6 14:13
 * @Desc 自动回复业务处理
 */
package cc.niushuai.study.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpCurrentAutoReplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ar")
public class AutoreplyController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/autoreply")
    @ResponseBody
    public String testAutoreply(){
        try {
            WxMpCurrentAutoReplyInfo currentAutoReplyInfo = wxMpService.getCurrentAutoReplyInfo();
            WxMpCurrentAutoReplyInfo.AutoReplyInfo addFriendAutoReplyInfo = new WxMpCurrentAutoReplyInfo.AutoReplyInfo();
            WxMpCurrentAutoReplyInfo.AutoReplyInfo messageDefaultAutoReplyInfo = new WxMpCurrentAutoReplyInfo.AutoReplyInfo();
            WxMpCurrentAutoReplyInfo.KeywordAutoReplyInfo keywordAutoReplyInfo = new WxMpCurrentAutoReplyInfo.KeywordAutoReplyInfo();

            addFriendAutoReplyInfo.setType("text");
            addFriendAutoReplyInfo.setContent("Hello 关注者.");

            messageDefaultAutoReplyInfo.setType("text");
            messageDefaultAutoReplyInfo.setContent("Hello 这是自动回复的内容.");

            List<WxMpCurrentAutoReplyInfo.AutoReplyRule> list = new ArrayList<>();
            WxMpCurrentAutoReplyInfo.AutoReplyRule e = new WxMpCurrentAutoReplyInfo.AutoReplyRule();
            e.setRuleName("autoreply_msg");
            e.setReplyMode("reply_all");
            //设置触发关键字
            List<WxMpCurrentAutoReplyInfo.KeywordInfo> keywordListInfo = new ArrayList<>();
            WxMpCurrentAutoReplyInfo.KeywordInfo keyword = new WxMpCurrentAutoReplyInfo.KeywordInfo();
            keyword.setType("text");
            keyword.setContent("这是关键字");
            keyword.setMatchMode("contain");

            keywordListInfo.add(keyword);
            e.setKeywordListInfo(keywordListInfo);
            //设置回复列表
            List<WxMpCurrentAutoReplyInfo.ReplyInfo> replyListInfo = new ArrayList<>();
            WxMpCurrentAutoReplyInfo.ReplyInfo replyinfo = new WxMpCurrentAutoReplyInfo.ReplyInfo();
            replyinfo.setType("news");
            replyinfo.setContent("KQb_w_Tiz-nSdVLoTV35Psmty8hGBulGhEdbb9SKs-o");
            //设置回复内容
            WxMpCurrentAutoReplyInfo.NewsInfo newsInfo = new WxMpCurrentAutoReplyInfo.NewsInfo();
            List<WxMpCurrentAutoReplyInfo.NewsItem> newItems = new ArrayList<>();
            WxMpCurrentAutoReplyInfo.NewsItem item = new WxMpCurrentAutoReplyInfo.NewsItem();
            item.setTitle("这是标题");
            item.setAuthor("这是作者");
            item.setShowCover(true);
            item.setCoverUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2168427908,4072089613&fm=200&gp=0.jpg");
            item.setDigest("text");
            item.setContentUrl("http://www.bejson.com/knownjson/msg/");
            item.setSourceUrl("");

            newItems.add(item);
            newsInfo.setList(newItems);
            replyinfo.setNewsInfo(newsInfo);
            replyinfo.setContent("");
            replyListInfo.add(replyinfo);

            e.setReplyListInfo(replyListInfo);

            e.setCreateTime(new Date());

            list.add(e);

            keywordAutoReplyInfo.setList(list);


            currentAutoReplyInfo.setAddFriendAutoReplyInfo(addFriendAutoReplyInfo);
            currentAutoReplyInfo.setMessageDefaultAutoReplyInfo(messageDefaultAutoReplyInfo);
            currentAutoReplyInfo.setKeywordAutoReplyInfo(keywordAutoReplyInfo);

            ///System.out.println(JSONObject.toJSONString(addFriendAutoReplyInfo));
            //System.out.println(JSONObject.toJSONString(messageDefaultAutoReplyInfo));
            //System.out.println(JSONObject.toJSONString(keywordAutoReplyInfo));
            System.out.println(JSONObject.toJSONString(currentAutoReplyInfo));

            return JSONObject.toJSONString(currentAutoReplyInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "";
    }

}
