package com.makiru.servlet.user;

import com.makiru.pojo.User;
import com.makiru.service.user.UserService;
import com.makiru.service.user.UserServiceImpl;
import com.makiru.utils.Constant;
import com.makiru.utils.IdUtil;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(!StringUtils.isNullOrEmpty(method)){
            switch (method){
                case "isNameExist":
                    this.isNameExist(req, resp);
                    break;
                case "register":
                    this.register(req, resp);
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

    private void isNameExist(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("userName");
        UserService userService = new UserServiceImpl();
        String msg = "";
        User user = userService.getUserByName(userName);
        if(user == null){
            msg = "不存在";
        }else{
            msg = "存在";
        }
        try {
            resp.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("username");
        String userPassword = req.getParameter("password");
        String email = req.getParameter("email");
        String verifyCode = req.getParameter("verificationCode");
        String msg = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        UserService userService = new UserServiceImpl();

        if(!StringUtils.isNullOrEmpty(userName)
                && !StringUtils.isNullOrEmpty(userPassword)
                && !StringUtils.isNullOrEmpty(email)
                && !StringUtils.isNullOrEmpty(verifyCode)){
            String code = (String) req.getSession().getAttribute("VERIFY_CODE");
            if(!StringUtils.isNullOrEmpty(code) && code.equals(verifyCode)){
                User user = new User();
                user.setUserId(IdUtil.getNextId());
                user.setUserName(userName);
                try {
                    user.setUserBirthday(dateFormat.parse(Constant.USER_BIRTHDAY));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                user.setUserPhone(Constant.USER_PHONE);
                user.setUserAddress(Constant.USER_ADDRESS);
                user.setGender(Constant.USER_GENDER);
                user.setUserPassword(userPassword);
                user.setUserEmail(email);
                user.setUserRole(2);
                int updateRow = userService.insertUser(user);
                if(updateRow > 0){
                    msg = "注册成功";
                }else{
                    msg = "注册失败";
                }
            }else{
                msg = "验证码输入错误";
            }
        }else{
            msg = "注册信息不全";
        }
        req.setAttribute("username", userName);
        req.setAttribute("password", userPassword);
        req.setAttribute("email", email);
        req.setAttribute("verificationCode", verifyCode);
        req.getSession().removeAttribute("VERIFY_CODE");
        req.setAttribute(Constant.MESSAGE, msg);
        try {
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
