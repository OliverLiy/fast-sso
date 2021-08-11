package com.fast.sso.client.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by:ly
 * @ClassName: Pagination
 * @Description: TODO
 * @Date: 2021/7/14 15:58
 **/
@Data
public class Pagination implements Serializable {
    private static final long serialVersionUID = -2590359609607515975L;
    private int pageIndex = 1;
    private int pageSize = 10;
    private long total;
    private int pages;

    public Pagination() {
    }


    public Pagination(int pageIndex, int pageSize, long total) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Pagination(int pageIndex, int pageSize, long total, int pages) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
    }
}
