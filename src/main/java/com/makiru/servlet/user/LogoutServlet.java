package com.makiru.servlet.user;

import com.makiru.utils.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        if(o != null){
            req.getSession().removeAttribute(Constant.USER_SESSION);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }else{
            //基本不会出现，出现了表明非法访问
            req.getRequestDispatcher(req.getContextPath() + "/error/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
