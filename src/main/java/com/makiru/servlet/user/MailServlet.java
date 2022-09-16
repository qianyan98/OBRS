package com.makiru.servlet.user;

import com.makiru.utils.MailUtil;
import com.makiru.utils.VerifyCodeUtil;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(!StringUtils.isNullOrEmpty(method)){
            switch (method){
                case "sendVerifyCode":
                    this.sendVerifyCode(req, resp);
                    break;
                case "sendNotification":
                    this.sendNotification(req, resp);
                    break;
            }
        }else{
            req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void sendVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if(!StringUtils.isNullOrEmpty(email)){
            String code = VerifyCodeUtil.getVerifyCode();
            req.getSession().setAttribute("VERIFY_CODE", code);
            MailUtil mailUtil = new MailUtil(email, "注册验证", "欢迎注册，您的验证码是" + code);
            mailUtil.start();
            resp.getWriter().write("发送成功");
        }else{
            resp.getWriter().write("发送失败");
        }
    }

    private void sendNotification(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sendTo = req.getParameter("sendTo");
        String sendFrom = req.getParameter("sendFrom");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");

        if(!StringUtils.isNullOrEmpty(sendFrom)
                && !StringUtils.isNullOrEmpty(sendTo)
                && !StringUtils.isNullOrEmpty(subject)
                && !StringUtils.isNullOrEmpty(content)){
            MailUtil mailUtil = new MailUtil(sendTo, sendFrom, subject, content);
            mailUtil.start();
            resp.getWriter().write("发送成功");
        }else{
            resp.getWriter().write("发送失败");
        }
    }
}
