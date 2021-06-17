package cn.zucc.edu.club.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        //边框
        properties.put("kaptcha.border", "no");
        //文本颜色
        properties.put("kaptcha.textproducer.font.color", "black");
        //字符串空行
        properties.put("kaptcha.textproducer.char.space", "4");
        //高度
        properties.put("kaptcha.image.height", "40");
        //宽
        properties.put("kaptcha.image.width", "120");
        //字符大小
        properties.put("kaptcha.textproducer.font.size", "30");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
