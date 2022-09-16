package com.makiru.servlet.user;

import com.alibaba.fastjson2.JSON;
import com.makiru.pojo.User;
import com.makiru.service.user.UserService;
import com.makiru.service.user.UserServiceImpl;
import com.makiru.utils.Constant;
import com.makiru.utils.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import com.alibaba.fastjson2.JSONObject;

public class UserServlet extends HttpServlet {
    private UserService userService;

    public UserServlet() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(!StringUtils.isNullOrEmpty(method)){
            switch (method){
                case "viewUser":
                    this.viewUser(req, resp);
                    break;
                case "toModifyUser":
                    this.toModifyUser(req, resp);
                    break;
                case "isUserNameExist":
                    this.isUserNameExist(req, resp);
                    break;
                case "modifyUser":
                    this.modifyUser(req, resp);
                    break;
                case "checkOldPwd":
                    this.checkOldPwd(req, resp);
                    break;
                case "changePwd":
                    this.changePwd(req, resp);
                    break;
                case "userQuery":
                    this.getUserList(req, resp);
                    break;
                case "deleteUser":
                    this.deleteUser(req, resp);
                    break;
                case "toUpdateUser":
                    this.toUpdateUser(req, resp);
                    break;
                case "updateUser":
                    this.updateUser(req, resp);
                    break;
                case "getLikeUserName":
                    this.getLikeUserName(req, resp);
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

    private void viewUser(HttpServletRequest req, HttpServletResponse resp){
        User user1 = userService.getUserById(((User) req.getSession().getAttribute(Constant.USER_SESSION)).getUserId());
        User user = null;
        if(user1.getUserRole() == 1){
            String userId = req.getParameter("userId");
            if(!StringUtils.isNullOrEmpty(userId)){
                user = userService.getUserById(userId);
            }else{
                try {
                    req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            user = user1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(user != null){
            req.setAttribute("user", user);
            req.setAttribute("birthday", df.format(user.getUserBirthday()));
            try {
                if(user1.getUserRole() == 1){
                    req.getRequestDispatcher("viewUser.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("myInform.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void toModifyUser(HttpServletRequest req, HttpServletResponse resp){
        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(user != null){
            req.setAttribute("user", user);
            req.setAttribute("birthday", df.format(user.getUserBirthday()));
            try {
                req.getRequestDispatcher("modifyUser.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void isUserNameExist(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("userName");
        String msg = "";
        if(!StringUtils.isNullOrEmpty(userName)){
            User user = userService.getUserByName(userName);
            if(user != null){
                msg = "存在";
            }else{
                msg = "不存在";
            }
        }else{
            msg = "其他错误";
        }
        try {
            resp.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void modifyUser(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("userName");
        int gender = req.getParameter("gender") == null ? 1 : Integer.parseInt(req.getParameter("gender"));
        String userPhone = req.getParameter("userPhone");
        String userAddress = req.getParameter("userAddress");
        Date userBirthday = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String msg = "";
        if(!StringUtils.isNullOrEmpty(req.getParameter("userBirthday"))){
            try {
                userBirthday = df.parse(req.getParameter("userBirthday"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        User user = userService.getUserById(((User) req.getSession().getAttribute(Constant.USER_SESSION)).getUserId());
        if(!StringUtils.isNullOrEmpty(userName)
                && !StringUtils.isNullOrEmpty(userPhone)
                && !StringUtils.isNullOrEmpty(userAddress) && userBirthday != null){
            user.setUserName(userName);
            user.setUserPhone(userPhone);
            user.setUserAddress(userAddress);
            user.setUserBirthday(userBirthday);
            user.setGender(gender);

            int updateRow = userService.updateUser(user);
            if(updateRow > 0){
                msg = "更新成功";
            }else{
                msg = "更新失败";
            }
        }else{
            msg = "其他错误";
        }
        req.setAttribute("user", user);
        req.setAttribute("birthday", df.format(user.getUserBirthday()));
        req.getSession().setAttribute(Constant.USER_SESSION, user);
        req.setAttribute(Constant.MESSAGE, msg);
        try {

            req.getRequestDispatcher("modifyUser.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    private void checkOldPwd(HttpServletRequest req, HttpServletResponse resp){
        String userId = null;
        String oldPwd = null;
        User user = ((User) req.getSession().getAttribute(Constant.USER_SESSION));
        String msg = "";
        if(user != null){
            userId = user.getUserId();
            oldPwd = req.getParameter("oldPwd");
            User user1 = userService.getUserById(userId);
            if(user1.getUserPassword().equals(oldPwd)){
                msg = "密码正确";
            }else {
                msg = "密码错误";
            }
        }else{
            msg = "其他错误";
        }
        try {
            resp.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void changePwd(HttpServletRequest req, HttpServletResponse resp){
        String userId = null;
        String newPwd = null;
        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        if(user != null){
            userId = user.getUserId();
            newPwd = req.getParameter("newPwd");
            if(!StringUtils.isNullOrEmpty(newPwd)){
                int updateRow = userService.updatePwd(userId, newPwd);
                if(updateRow > 0){
                    req.setAttribute(Constant.MESSAGE, "密码修改成功，请重新登录");
                    req.getSession().removeAttribute(Constant.USER_SESSION);
                }else{
                    req.setAttribute(Constant.MESSAGE, "密码修改失败，请重新尝试");
                }
            }else{
                req.setAttribute(Constant.MESSAGE, "新密码为空");
            }
            try {
                req.getRequestDispatcher("changePwd.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        int userRole = StringUtils.isNullOrEmpty(req.getParameter("userRole")) ? -1 : Integer.parseInt(req.getParameter("userRole"));
        int gender = StringUtils.isNullOrEmpty(req.getParameter("gender")) ? -1 : Integer.parseInt(req.getParameter("gender"));
        int pageSize = 5;
        int pageIndex = StringUtils.isNullOrEmpty(req.getParameter("pageIndex")) ? 1 : Integer.parseInt(req.getParameter("pageIndex"));
        Properties properties = new Properties();
        InputStream inputStream = UserServlet.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pageSize = Integer.parseInt(properties.getProperty("pageSize", "5"));
        List<User> userList = userService.getUserList(userName, userRole, gender, pageSize, pageIndex);
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(pageSize);
        pageSupport.setCurrentPage(pageIndex);
        pageSupport.setTotalCount(userService.getUserCount(userName, userRole, gender));
        req.setAttribute("currentPageNum", pageIndex);
        List<Integer> pageNumList = new ArrayList<>();
        for (int i = 0; i < pageSupport.getTotalPageNum(); i++) {
            pageNumList.add(i + 1);
        }
        req.setAttribute("pageNumList", pageNumList);
        req.setAttribute("totalPageNum", pageSupport.getTotalPageNum());
        req.setAttribute("userName", userName);
        req.setAttribute("userList", userList);
        req.setAttribute("roleList", userService.getRoleList());
        req.getRequestDispatcher("userManage.jsp").forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        String msg = "";
        if(!StringUtils.isNullOrEmpty(userId)){
            int updateRow = userService.deleteUser(userId);
            if(updateRow > 0){
                msg = "删除成功";
            }else{
                msg = "删除失败";
            }
        }else{
            msg = "删除失败";
        }
        resp.getWriter().write(msg);
    }

    private void toUpdateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String msg = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!StringUtils.isNullOrEmpty(userId)){
            User user = userService.getUserById(userId);
            if(user != null){
                req.setAttribute("user", user);
                req.setAttribute("birthday", sdf.format(user.getUserBirthday()));
                req.getRequestDispatcher("updateUser.jsp").forward(req, resp);
            }else{
                msg = "其他错误";
                req.setAttribute(Constant.MESSAGE, msg);
                req.getRequestDispatcher("viewUser.jsp").forward(req, resp);
            }
        }else{
            msg = "其他错误";
            req.setAttribute(Constant.MESSAGE, msg);
            req.getRequestDispatcher("viewUser.jsp").forward(req, resp);
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp){
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        int gender = req.getParameter("gender") == null ? 1 : Integer.parseInt(req.getParameter("gender"));
        String userPhone = req.getParameter("userPhone");
        String userAddress = req.getParameter("userAddress");
        Date userBirthday = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String msg = "";
        if(!StringUtils.isNullOrEmpty(req.getParameter("userBirthday"))){
            try {
                userBirthday = df.parse(req.getParameter("userBirthday"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        User user = userService.getUserById(((User) req.getSession().getAttribute(Constant.USER_SESSION)).getUserId());
        User user1 = new User();
        if(!StringUtils.isNullOrEmpty(userName)
                && !StringUtils.isNullOrEmpty(userId)
                && !StringUtils.isNullOrEmpty(userPhone)
                && !StringUtils.isNullOrEmpty(userAddress) && userBirthday != null){
            user1.setUserId(userId);
            user1.setUserName(userName);
            user1.setUserPhone(userPhone);
            user1.setUserAddress(userAddress);
            user1.setUserBirthday(userBirthday);
            user1.setGender(gender);

            int updateRow = userService.updateUser(user1);
            if(updateRow > 0){
                msg = "更新成功";
            }else{
                msg = "更新失败";
            }
        }else{
            msg = "其他错误";
        }
        req.setAttribute("user", user1);
        req.setAttribute("birthday", df.format(user1.getUserBirthday()));
        if(user1.getUserId().equals(user.getUserId())){
            req.getSession().setAttribute(Constant.USER_SESSION, user1);
        }
        req.setAttribute(Constant.MESSAGE, msg);
        try {
            req.getRequestDispatcher("updateUser.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getLikeUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("userName");
        List<User> userList = new ArrayList<>();
        int recordCount = 5;
        Properties properties = new Properties();
        InputStream inputStream = UserServlet.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        recordCount = Integer.parseInt(properties.getProperty("recordCount", "5"));
        userList = userService.getLikeUserByName(userName, recordCount);
        resp.getWriter().println(JSONObject.toJSONString(userList));
    }
}
