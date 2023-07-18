package com.education.system.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    DefaultKaptcha producer() {
        Properties properties = new Properties();

        properties.put("kaptcha.obscurificator.impl", "com.education.system.config.NoWaterRipple");
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.names", "Courier,Arial");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
//        properties.put("kaptcha.noise.color", "204,255,229");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "2");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.image.width", "100");
        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.background-color.from", "lightGray");
        properties.put("kaptcha.background-color.to", "white");
//        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
