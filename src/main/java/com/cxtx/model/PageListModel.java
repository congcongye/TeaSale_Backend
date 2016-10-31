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

        public Builder totalCount(int totalCount) {
            model.setTotalCount(totalCount);
            return this;
        }

        public Builder list(List list) {
            model.setList(list);
            return this;
        }

        public Builder offset(int offset) {
            model.setOffset(offset);
            return this;
        }

        public Builder limit(int limit) {
            model.setLimit(limit);
            return this;
        }

        public PageListModel build() {
            return model;
        }
    }

    private int totalCount;

    private List list;

    private int offset;

    private int limit;

    private PageListModel(int totalCount, List list) {
        this.totalCount = totalCount;
        this.list = list;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public static <E> PageListModel empty() {
        return new PageListModel(0, new ArrayList<E>());
    }
}
