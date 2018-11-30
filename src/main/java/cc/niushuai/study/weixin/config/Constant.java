package cc.niushuai.study.weixin.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "constant")
@PropertySource(value = "classpath:/config/constant.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
public class Constant {

    private String confirmToken;
    private String appId;
    private String appSecret;
    private String aesKey;
}
