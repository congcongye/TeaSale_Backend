package com.cxtx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchuyang on 16/10/31.
 */
public class PageListModel {

    private PageListModel() {

    }

    public static Builder Builder() {
        return new Builder();
    }

    static public class Builder {

        private PageListModel model = new PageListModel();

        public Builder totalCount(long totalCount) {
            model.setTotalCount(totalCount);
            return this;
        }

        public Builder list(List list) {
            model.setList(list);
            return this;
        }

        public Builder totalPage(int totalPage) {
            model.setTotalPage(totalPage);
            return this;
        }

        public Builder pageSize(int pageSize) {
            model.setPageSize(pageSize);
            return this;
        }
        public Builder pageIndex(int pageIndex) {
            model.setPageIndex(pageIndex);
            return this;
        }

        public PageListModel build() {
            return model;
        }
    }

    private long totalCount;

    private int totalPage;

    private List list;

    private int pageSize;

    private int pageIndex;

    private PageListModel(int totalCount, List list) {
        this.totalCount = totalCount;
        this.list = list;
    }


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public static <E> PageListModel empty() {
        return new PageListModel(0, new ArrayList<E>());
    }
}
