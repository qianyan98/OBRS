package com.makiru.servlet.book;

import com.makiru.dto.ReserveDto;
import com.makiru.pojo.Book;
import com.makiru.pojo.Reserve;
import com.makiru.pojo.User;
import com.makiru.service.book.BookService;
import com.makiru.service.book.BookServiceImpl;
import com.makiru.service.user.UserService;
import com.makiru.service.user.UserServiceImpl;
import com.makiru.utils.Constant;
import com.makiru.utils.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookServlet extends HttpServlet {

    private BookService bookService;
    private UserService userService;

    public BookServlet() {
        bookService = new BookServiceImpl();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(!StringUtils.isNullOrEmpty(method)){
            switch (method) {
                case "bookQuery":
                    this.getBookList(req, resp);
                    break;
                case "toReserveBook":
                    this.toReserveBook(req, resp);
                    break;
                case "reserveBook":
                    this.reserveBook(req, resp);
                    break;
                case "viewBook":
                    this.viewBook(req, resp);
                    break;
                case "reserveQuery":
                    this.getReserveList(req, resp);
                    break;
                case "deleteReserve":
                    this.deleteReserve(req, resp);
                    break;
                case "toRenewBook":
                    this.toRenewBook(req, resp);
                    break;
                case "renewBook":
                    this.renewBook(req, resp);
                    break;
                case "deleteBook":
                    this.deleteBook(req, resp);
                    break;
                case "toUpdateBook":
                    this.toUpdateBook(req, resp);
                    break;
                case "updateBook":
                    this.updateBook(req, resp);
                    break;
                case "addBook":
                    this.addBook(req, resp);
                    break;
                case "getAllReserve":
                    this.getAllReserve(req, resp);
                    break;
                case "toNotification":
                    this.toNotification(req, resp);
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

    /*获取目前库内所有书籍，主页面预约图书的列表信息*/
    private void getBookList(HttpServletRequest req, HttpServletResponse resp){
        List<Book> bookList = null;
        int totalCount = 0;
        Date startDate = null;
        Date endDate = null;
        String bookName = null;
        String bookAuthor = null;
        int pageIndex = 1;
        int pageSize = 5;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        if(o != null){
            bookName = req.getParameter("bookName");
            bookAuthor = req.getParameter("bookAuthor");
            if(req.getParameter("pageIndex") != null){
                pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
            }
            try {
                if(!StringUtils.isNullOrEmpty(req.getParameter("startDate")) &&  !StringUtils.isNullOrEmpty(req.getParameter("endDate"))){
                    startDate = fmt.parse(req.getParameter("startDate"));
                    endDate = fmt.parse(req.getParameter("endDate"));
                }

                Properties properties = new Properties();
                InputStream resource = BookServlet.class.getClassLoader().getResourceAsStream("config.properties");
                properties.load(resource);
                pageSize = Integer.parseInt(properties.getProperty("pageSize"));

                bookList = bookService.getBookList(pageIndex, pageSize, bookName, bookAuthor, startDate, endDate);
                totalCount = bookService.getBookCount(bookName, bookAuthor, startDate, endDate);
                req.setAttribute("bookList", bookList);
                PageSupport pageSupport = new PageSupport();
                pageSupport.setPageSize(pageSize);
                pageSupport.setCurrentPage(pageIndex);
                pageSupport.setTotalCount(totalCount);
                req.setAttribute("currentPageNum", pageIndex);
                List<Integer> pageNumList = new ArrayList<>();
                for (int i = 0; i < pageSupport.getTotalPageNum(); i++) {
                    pageNumList.add(i + 1);
                }
                req.setAttribute("pageNumList", pageNumList);
                req.setAttribute("totalPageNum", pageSupport.getTotalPageNum());
                req.setAttribute("bookName", bookName);
                req.setAttribute("bookAuthor", bookAuthor);
                req.setAttribute("startDate", startDate == null ? "" : fmt.format(startDate));
                req.setAttribute("endDate", endDate == null ? "" : fmt.format(endDate));
                if(((User)o).getUserRole() == 2){
                    req.getRequestDispatcher("allBookList.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("bookManage.jsp").forward(req, resp);
                }

            } catch (ParseException e) {
                System.out.println("时间转换出错");
            } catch (IOException e) {
                System.out.println("IO  错误");
            } catch (ServletException e) {
                System.out.println("servlet报错");
            }
        }else {
            try {
                req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*前往预约书的页面*/
    private void toReserveBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("bookId");
        Book book = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if(!StringUtils.isNullOrEmpty(bookId)){
            book = bookService.getBookById(bookId);
            req.setAttribute("book", book);
            req.setAttribute("startDate", dateFormat.format(new Date()));
            req.setAttribute("msg", "");
            req.getRequestDispatcher("/front/reserveBook.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
        }
    }
    /*插入预约记录，并更新书籍状态*/
    private void reserveBook(HttpServletRequest req, HttpServletResponse resp){
        //获取在线用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        String userId = null;
        if(user != null){
            userId = user.getUserId();
        }
        String bookId = req.getParameter("bookId");
        Date startDate = null;
        Date endDate = null;
        String msg = "预约失败";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if(!StringUtils.isNullOrEmpty(req.getParameter("startDate"))
                && !StringUtils.isNullOrEmpty("reserveEndDate")){
            try {
                startDate = dateFormat.parse(req.getParameter("startDate"));
                endDate = dateFormat.parse(req.getParameter("reserveEndDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isNullOrEmpty(userId)
                && !StringUtils.isNullOrEmpty(bookId)
                && startDate != null
                && endDate != null){
            int updateRow1 = bookService.insertReserve(bookId, userId, startDate, endDate);
            if(updateRow1 > 0){
                msg = "预约成功";
            }else{
                msg = "预约失败";
                System.out.println("预约记录插入失败或者更新书籍状态失败");
            }
            req.setAttribute("book", bookService.getBookById(bookId));
            req.setAttribute("startDate", dateFormat.format(startDate));
            req.setAttribute("reserveEndDate", dateFormat.format(endDate));
            req.setAttribute("msg", msg);
            try {
                req.getRequestDispatcher("reserveBook.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }else{
            req.setAttribute("book", bookService.getBookById(bookId));
            req.setAttribute("startDate", startDate == null ? "" : dateFormat.format(startDate));
            req.setAttribute("reserveEndDate", endDate == null ? "" : dateFormat.format(endDate));
            req.setAttribute("msg", msg);
            try {
                req.getRequestDispatcher("reserveBook.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*前往查看图书的页面*/
    private void viewBook(HttpServletRequest req, HttpServletResponse resp){
        String bookId = req.getParameter("bookId");
        if(!StringUtils.isNullOrEmpty(bookId)){
            Book book = bookService.getBookById(bookId);
            req.setAttribute("book", book);
            try {
                req.getRequestDispatcher("viewBook.jsp").forward(req, resp);
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
    /*查看自己的所有预约，并管理*/
    private void getReserveList(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        String userId = null;
        String bookName = null;
        String bookAuthor = null;
        int pageSize = 5;
        int pageIndex = 1;
        Date startDate = null;
        Date endDate = null;
        int totalCount = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        List<Reserve> reserveList = new ArrayList<>();

        if(user != null){
            userId = user.getUserId();
            bookName = req.getParameter("bookName");
            bookAuthor = req.getParameter("bookAuthor");
            if(req.getParameter("pageIndex") != null){
                pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
            }
            try {
                InputStream inputStream = BookServlet.class.getClassLoader().getResourceAsStream("config.properties");
                Properties properties = new Properties();
                properties.load(inputStream);

                if(properties.getProperty("pageSize") != null){
                    pageSize = Integer.parseInt(properties.getProperty("pageSize"));
                }

                if(!StringUtils.isNullOrEmpty(req.getParameter("startDate")) && !StringUtils.isNullOrEmpty(req.getParameter("endDate"))){
                    startDate = format.parse(req.getParameter("startDate"));
                    endDate = format.parse(req.getParameter("endDate"));
                }

                reserveList = bookService.getReserveList(userId, pageIndex, pageSize, bookName, bookAuthor, startDate, endDate);
                totalCount = bookService.getReserveCount(userId, bookName, bookAuthor, startDate, endDate);
                //获取分页
                PageSupport pageSupport = new PageSupport();
                pageSupport.setPageSize(pageSize);
                pageSupport.setCurrentPage(pageIndex);
                pageSupport.setTotalCount(totalCount);

                req.setAttribute("reserveList", reserveList);
                req.setAttribute("bookName", bookName);
                req.setAttribute("bookAuthor", bookAuthor);
                req.setAttribute("startDate", startDate == null ? "" : format.format(startDate));
                req.setAttribute("endDate", endDate == null ? "" : format.format(endDate));
                req.setAttribute("currentPageNum", pageIndex);
                List<Integer> pageNumList = new ArrayList<>();
                for (int i = 0; i < pageSupport.getTotalPageNum(); i++) {
                    pageNumList.add(i + 1);
                }
                req.setAttribute("pageNumList", pageNumList);
                req.setAttribute("totalPageNum", pageSupport.getTotalPageNum());
                req.setAttribute(Constant.MESSAGE, getStatusOfReserve(userId));

                req.getRequestDispatcher("myReserve.jsp").forward(req, resp);
            } catch (IOException | ParseException | ServletException e) {
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

    private String getStatusOfReserve(String userId){
        List<Reserve> reserveList = bookService.getReserveList(userId, -1, -1, null, null, null, null);
        int expiring = 0;
        int expired = 0;

        Date now = new Date();
        long now_time = now.getTime();

        for (Reserve reserve : reserveList) {
            Date endDate = reserve.getEndDate();
            long end_time = endDate.getTime();
            int sub = (int) ((end_time - now_time) / (1000 * 60 * 60 * 24));
            if(sub <= Constant.EXPIRING && sub >= 0){
                expiring++;
            }
            if(sub < 0){
                expired++;
            }
        }
        if(expired > 0 && expiring > 0){
            return "您的预约中，有" + expired + "本书已逾期，有" + expiring + "本书即将逾期，请注意";
        }else if(expiring > 0){
            return "您的预约中，有" + expiring + "本书即将逾期，请注意";
        }else if(expired > 0){
            return "您的预约中，有" + expired + "本书已逾期，请注意";
        }
        return "";
    }

    /*删除预约，更新书籍状态*/
    private void deleteReserve(HttpServletRequest req, HttpServletResponse resp){
        String reserveId = req.getParameter("reserveId");
        String bookId = req.getParameter("bookId");
        String msg = "";
        User user = ((User) req.getSession().getAttribute(Constant.USER_SESSION));
        String userId = null;
        if(user != null){
            userId = user.getUserId();
        }
        if(!StringUtils.isNullOrEmpty(reserveId)
                && !StringUtils.isNullOrEmpty(bookId)
                && !StringUtils.isNullOrEmpty(userId)){
            int updateRow = bookService.deleteReserve(reserveId, bookId, userId);
            if(updateRow > 0){
                msg = "删除成功";
            }else{
                msg = "删除失败";
            }
        } else {
            msg = "删除失败";
        }
        try {
            resp.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*前往续借页面*/
    private void toRenewBook(HttpServletRequest req, HttpServletResponse resp){
        String bookId = req.getParameter("bookId");
        String reserveId = req.getParameter("reserveId");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);

        if(!StringUtils.isNullOrEmpty(bookId) && !StringUtils.isNullOrEmpty(reserveId)){
            Book book = bookService.getBookById(bookId);
            req.setAttribute("book", book);
            req.setAttribute("reserveId", reserveId);
            req.setAttribute("startDate", df.format(new Date()));
            try {
                if(user.getUserRole() == 2){
                    req.getRequestDispatcher("renewBook.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("renewReserve.jsp").forward(req, resp);
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
    /*续借*/
    private void renewBook(HttpServletRequest req, HttpServletResponse resp){
        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        String userId = null;
        String reserveId = null;
        String bookId = null;
        Date startDate = null;
        Date endDate = null;
        String msg = "续借失败";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        userId = user.getUserId();
        reserveId = req.getParameter("reserveId");
        bookId = req.getParameter("bookId");
        if(!StringUtils.isNullOrEmpty(req.getParameter("startDate"))
                && !StringUtils.isNullOrEmpty(req.getParameter("reserveEndDate"))){
            try {
                startDate = df.parse(req.getParameter("startDate"));
                endDate = df.parse(req.getParameter("reserveEndDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isNullOrEmpty(userId)
                && !StringUtils.isNullOrEmpty(reserveId)
                && startDate != null && endDate != null
                && !StringUtils.isNullOrEmpty(bookId)){
            int updateRow = 0;
            if(user.getUserRole() == 1){
                Reserve reserve = bookService.getReserveById(reserveId);
                updateRow = bookService.updateReserve(reserveId, reserve.getUserId(), startDate, endDate);
            }else{
                updateRow = bookService.updateReserve(reserveId, userId, startDate, endDate);
            }
            if(updateRow > 0){
                msg = "续借成功";
            }else{
                msg = "续借失败";
                System.out.println("reserve表更新失败");
            }
            req.setAttribute("book", bookService.getBookById(bookId));
            req.setAttribute("reserveEndDate", df.format(endDate));
            req.setAttribute(Constant.MESSAGE, msg);
            try {
                if(user.getUserRole() == 1){
                    req.getRequestDispatcher("renewReserve.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("renewBook.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }else{
            if(!StringUtils.isNullOrEmpty(bookId)){
                req.setAttribute("book", bookService.getBookById(bookId));
            }
            req.setAttribute(Constant.MESSAGE, msg);
            req.setAttribute("reserveEndDate", endDate == null ? "" : df.format(endDate));
            try {
                if(user.getUserRole() == 2){
                    req.getRequestDispatcher("renewReserve.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("renewBook.jsp").forward(req, resp);
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*删除图书*/
    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookId = req.getParameter("bookId");
        String msg = "";
        if(!StringUtils.isNullOrEmpty(bookId)){
            int result = bookService.deleteBookById(bookId);
            if(result > 0){
                msg = "删除成功";
            }else{
                msg = "删除失败";
            }
        }else{
            msg = "删除失败";
        }
        resp.getWriter().write(msg);
    }
    /*前往更新书籍信息页面*/
    private void toUpdateBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("bookId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!StringUtils.isNullOrEmpty(bookId)){
            Book book = bookService.getBookById(bookId);
            if(book != null){
                req.setAttribute("bookPublishDate", book.getBookPublishDate() == null ? "" : sdf.format(book.getBookPublishDate()));
                req.setAttribute("book", book);
            }else{
                req.setAttribute(Constant.MESSAGE, "该书籍不存在");
            }
            req.getRequestDispatcher("updateBook.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
        }
    }
    /*更新书籍信息*/
    private void updateBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("bookId");
        String bookName = req.getParameter("bookName");
        String bookAuthor = req.getParameter("bookAuthor");
        Date bookPublishDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!StringUtils.isNullOrEmpty(req.getParameter("bookPublishDate"))){
            try {
                bookPublishDate = sdf.parse(req.getParameter("bookPublishDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String bookType = req.getParameter("bookType");
        String bookDescription = req.getParameter("bookDescription");
        String msg = "";
        Book book = new Book();
        if(!StringUtils.isNullOrEmpty(bookId)
                && !StringUtils.isNullOrEmpty(bookName)
                && !StringUtils.isNullOrEmpty(bookAuthor)
                && !StringUtils.isNullOrEmpty(bookType)
                && !StringUtils.isNullOrEmpty(bookDescription)
                && bookPublishDate != null){
            book.setBookId(bookId);
            book.setBookName(bookName);
            book.setBookAuthor(bookAuthor);
            book.setBookPublishDate(bookPublishDate);
            book.setBookType(bookType);
            book.setBookDescription(bookDescription);
            int result = bookService.updateBook(book);
            if(result > 0){
                msg = "更新成功";
            }else{
                msg = "更新失败";
            }
        }else{
            msg = "更新失败";
        }
        req.setAttribute("book", book);
        req.setAttribute("bookPublishDate", book.getBookPublishDate() == null ? "" : sdf.format(book.getBookPublishDate()));
        req.setAttribute(Constant.MESSAGE, msg);
        req.getRequestDispatcher("updateBook.jsp").forward(req, resp);
    }

    public void addBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookName = req.getParameter("bookName");
        String bookAuthor = req.getParameter("bookAuthor");
        Date bookPublishDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!StringUtils.isNullOrEmpty(req.getParameter("bookPublishDate"))){
            try {
                bookPublishDate = sdf.parse(req.getParameter("bookPublishDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String bookType = req.getParameter("bookType");
        String bookDescription = req.getParameter("bookDescription");
        String msg = "";
        Book book = new Book();
        if(!StringUtils.isNullOrEmpty(bookName)
                && !StringUtils.isNullOrEmpty(bookAuthor)
                && !StringUtils.isNullOrEmpty(bookType)
                && !StringUtils.isNullOrEmpty(bookDescription)
                && bookPublishDate != null){
            book.setBookName(bookName);
            book.setBookAuthor(bookAuthor);
            book.setBookPublishDate(bookPublishDate);
            book.setBookType(bookType);
            book.setBookDescription(bookDescription);
            int result = bookService.addBook(book);
            if(result > 0){
                msg = "添加成功";
            }else{
                msg = "添加失败";
            }
        }else{
            msg = "添加失败";
        }
        req.setAttribute("book", book);
        req.setAttribute("bookPublishDate", book.getBookPublishDate() == null ? "" : sdf.format(book.getBookPublishDate()));
        req.setAttribute(Constant.MESSAGE, msg);
        req.getRequestDispatcher("addBook.jsp").forward(req, resp);
    }

    private void getAllReserve(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userName = req.getParameter("userName");
        String reserveId = req.getParameter("reserveId");
        int reserveStatus = StringUtils.isNullOrEmpty(req.getParameter("status")) ? -1 : Integer.parseInt(req.getParameter("status"));
        int pageIndex = StringUtils.isNullOrEmpty(req.getParameter("pageIndex")) ? 1 : Integer.parseInt(req.getParameter("pageIndex"));

        int pageSize = 5;
        Properties properties = new Properties();
        InputStream stream = BookServlet.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(stream);
        pageSize = Integer.parseInt(properties.getProperty("pageSize", "5"));

        List<ReserveDto> reserveList = bookService.getAllReserve(userName, reserveId, reserveStatus, pageIndex, pageSize);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPage(pageIndex);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(bookService.getReserveCount(userName, reserveId, reserveStatus));

        int[] numList = new int[pageSupport.getTotalPageNum()];
        for (int i = 0; i < numList.length; i++) {
            numList[i] = i + 1;
        }

        req.setAttribute("reserveList", reserveList);
        req.setAttribute("userName", userName);
        req.setAttribute("reserveId", reserveId);
        req.setAttribute("currentPageNum", pageIndex);
        req.setAttribute("pageNumList", numList);
        req.setAttribute("totalPageNum", pageSupport.getTotalPageNum());
        req.getRequestDispatcher("reserveManage.jsp").forward(req, resp);
    }

    private void toNotification(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reserveId = req.getParameter("reserveId");
        if(!StringUtils.isNullOrEmpty(reserveId)){
            //获得预约人的邮箱地址
            Reserve reserve = bookService.getReserveById(reserveId);
            User user = userService.getUserById(reserve.getUserId());
            String sendTo = user.getUserEmail();
            //获得发件人的邮箱
            User user1 = userService.getUserById(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getUserId());
            String sendFrom = user1.getUserEmail();
            req.setAttribute("sendTo", sendTo);
            req.setAttribute("sendFrom", sendFrom);
            req.getRequestDispatcher("emailNotification.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
        }
    }
}
