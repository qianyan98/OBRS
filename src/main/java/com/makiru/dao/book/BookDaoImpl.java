package com.makiru.dao.book;

import com.makiru.dao.BaseDao;
import com.makiru.dto.ReserveDto;
import com.makiru.pojo.Book;
import com.makiru.pojo.Reserve;
import com.makiru.utils.IdUtil;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDaoImpl implements BookDao{
    @Override
    public List<Book> getBookList(int pageIndex, int pageSize, String bookName, String bookAuthor, Date startTime, Date endTime) {
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("select * from OBRS_book");
        boolean tag = false;
        if(!StringUtils.isNullOrEmpty(bookName)){
            builder.append(" where bookName like ?");
            params.add("%" + bookName + "%");
            tag = true;
        }
        if(!StringUtils.isNullOrEmpty(bookAuthor)){
            if(tag){
                builder.append(" and bookAuthor like ?");
            }else{
                builder.append(" where bookAuthor like ?");
                tag = true;
            }
            params.add("%" + bookAuthor + "%");
        }
        if(startTime != null && endTime != null){
            if(tag){
                builder.append(" and bookPublishDate between ? and ?");
            }else{
                builder.append(" where bookPublishDate between ? and ?");
            }
            params.add(startTime);
            params.add(endTime);
        }
        builder.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);
        String sql = builder.toString();
        List<Book> bookList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            while (resultSet.next()){
                Book book = new Book();
                book.setBookId(resultSet.getString("bookId"));
                book.setBookName(resultSet.getString("bookName"));
                book.setBookAuthor(resultSet.getString("bookAuthor"));
                book.setBookPublishDate(resultSet.getDate("bookPublishDate"));
                book.setBookType(resultSet.getString("bookType"));
                book.setBookDescription(resultSet.getString("bookDescription"));
                book.setBookStatus(resultSet.getInt("bookStatus"));
                bookList.add(book);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return bookList;
    }

    @Override
    public int getBookCount(String bookName, String bookAuthor, Date startTime, Date endTime) {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("select count(1) as count from OBRS_book");
        boolean tag = false;
        if(!StringUtils.isNullOrEmpty(bookName)){
            builder.append(" where bookName like ?");
            params.add("%" + bookName + "%");
            tag = true;
        }
        if(!StringUtils.isNullOrEmpty(bookAuthor)){
            if(tag){
                builder.append(" and bookAuthor like ?");
            }else{
                builder.append(" where bookAuthor like ?");
                tag = true;
            }
            params.add("%" + bookAuthor + "%");
        }
        if(startTime != null && endTime != null){
            if(tag){
                builder.append(" and bookPublishDate between ? and ?");
            }else{
                builder.append(" where bookPublishDate between ? and ?");
            }
            params.add(startTime);
            params.add(endTime);
        }
        String sql = builder.toString();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            if (resultSet.next()){
                count = resultSet.getInt("count");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return count;
    }

    @Override
    public Book getBookById(String bookId) {
        String sql = "select * from OBRS_book where bookId=?";
        Object[] params = {bookId};
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Book book = null;
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            if(resultSet.next()){
                book = new Book();
                book.setBookId(resultSet.getString("bookId"));
                book.setBookName(resultSet.getString("bookName"));
                book.setBookAuthor(resultSet.getString("bookAuthor"));
                book.setBookType(resultSet.getString("bookType"));
                book.setBookStatus(resultSet.getInt("bookStatus"));
                book.setBookPublishDate(resultSet.getDate("bookPublishDate"));
                book.setBookDescription(resultSet.getString("bookDescription"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return book;
    }

    @Override
    public int insertReserve(String bookId, String userId, Date startDate, Date endDate) {
        int updateRow = 0;
        //插入前，判断下是否已被预约
        List<Reserve> reserveList = getReserveById(bookId, 0);
        if(reserveList.size() != 0){
            return 0;
        }
        String sql1 = "insert into OBRS_reserve(reserveId, bookId, userId, startDate, endDate) values(?, ?, ?, ?, ?)";
        Object[] params1 = {IdUtil.getNextId(), bookId, userId, startDate, endDate};

        String sql2 = "update OBRS_book set bookStatus=? where bookId=?";
        Object[] params2 = {2, bookId};

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            //插入预约记录
            statement = connection.prepareStatement(sql1);
            updateRow = BaseDao.execute(statement, params1);
            //更新书籍状态
            statement = connection.prepareStatement(sql2);
            updateRow = BaseDao.execute(statement, params2);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public List<Reserve> getReserveList(String userId, int pageIndex, int pageSize, String bookName, String bookAuthor, Date startDate, Date endDate) {
        List<Reserve> reserveList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("select r.*, b.bookName, b.bookAuthor, b.bookPublishDate " +
                "from OBRS_reserve as r INNER JOIN OBRS_book as b on r.bookId=b.bookId where userId=?");
        List<Object> params = new ArrayList<>();
        params.add(userId);
        if(!StringUtils.isNullOrEmpty(bookName)){
            builder.append(" and bookName like ?");
            params.add("%" + bookName + "%");
        }
        if(!StringUtils.isNullOrEmpty(bookAuthor)){
            builder.append(" and bookAuthor like ?");
            params.add("%" + bookAuthor + "%");
        }
        if(startDate != null && endDate != null){
            builder.append(" and startDate >= ? and endDate <= ?");
            params.add(startDate);
            params.add(endDate);
        }

        if(pageIndex != -1 && pageSize != -1){
            builder.append(" order by endDate ASC limit ?,?");
            params.add((pageIndex - 1) * pageSize);
            params.add(pageSize);
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            while (resultSet.next()){
                Reserve reserve = new Reserve();
                reserve.setReserveId(resultSet.getString("reserveId"));
                reserve.setBookId(resultSet.getString("bookId"));
                reserve.setBookName(resultSet.getString("bookName"));
                reserve.setBookAuthor(resultSet.getString("bookAuthor"));
                reserve.setBookPublishDate(resultSet.getDate("bookPublishDate"));
                reserve.setCount(resultSet.getInt("count"));
                reserve.setStartDate(resultSet.getDate("startDate"));
                reserve.setEndDate(resultSet.getDate("endDate"));
                reserveList.add(reserve);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }

        return reserveList;
    }

    @Override
    public int getReserveCount(String userId, String bookName, String bookAuthor, Date startDate, Date endDate) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(1) as count from OBRS_reserve as r INNER JOIN OBRS_book as b on r.bookId = b.bookId where r.userId=?");
        List<Object> params = new ArrayList<>();
        params.add(userId);
        if(!StringUtils.isNullOrEmpty(bookName)){
            builder.append(" and bookName like ?");
            params.add("%" + bookName + "%");
        }
        if(!StringUtils.isNullOrEmpty(bookAuthor)){
            builder.append(" and bookAuthor like ?");
            params.add("%" + bookAuthor + "%");
        }
        if(startDate != null && endDate != null){
            builder.append(" and startDate >= ?").append(" and endDate <= ?");
            params.add(startDate);
            params.add(endDate);
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            if(resultSet.next()){
                totalCount = resultSet.getInt("count");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return totalCount;
    }

    @Override
    public int deleteReserve(String reserveId, String bookId, String userId) {
        List<Reserve> reserveList = getReserveById(reserveId, 1);
        if(reserveList.size() == 0){
            return 0;
        }
        String sql1 = "delete from OBRS_reserve where reserveId=? and bookId=? and userId=?";
        Object[] params1 = {reserveId, bookId, userId};
        String sql2 = "update OBRS_book set bookStatus=1 where bookId=?";
        Object[] params2 = {bookId};
        int updateRow = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql1);
            updateRow = BaseDao.execute(statement, params1);
            statement = connection.prepareStatement(sql2);
            updateRow = BaseDao.execute(statement, params2);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public int updateReserve(String reserveId, String userId, Date startDate, Date endDate) {
        List<Reserve> reserveList = getReserveById(reserveId, 1);
        if(reserveList.size() != 1){
            return 0;
        }
        String sql = "update OBRS_reserve set startDate=?, endDate=?, count=? where reserveId=? and userId=?";
        Object[] params = {startDate, endDate, reserveList.get(0).getCount() + 1, reserveId, userId};

        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public int deleteBookById(String bookId) {
        Book book = getBookById(bookId);
        if(book != null){
            String sql = "delete from OBRS_book where bookId = ?";
            Object[] params = {bookId};
            Connection connection = null;
            PreparedStatement statement = null;
            int result = 0;
            try {
                connection = BaseDao.getConnection();
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(sql);
                result = BaseDao.execute(statement, params);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException e) {
                if(connection == null){
                    return -1;
                }
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                BaseDao.closeResources(connection, statement, null);
            }
            return result;
        }else{
            return -1;
        }
    }

    @Override
    public int updateBook(Book book) {
        String sql = "update OBRS_book set bookName=?, bookAuthor=?, bookType=?, bookDescription=?, bookPublishDate=? where bookId=?";
        Object[] params = {book.getBookName(), book.getBookAuthor(), book.getBookType(), book.getBookDescription(), book.getBookPublishDate(), book.getBookId()};
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            result = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return result;
    }

    @Override
    public int addBook(Book book) {
        String sql = "insert into OBRS_book (bookId, bookName, bookAuthor, bookType, bookDescription, bookStatus, bookPublishDate) values(?,?,?,?,?,?,?)";
        Object[] params = {IdUtil.getNextId(), book.getBookName(), book.getBookAuthor(), book.getBookType(), book.getBookDescription(), 1, book.getBookPublishDate()};
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            result = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<ReserveDto> getAllReserve(String userName, String reserveId, Date endDate1, Date endDate2, int pageIndex, int pageSize) {
        List<ReserveDto> reserveList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String baseSql = "select r.reserveId, r.bookId, r.startDate, r.endDate, b.bookName, u.userName " +
                "from OBRS_reserve as r, OBRS_book as b, OBRS_user as u " +
                "where r.bookId = b.bookId and r.userId = u.userId";
        builder.append(baseSql);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(userName)){
            builder.append(" and u.userName like ?");
            params.add("%" + userName + "%");
        }
        if(!StringUtils.isNullOrEmpty(reserveId)){
            builder.append(" and r.reserveId = ?");
            params.add(reserveId);
        }
        if(endDate1 != null && endDate2 == null){
            builder.append(" and r.endDate < ?");
            params.add(endDate1);
        }else if(endDate1 != null){
            builder.append(" and r.endDate >= ? and r.endDate <= ?");
            params.add(endDate1);
            params.add(endDate2);
        }else if(endDate2 != null){
            builder.append(" and r.endDate > ?");
            params.add(endDate2);
        }

        builder.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            while (resultSet.next()){
                ReserveDto reserveDto = new ReserveDto();
                reserveDto.setReserveId(resultSet.getString("reserveId"));
                reserveDto.setBookId(resultSet.getString("bookId"));
                reserveDto.setBookName(resultSet.getString("bookName"));
                reserveDto.setUserName(resultSet.getString("userName"));
                reserveDto.setStartDate(resultSet.getDate("startDate"));
                reserveDto.setEndDate(resultSet.getDate("endDate"));
                reserveList.add(reserveDto);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }

        return reserveList;
    }

    @Override
    public int getReserveCount(String userName, String reserveId, Date endDate1, Date endDate2) {
        StringBuilder builder = new StringBuilder();
        String baseSql = "select count(1) as count from OBRS_reserve as r, OBRS_user as u where r.userId = u.userId";
        builder.append(baseSql);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(userName)){
            builder.append(" and u.userName like ?");
            params.add("%" + userName + "%");
        }
        if(!StringUtils.isNullOrEmpty(reserveId)){
            builder.append(" and r.reserveId = ?");
            params.add(reserveId);
        }
        if(endDate1 != null && endDate2 == null){
            builder.append(" and r.endDate < ?");
            params.add(endDate1);
        }else if(endDate1 != null){
            builder.append(" and r.endDate >= ? and r.endDate <= ?");
            params.add(endDate1);
            params.add(endDate2);
        }else if(endDate2 != null){
            builder.append(" and r.endDate > ?");
            params.add(endDate2);
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            if(resultSet.next()){
                count = resultSet.getInt("count");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return count;
    }

    @Override
    public Reserve getReserveById(String reserveId) {
        String sql = "select * from OBRS_reserve where reserveId = ?";
        Object[] params = {reserveId};
        Reserve reserve = new Reserve();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            if(resultSet.next()){
                reserve.setReserveId(resultSet.getString("reserveId"));
                reserve.setBookId(resultSet.getString("bookId"));
                reserve.setUserId(resultSet.getString("userId"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return reserve;
    }

    private List<Reserve> getReserveById(String id, int action){
        String sql = "";
        if(action == 0){
            sql = "select * from OBRS_reserve where bookId=?";
        }else if(action == 1){
            sql = "select * from OBRS_reserve where reserveId=?";
        }
        Object[] params = {id};
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Reserve> reserveList = new ArrayList<>();
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            while (resultSet.next()){
                Reserve reserve = new Reserve();
                reserve.setReserveId(resultSet.getString("reserveId"));
                reserve.setCount(resultSet.getInt("count"));
                reserveList.add(reserve);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return reserveList;
    }

    @Deprecated
    private int updateBookStatus(String bookId) {
        //先获取书的状态
        Book book = this.getBookById(bookId);
        int status = 1;
        if(book.getBookStatus() == 1){
            status = 2;
        }
        //更新
        String sql = "update OBRS_book set bookStatus=? where bookId=?";
        Object[] params = {status, bookId};
        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e){
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }
}
