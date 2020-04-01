package com.ncu.springboot.core.util;

import static com.ncu.springboot.core.constant.type.DirectionType.X_NEGATIVE;
import static com.ncu.springboot.core.constant.type.DirectionType.X_POSITIVE;
import static com.ncu.springboot.core.constant.type.DirectionType.Y_NEGATIVE;
import static com.ncu.springboot.core.constant.type.DirectionType.Y_POSITIVE;
import static com.ncu.springboot.core.constant.type.RelationDirectionType.BACK;
import static com.ncu.springboot.core.constant.type.RelationDirectionType.LEFT;

import com.ncu.springboot.core.constant.type.DirectionType;
import com.ncu.springboot.core.constant.type.RelationDirectionType;

public class DirectionUtil {

    public static final DirectionType DEVICE_INVENTORY_DIRECTION = DirectionType.X_POSITIVE;

    public static final String STORAGE_CODE_A = "A";
    public static final String STORAGE_CODE_B = "B";
    public static final String STORAGE_CODE_TWO = "AB";
    public static final String STORAGE_CODE_NONE = "";

    public static boolean isXLine(DirectionType direction) {
        return direction == DirectionType.X_POSITIVE || direction == DirectionType.X_NEGATIVE;
    }

    public static boolean isYLine(DirectionType direction) {
        return direction == DirectionType.Y_POSITIVE || direction == DirectionType.Y_NEGATIVE;
    }

    public static boolean isSameLine(DirectionType direction1, DirectionType direction2) {
        return isXLine(direction1) && isXLine(direction2) || isYLine(direction1) && isYLine(direction2);
    }

    public static String addSide(String originSideCode, String addSideCode) {
        String resultSide = originSideCode;
        boolean originIsNull, addIsNull;
        originSideCode = ValueUtil.getString(originSideCode, STORAGE_CODE_NONE).trim().toUpperCase();
        addSideCode = ValueUtil.getString(addSideCode, STORAGE_CODE_NONE).trim().toUpperCase();
        if ((originIsNull = originSideCode.equals(STORAGE_CODE_NONE)) & (addIsNull = addSideCode.equals(STORAGE_CODE_NONE))) {
            resultSide = STORAGE_CODE_NONE;
        } else if (originIsNull && !addIsNull) {
            resultSide = addSideCode;
        } else if (!originIsNull && addIsNull) {
            resultSide = originSideCode;
        } else if (!originSideCode.equals(addSideCode) && !STORAGE_CODE_TWO.equals(originSideCode)) {
            resultSide = STORAGE_CODE_TWO;
        }
        return resultSide;
    }

    public static String removeSide(String originSideCode, String removeSideCode) {
        originSideCode = ValueUtil.getString(originSideCode, STORAGE_CODE_NONE).trim().toUpperCase();
        removeSideCode = ValueUtil.getString(removeSideCode, STORAGE_CODE_NONE).trim().toUpperCase();
        String resultSideCode = originSideCode;
        if (originSideCode.equals(STORAGE_CODE_NONE)) {
            resultSideCode = STORAGE_CODE_NONE;
        } else if (removeSideCode.equals(STORAGE_CODE_NONE)) {
            resultSideCode = originSideCode;
        } else if (originSideCode.equals(removeSideCode) || STORAGE_CODE_TWO.equals(removeSideCode)) {
            resultSideCode = STORAGE_CODE_NONE;
        } else if (originSideCode.equals(STORAGE_CODE_TWO) && isValidSideSingleCode(removeSideCode)) {
            resultSideCode = removeSideCode.equals(STORAGE_CODE_A) ? STORAGE_CODE_B : STORAGE_CODE_A;
        }
        return resultSideCode;
    }

    /**
     * direction 当前方向
     * relactionCode 关联编码
     */
    public static final DirectionType getRelationDirection(DirectionType direction, RelationDirectionType relactionCode) {
        if (!isValidDirection(direction)) {
            throw new IllegalArgumentException("illegal direction : " + direction);
        }
        switch (direction) {
            case X_NEGATIVE:
                return relactionCode == BACK ? X_POSITIVE : relactionCode == LEFT ? Y_POSITIVE : Y_NEGATIVE;
            case X_POSITIVE:
                return relactionCode == BACK ? X_NEGATIVE : relactionCode == LEFT ? Y_NEGATIVE : Y_POSITIVE;
            case Y_NEGATIVE:
                return relactionCode == BACK ? Y_POSITIVE : relactionCode == LEFT ? X_NEGATIVE : X_POSITIVE;
            case Y_POSITIVE:
                return relactionCode == BACK ? Y_NEGATIVE : relactionCode == LEFT ? X_POSITIVE : X_NEGATIVE;
        }
        throw new IllegalArgumentException("illegal direction : " + direction);
    }

    public static DirectionType reverseDirection(DirectionType direction) {
        return getRelationDirection(direction, RelationDirectionType.BACK);
    }

    public static String reverseStorageCode(String storageSideCode) {
        return reverseStorageCode(storageSideCode, null);
    }

