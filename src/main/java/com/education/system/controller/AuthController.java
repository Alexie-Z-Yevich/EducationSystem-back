package com.education.system.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.education.system.common.lang.Const;
import com.education.system.common.lang.Result;
import com.education.system.entity.StuMessage;
import com.education.system.service.StuMessageService;
import com.education.system.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@RestController
public class AuthController {

    @Autowired
    Producer producer;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    StuMessageService stuMessageService;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
//        String key = UUID.randomUUID().toString();
//        String code = producer.createText();

        String key = "aaaaa";
        String code = "11111";

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

        return Result.succ(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64Img)
                        .build()
        );
    }

//     获取用户信息接口
//    @GetMapping("/sys/userInfo")
//    public Result userInfo(Principal principal) {
//
//        StuMessage stsMessage = stuMessageService.getByStuId(principal.getName());
//
//        return Result.succ(MapUtil.builder()
//                .put("id", stsMessage.getId())
//                .put("username", stsMessage.getName())
////                .put("avatar", stsMessage.getAvatar())
////                .put("created", stsMessage.getC())
//                .map()
//        );
//    }



}
