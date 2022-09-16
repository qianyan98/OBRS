package com.makiru.pojo;

import java.util.Date;

public class Book {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private Date bookPublishDate;
    private String bookType;
    private String bookDescription;
    private Integer bookStatus;

    public Book() {
    }

    public Book(String bookId, String bookName, String bookAuthor, Date bookPublishDate,
                String bookType, String bookDescription, Integer bookStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublishDate = bookPublishDate;
        this.bookType = bookType;
        this.bookDescription = bookDescription;
        this.bookStatus = bookStatus;
    }

    public Date getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(Date bookPublishDate) {
        this.bookPublishDate = bookPublishDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }
}