    public static String reverseStorageCode(String storageSideCode, String defaultSideCode) {
        if (!isValidSideSingleCode(storageSideCode)) {
            storageSideCode = defaultSideCode;
            if (!isValidSideSingleCode(storageSideCode)) {
                throw new IllegalArgumentException("illegal storageSideCode : " + storageSideCode);
            }
        }
        switch (storageSideCode) {
            case STORAGE_CODE_A:
                return STORAGE_CODE_B;
            case STORAGE_CODE_B:
                return STORAGE_CODE_A;
        }
        throw new IllegalArgumentException("illegal storageSideCode : " + storageSideCode);
    }

    public static boolean isValidDirection(DirectionType direction) {
        return direction != DirectionType.UNKNOWN && direction != null;
    }

    public static boolean isValidSideSingleCode(String storageSideCode) {
        storageSideCode = ValueUtil.getString(storageSideCode).toUpperCase();
        return STORAGE_CODE_A.equals(storageSideCode) || STORAGE_CODE_B.equals(storageSideCode);
    }

    public static final DirectionType leftDirection(DirectionType direction) {
        return getRelationDirection(direction, RelationDirectionType.LEFT);
    }

    public static final DirectionType rightDirection(DirectionType direction) {
        return getRelationDirection(direction, RelationDirectionType.RIGHT);
    }

    public static final DirectionType acquireStorageDirectionByCode(String storageSideCode, DirectionType targetDirection, boolean offset) {
        DirectionType direction = acquireStorageDirectionByCode(storageSideCode, targetDirection);
        return offset ? leftDirection(direction) : direction;
    }

    public static final DirectionType acquireStorageDirectionByCode(String storageSideCode, DirectionType targetDirection) {
        if (!isValidSideSingleCode(storageSideCode)) {
            throw new IllegalArgumentException("illegal storageSideCode : " + storageSideCode);
        }
        storageSideCode = ValueUtil.getString(storageSideCode).toUpperCase();
        if (STORAGE_CODE_A.equals(storageSideCode)) {
            return rightDirection(targetDirection);
        } else if (STORAGE_CODE_B.equals(storageSideCode)) {
            return leftDirection(targetDirection);
        }
        throw new IllegalArgumentException("illegal storageSideCode : " + storageSideCode);
    }

    /**
     * @param curStorageDirection 当前货架方向
     * @param targetDirection     打包台相对地图方向
     * @return 货架编号为A或B，若不为A且不为B，则返回NONE
     */
    public static final String acquireStorageSideCode(DirectionType curStorageDirection, DirectionType targetDirection) {
        DirectionType aSideDirection = leftDirection(curStorageDirection);
        if (!isSameLine(aSideDirection, targetDirection)) {
            return STORAGE_CODE_NONE;
        }
        if (aSideDirection == Y_NEGATIVE) {
            return targetDirection == Y_NEGATIVE ? STORAGE_CODE_A : STORAGE_CODE_B;
        } else if (aSideDirection == Y_POSITIVE) {
            return targetDirection == Y_NEGATIVE ? STORAGE_CODE_B : STORAGE_CODE_A;
        } else if (aSideDirection == X_NEGATIVE) {
            return targetDirection == X_NEGATIVE ? STORAGE_CODE_A : STORAGE_CODE_B;
        } else if (aSideDirection == X_POSITIVE) {
            return targetDirection == X_NEGATIVE ? STORAGE_CODE_B : STORAGE_CODE_A;
        }
        return STORAGE_CODE_NONE;
    }

    public static boolean isInValidSingleSideCode(String storageSideCode) {
        return !isValidSideSingleCode(storageSideCode);
    }

    public static String getDefaultSideCode(String side) {
        return isInValidSingleSideCode(side) ? STORAGE_CODE_A : side;
    }

    public static boolean checkRequireRotate(String requireSide, String finishSide) {
        if (DirectionUtil.STORAGE_CODE_TWO.equals(requireSide)) {
            return true;
        }
        if (isInValidSingleSideCode(requireSide)) {
            return false;
        }
        return !requireSide.equals(finishSide);
    }

    public static String acquireNextStorageSideCode(String requireSide, String finishSide) {
        String result;
        if (STORAGE_CODE_TWO.equals(requireSide)) {
            result = STORAGE_CODE_A.equals(finishSide) ? STORAGE_CODE_B : STORAGE_CODE_A;
        } else {
            result = requireSide.equals(finishSide) ? STORAGE_CODE_NONE : requireSide;
        }
        return result;
    }

    public static String subSide(String minuend, String subtrahend) {
        if( STORAGE_CODE_TWO.equals(subtrahend) || minuend.equals(subtrahend) || STORAGE_CODE_NONE.equals(minuend) ) {
            return "";
        }
        if( STORAGE_CODE_TWO.equals(minuend) ) {
            return STORAGE_CODE_A.equals(subtrahend) ? STORAGE_CODE_B : STORAGE_CODE_B.equals(subtrahend) ? STORAGE_CODE_A : minuend;
        }
        return minuend;
    }
}
