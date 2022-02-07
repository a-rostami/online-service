package com.rostami.onlineservice.controller.util.captcha;

import com.rostami.onlineservice.exception.InvalidCaptchaException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class CaptchaVerificationController {

    @GetMapping(value = "/verifyCaptcha")
    public ResponseEntity<String> getProfileById(@RequestParam("captchaText") String captchaText, HttpSession session) {
        String captcha = (String) session.getAttribute("CAPTCHA");
        if (StringUtils.isBlank(captcha) || (!StringUtils.isBlank(captcha) && !captcha.equals(captchaText)))
            throw new InvalidCaptchaException("Captcha Is Invalid");

        return ResponseEntity.ok("Captcha Valid");
    }

}
