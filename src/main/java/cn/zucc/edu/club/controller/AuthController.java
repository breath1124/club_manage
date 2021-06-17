package cn.zucc.edu.club.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.zucc.edu.club.entity.Const;
import cn.zucc.edu.club.entity.Result;
import cn.zucc.edu.club.util.RedisUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Api(tags = "图片验证码")
@RestController
public class AuthController {

    @Autowired
    Producer producer;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("生成图片验证码")
    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        //生成验证码
        String code = producer.createText();

        //测试用
//        key = "aaaaa";
//        code = "11111";

        //生成图片
        BufferedImage image=producer.createImage(code);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",out);
        //转换成base64位编码
        BASE64Encoder encoder = new BASE64Encoder();
        String str="data:image/jpeg;base64,";//前缀

        String base64Img = str + encoder.encode(out.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code,120);
        return Result.success(
                MapUtil.builder().put("token", key)
                        .put("captchaImg", base64Img)
                        .build()
        );
    }

}
