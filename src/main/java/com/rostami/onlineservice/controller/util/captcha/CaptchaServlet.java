package com.rostami.onlineservice.controller.util.captcha;

import com.rostami.onlineservice.util.CaptchaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

public class CaptchaServlet extends HttpServlet {
    public static final String FILE_TYPE = "jpeg";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Max-Age", 0);
        response.setContentType("image/jpeg");
        String captchaStr = CaptchaUtil.generateCaptcha(6);

        try {
            int width = 200;
            int height = 80;
            Color backgroundColor = new Color(0, 255, 255);
            Color foregroundColor = new Color(0, 100, 0);
            Font font = new Font("Arial", Font.BOLD, 40);
            BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.OPAQUE);
            Graphics g = captchaImage.createGraphics();
            g.setFont(font);
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);
            g.setColor(foregroundColor);
            g.drawString(captchaStr, 30, 55);
            HttpSession session = request.getSession(true);
            session.setAttribute("CAPTCHA", captchaStr);
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(captchaImage, FILE_TYPE, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
