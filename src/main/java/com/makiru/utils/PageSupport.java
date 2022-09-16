package com.makiru.utils;

public class PageSupport {
    private int currentPage;
    private int totalPageNum;
    private int pageSize;
    private int totalCount;

    public PageSupport() {
    }

    public PageSupport(int currentPage, int totalPageNum, int pageSize, int totalCount) {
        this.currentPage = currentPage;
        this.totalPageNum = totalPageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage > 0 && currentPage <= totalPageNum){
            this.currentPage = currentPage;
        }else{
            this.currentPage = 1;
        }
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum() {
        this.totalPageNum = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.setTotalPageNum();
    }
}
