package com.makiru.servlet.user;

import com.makiru.utils.VerifyCodeUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class verifyCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Random r = new Random();
        BufferedImage image = new BufferedImage(80,22,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Color c = new Color(200,150,255);
        g.setColor(c);//背景颜色
        g.fillRect(0, 0, 80, 22);//背景框
        g.setFont(new Font(null, Font.BOLD, 20));
        String code = VerifyCodeUtil.getVerifyCode();
        req.getSession().setAttribute("VERIFY_CODE", code);
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));//给字体一个随机的颜色
            g.drawString(code.charAt(i)+"", (i*15)+3, 18);
        }
        ImageIO.write(image,"JPG",resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
