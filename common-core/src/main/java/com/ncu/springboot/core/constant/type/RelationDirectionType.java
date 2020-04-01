package com.ncu.springboot.core.constant.type;

/**
 * 关联方向类型枚举
 */
public enum RelationDirectionType {
    LEFT("向左"),
    RIGHT("向右"),
    BACK("向后");

    private String desc;

    RelationDirectionType(String desc) {
        this.desc = desc;
    }
}
