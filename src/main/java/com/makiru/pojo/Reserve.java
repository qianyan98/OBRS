package com.makiru.pojo;

import com.makiru.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reserve {
    private String reserveId;
    private String bookId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private int count;  //续借次数  最多2次

    private int reserveStatus;  //预约状态（有效 1，快过期 2，逾期 3）

    private String bookName;
    private String bookAuthor;
    private Date bookPublishDate;

    public Reserve() {
    }

    public Reserve(String reserveId, String bookId, String userId, Date startTime,
                   Date endTime, String bookName, String bookAuthor,
                   Date bookPublishDate, int reserveStatus, int count) {
        this.reserveId = reserveId;
        this.bookId = bookId;
        this.userId = userId;
        this.startDate = startTime;
        this.endDate = endTime;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublishDate = bookPublishDate;
        this.reserveStatus = reserveStatus;
        this.count = count;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        this.autoSetReserveStatus();
    }

    public Integer getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    private void autoSetReserveStatus(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        long now_time = 0;
        long end_time = 0;
        try {
            now_time = sdf.parse(sdf.format(date)).getTime();
            end_time = sdf.parse(sdf.format(this.endDate)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int sub = (int) ((end_time - now_time) / (1000 * 60 * 60 * 24));
        if(sub >= 0 && sub <= Constant.EXPIRING){
            this.reserveStatus = 2;
        }else if(sub < 0){
            this.reserveStatus = 3;
        }else{
            this.reserveStatus = 1;
        }
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Date getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(Date bookPublishDate) {
        this.bookPublishDate = bookPublishDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if(count >= 0){
            this.count = count;
        }else{
            this.count = 0;
        }
    }
}
