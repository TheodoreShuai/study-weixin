package cc.niushuai.study.weixin.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 微信令牌
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinAccessToken {
    private String accessToken;
    private long expirationTime;


    @Override
    public String toString(){
        String formatDate = DateUtil.format(new Date(expirationTime), "yyyy-MM-dd HH:mm:ss");
        return "[令牌:"+accessToken+", 过期时间:"+formatDate+"]";
    }
}
