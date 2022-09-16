package com.makiru.servlet.user;

import com.makiru.pojo.User;
import com.makiru.service.user.UserService;
import com.makiru.service.user.UserServiceImpl;
import com.makiru.utils.Constant;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String verifyCode = req.getParameter("verificationCode");
        User user = null;
        String msg = "";
        boolean tag = false;
        if(!StringUtils.isNullOrEmpty(userName)
                && !StringUtils.isNullOrEmpty(password)
                && !StringUtils.isNullOrEmpty(verifyCode)){
            String code = (String) req.getSession().getAttribute("VERIFY_CODE");
            if(verifyCode.equals(code)){
                UserService userService = new UserServiceImpl();
                user = userService.getUserByName(userName);
                if(user == null){
                    msg = "用户不存在";
                }else{
                    if(user.getUserPassword().equals(password)){
                        tag = true;
                    }else{
                        msg = "用户名或密码错误";
                    }
                }
            }else{
                msg = "验证码错误";
            }
        }else{
            msg = "登录失败";
        }
        if(tag && user.getUserRole().equals(Constant.NORMAL_USER)){
            user.setUserPassword("");   //抹掉密码再存入session
            req.getSession().setAttribute(Constant.USER_SESSION, user);
            resp.sendRedirect(req.getContextPath() + "/front/frame.jsp");
        }else if(tag && user.getUserRole().equals(Constant.ADMIN)){
            user.setUserPassword("");   //抹掉密码再存入session
            user.setUserEmail("");
            req.getSession().setAttribute(Constant.USER_SESSION, user);
            resp.sendRedirect(req.getContextPath() + "/background/frame.jsp");
        }else{
            req.setAttribute(Constant.MESSAGE, msg);
            req.getRequestDispatcher(req.getContextPath() + "/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
