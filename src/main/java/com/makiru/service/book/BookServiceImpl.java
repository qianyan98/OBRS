package com.makiru.service.book;

import com.makiru.dao.book.BookDao;
import com.makiru.dao.book.BookDaoImpl;
import com.makiru.dto.ReserveDto;
import com.makiru.pojo.Book;
import com.makiru.pojo.Reserve;
import com.makiru.utils.Constant;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BookServiceImpl implements BookService{

    private BookDao bookDao;

    public BookServiceImpl() {
        bookDao = new BookDaoImpl();
    }

    @Override
    public List<Book> getBookList(int pageIndex, int pageSize, String bookName, String bookAuthor, Date startTime, Date endTime) {
        return bookDao.getBookList(pageIndex, pageSize, bookName, bookAuthor, startTime, endTime);
    }

    @Override
    public int getBookCount(String bookName, String bookAuthor, Date startTime, Date endTime) {
        return bookDao.getBookCount(bookName, bookAuthor, startTime, endTime);
    }

    @Override
    public Book getBookById(String bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public int insertReserve(String bookId, String userId, Date startDate, Date endDate) {
        return bookDao.insertReserve(bookId, userId, startDate, endDate);
    }

    @Override
    public List<Reserve> getReserveList(String userId, int pageIndex, int pageSize, String bookName, String bookAuthor, Date startDate, Date endDate) {
        return bookDao.getReserveList(userId, pageIndex, pageSize, bookName, bookAuthor, startDate, endDate);
    }

    @Override
    public int getReserveCount(String userId, String bookName, String bookAuthor, Date startDate, Date endDate) {
        return bookDao.getReserveCount(userId, bookName, bookAuthor, startDate, endDate);
    }

    @Override
    public int deleteReserve(String reserveId, String bookId, String userId) {
        return bookDao.deleteReserve(reserveId, bookId, userId);
    }

    @Override
    public int updateReserve(String reserveId, String userId, Date startDate, Date endDate) {
        return bookDao.updateReserve(reserveId, userId, startDate, endDate);
    }

    @Override
    public int deleteBookById(String bookId) {
        return bookDao.deleteBookById(bookId);
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public List<ReserveDto> getAllReserve(String userName, String reserveId, int reserveStatus, int pageIndex, int pageSize) {
        List<ReserveDto> reserveDtoList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate1 = new Date();
        long now = 0;
        try {
            now = sdf.parse(sdf.format(endDate1)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate2 = new Date(now + (Constant.EXPIRING * 24 * 60 * 60 * 1000));
        if(reserveStatus == -1){
            reserveDtoList = bookDao.getAllReserve(userName, reserveId, null, null, pageIndex, pageSize);
        }else if(reserveStatus == 1){
            reserveDtoList = bookDao.getAllReserve(userName, reserveId, null, endDate2, pageIndex, pageSize);
        }else if(reserveStatus == 2){
            reserveDtoList = bookDao.getAllReserve(userName, reserveId, endDate1, endDate2, pageIndex, pageSize);
        }else{
            reserveDtoList = bookDao.getAllReserve(userName, reserveId, endDate1, null, pageIndex, pageSize);
        }
        return reserveDtoList;
    }

    @Override
    public int getReserveCount(String userName, String reserveId, int reserveStatus) {
        int count = 0;
        Date endDate1 = new Date();
        long now = endDate1.getTime();
        Date endDate2 = new Date(now + (Constant.EXPIRING * 24 * 60 * 60 * 1000));
        if(reserveStatus == -1){
            count = bookDao.getReserveCount(userName, reserveId, null, null);
        }else if(reserveStatus == 1){
            count = bookDao.getReserveCount(userName, reserveId, null, endDate2);
        }else if(reserveStatus == 2){
            count = bookDao.getReserveCount(userName, reserveId, endDate1, endDate2);
        }else {
            count = bookDao.getReserveCount(userName, reserveId, endDate1, null);
        }
        return count;
    }

    @Override
    public Reserve getReserveById(String reserveId) {
        return bookDao.getReserveById(reserveId);
    }
}
