package com.makiru.dto;

import com.makiru.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserveDto {
    private String reserveId;
    private String bookId;
    private String bookName;
    private String userName;
    private Date startDate;
    private Date endDate;
    private int status;

    public ReserveDto() {
    }

    public ReserveDto(String reserveId, String bookId, String bookName, String userName, Date startDate, Date endDate, int status) {
        this.reserveId = reserveId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        autoSetReserveStatus();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
            this.status = 2;
        }else if(sub < 0){
            this.status = 3;
        }else{
            this.status = 1;
        }
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
