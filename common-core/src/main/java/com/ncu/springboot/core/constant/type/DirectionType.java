package com.ncu.springboot.core.constant.type;

import com.ncu.springboot.core.util.ValueUtil;

public enum DirectionType {
    UNKNOWN(-1, "未知"),
    X_POSITIVE(0, "X轴正方向"),
    X_NEGATIVE(2, "X轴负方向"),
    Y_NEGATIVE(3, "Y轴负方向"),
    Y_POSITIVE(1, "Y轴正方向");

    private int val;
    private String desc;

    DirectionType(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    public static int getReverseDirection(Integer direction) {
        direction = ValueUtil.getInt(direction);
        if (0 == direction)
            return 2;
        else if (1 == direction)
            return 3;
        else if (2 == direction) {
            return 0;
        } else if (3 == direction) {
            return 1;
        }
        return 0;
    }

    public static DirectionType valOf(String directionVal) {
        return valOf(ValueUtil.getInt(directionVal));
    }

    public static DirectionType valOf(Integer directionVal) {
        directionVal = ValueUtil.getInt(directionVal , -1);
        for (DirectionType type : values()) {
            if (type.getVal() == directionVal) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public String getStringVal() {
        return ValueUtil.getString(val);
    }
}
