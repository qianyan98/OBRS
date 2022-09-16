package com.makiru.service.book;

import com.makiru.dto.ReserveDto;
import com.makiru.pojo.Book;
import com.makiru.pojo.Reserve;

import java.util.Date;
import java.util.List;

public interface BookService {
    //获取书籍列表
    List<Book> getBookList(int pageIndex, int pageSize, String bookName, String bookAuthor, Date startTime, Date endTime);
    //获取书籍数量
    int getBookCount(String bookName, String bookAuthor, Date startTime, Date endTime);
    //根据书籍id获取书籍信息
    Book getBookById(String bookId);
    //插入预约信息
    int insertReserve(String bookId, String userId, Date startDate, Date endDate);
    //获取所有预约
    List<Reserve> getReserveList(String userId, int pageIndex, int pageSize, String bookName, String bookAuthor, Date startDate, Date endDate);
    //获取预约数目
    int getReserveCount(String userId, String bookName, String bookAuthor, Date startDate, Date endDate);
    //删除预约记录，并更新书籍状态
    int deleteReserve(String reserveId, String bookId, String userId);
    //更新预约记录中的count，startDate，endDate（续借）
    int updateReserve(String reserveId, String userId, Date startDate, Date endDate);
    //根据书籍id删除书籍
    int deleteBookById(String bookId);
    //更新书籍
    int updateBook(Book book);
    //添加书籍
    int addBook(Book book);
    //获取所有的预约记录
    List<ReserveDto> getAllReserve(String userName, String reserveId, int reserveStatus, int pageIndex, int pageSize);
    //获取预约的数目
    int getReserveCount(String userName, String reserveId, int reserveStatus);
    //根据id获取预约
    Reserve getReserveById(String reserveId);
}
