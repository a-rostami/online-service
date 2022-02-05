package com.rostami.onlineservice.config.util.captcha;

import com.rostami.onlineservice.controller.util.captcha.CaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {

    @Bean
    public ServletRegistrationBean<CaptchaServlet> customServletBean() {
        return new ServletRegistrationBean<>(new CaptchaServlet(), "/secure/captcha/captcha.jpg");
    }
}
