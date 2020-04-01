package com.ncu.springboot.core.model;

import java.io.Serializable;

/**
 * 排序模型类
 * */
public class SortMarker implements Serializable {

    public static final String ASC = "asc";

    public static final String DESC = "desc";

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 排序属性
     * */
    private String field;

    /**
     * 是否升序
     * */
    private boolean asc = true;

    public SortMarker() {
    }

    public SortMarker(String field) {
        this.field = field;
    }

    public SortMarker(String field, boolean asc) {
        this.field = field;
        this.asc = asc;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return asc ? ASC : DESC;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
